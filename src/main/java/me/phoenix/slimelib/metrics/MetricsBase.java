package me.phoenix.slimelib.metrics;

import me.phoenix.slimelib.metrics.chart.CustomChart;
import me.phoenix.slimelib.metrics.object.JsonObject;
import me.phoenix.slimelib.metrics.object.JsonObjectBuilder;
import me.phoenix.slimelib.other.RandomUtils;
import me.phoenix.slimelib.other.TypeUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.zip.GZIPOutputStream;

/**
 * Main class for Metrics.
 */
public class MetricsBase{

	private static final String METRICS_VERSION = "3.0.2";

	private static final String REPORT_URL = "https://bStats.org/api/v2/data/%s";

	private final ScheduledExecutorService scheduler;

	private final String platform;

	private final String serverUuid;

	private final int serviceId;

	private final Consumer<JsonObjectBuilder> appendPlatformDataConsumer;

	private final Consumer<JsonObjectBuilder> appendServiceDataConsumer;

	private final Consumer<Runnable> submitTaskConsumer;

	private final Supplier<Boolean> checkServiceEnabledSupplier;

	private final BiConsumer<String, Throwable> errorLogger;

	private final Consumer<String> infoLogger;

	private final boolean logErrors;

	private final boolean logSentData;

	private final boolean logResponseStatusText;

	private final Set<CustomChart> customCharts = new HashSet<>();

	private final boolean enabled;

	/**
	 * Instantiates a new Metrics base.
	 *
	 * @param platform the platform
	 * @param serverUuid the server uuid
	 * @param serviceId the service id
	 * @param enabled the enabled
	 * @param appendPlatformDataConsumer the append platform data consumer
	 * @param appendServiceDataConsumer the append service data consumer
	 * @param submitTaskConsumer the submit task consumer
	 * @param checkServiceEnabledSupplier the check service enabled supplier
	 * @param errorLogger the error logger
	 * @param infoLogger the info logger
	 * @param logErrors the log errors
	 * @param logSentData the log sent data
	 * @param logResponseStatusText the log response status text
	 */
	public MetricsBase(String platform, String serverUuid, int serviceId, boolean enabled, Consumer<JsonObjectBuilder> appendPlatformDataConsumer, Consumer<JsonObjectBuilder> appendServiceDataConsumer, Consumer<Runnable> submitTaskConsumer, Supplier<Boolean> checkServiceEnabledSupplier, BiConsumer<String, Throwable> errorLogger, Consumer<String> infoLogger, boolean logErrors, boolean logSentData, boolean logResponseStatusText){
		final ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(1, task -> new Thread(task, "bStats-MetricsService"));
		threadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		this.scheduler = threadPoolExecutor;
		this.platform = platform;
		this.serverUuid = serverUuid;
		this.serviceId = serviceId;
		this.enabled = enabled;
		this.appendPlatformDataConsumer = appendPlatformDataConsumer;
		this.appendServiceDataConsumer = appendServiceDataConsumer;
		this.submitTaskConsumer = submitTaskConsumer;
		this.checkServiceEnabledSupplier = checkServiceEnabledSupplier;
		this.errorLogger = errorLogger;
		this.infoLogger = infoLogger;
		this.logErrors = logErrors;
		this.logSentData = logSentData;
		this.logResponseStatusText = logResponseStatusText;
		if(enabled){
			startSubmitting();
		}
	}

	private static byte[] compress(final String str){
		if(str == null){
			return null;
		}
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try(final GZIPOutputStream gzip = new GZIPOutputStream(outputStream)){
			gzip.write(str.getBytes(StandardCharsets.UTF_8));
		} catch(IOException e){
			throw new RuntimeException("Error compressing data!");
		}
		return outputStream.toByteArray();
	}

	/**
	 * Add custom chart.
	 *
	 * @param chart the chart to add
	 */
	public void addCustomChart(CustomChart chart){
		this.customCharts.add(chart);
	}

	private void startSubmitting(){
		final Runnable submitTask = () -> {
			if(enabled && Boolean.FALSE.equals(checkServiceEnabledSupplier.get())){
				scheduler.shutdown();
				return;
			}
			if(submitTaskConsumer != null){
				submitTaskConsumer.accept(this::submitData);
			} else{
				this.submitData();
			}
		};
		final long initialDelay = 1000L * 60 * (3 + RandomUtils.randomInt(0, 3));
		final long secondDelay = 1000L * 60 * (RandomUtils.randomInt(0, 30));
		scheduler.schedule(submitTask, initialDelay, TimeUnit.MILLISECONDS);
		scheduler.scheduleAtFixedRate(submitTask, initialDelay + secondDelay, 1000 * 60 * 30, TimeUnit.MILLISECONDS);
	}

	private void submitData(){
		final JsonObjectBuilder baseJsonBuilder = new JsonObjectBuilder();
		appendPlatformDataConsumer.accept(baseJsonBuilder);
		final JsonObjectBuilder serviceJsonBuilder = new JsonObjectBuilder();
		appendServiceDataConsumer.accept(serviceJsonBuilder);
		final JsonObject[] chartData = customCharts.stream().map(customChart -> customChart.getRequestJsonObject(errorLogger, logErrors)).filter(Objects::nonNull).toArray(JsonObject[]::new);
		serviceJsonBuilder.appendField("id", serviceId);
		serviceJsonBuilder.appendField("customCharts", chartData);
		baseJsonBuilder.appendField("service", serviceJsonBuilder.build());
		baseJsonBuilder.appendField("serverUUID", serverUuid);
		baseJsonBuilder.appendField("metricsVersion", METRICS_VERSION);
		final JsonObject data = baseJsonBuilder.build();
		scheduler.execute(() -> {
			try{
				sendData(data);
			} catch(Exception e){
				if(logErrors){
					errorLogger.accept("Could not submit bStats metrics data", e);
				}
			}
		});
	}

	private void sendData(JsonObject data) throws Exception{
		if(logSentData){
			infoLogger.accept("Sent bStats metrics data: " + data.toString());
		}
		final String url = String.format(REPORT_URL, platform);
		final HttpsURLConnection connection = (HttpsURLConnection) new URI(url).toURL().openConnection();
		// Compress the data to save bandwidth
		final byte[] compressedData = compress(data.toString());
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Accept", "application/json");
		connection.addRequestProperty("Connection", "close");
		connection.addRequestProperty("Content-Encoding", "gzip");
		connection.addRequestProperty("Content-Length", TypeUtils.asString(compressedData.length));
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("User-Agent", "MetricsService-Service/1");
		connection.setDoOutput(true);
		try(final DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())){
			outputStream.write(compressedData);
		}
		final StringBuilder builder = new StringBuilder();
		try(final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
			String line;
			while((line = bufferedReader.readLine()) != null){
				builder.append(line);
			}
		}
		if(logResponseStatusText){
			infoLogger.accept("Sent data to bStats and received response: " + builder);
		}
	}
}
