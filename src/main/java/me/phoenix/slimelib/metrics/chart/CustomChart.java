package me.phoenix.slimelib.metrics.chart;

import me.phoenix.slimelib.metrics.object.JsonObject;
import me.phoenix.slimelib.metrics.object.JsonObjectBuilder;

import java.util.function.BiConsumer;

/**
 * A Custom chart to represent data.
 */
public abstract class CustomChart{

	private final String chartId;

	/**
	 * Instantiates a new Custom chart.
	 *
	 * @param chartId the chart id
	 */
	protected CustomChart(String chartId){
		if(chartId == null){
			throw new IllegalArgumentException("chartId must not be null");
		}
		this.chartId = chartId;
	}

	/**
	 * Get chart data as a json object.
	 *
	 * @param errorLogger the error logger
	 * @param logErrors the log errors
	 *
	 * @return the json object
	 */
	public JsonObject getRequestJsonObject(BiConsumer<String, Throwable> errorLogger, boolean logErrors){
		final JsonObjectBuilder builder = new JsonObjectBuilder();
		builder.appendField("chartId", chartId);
		try{
			final JsonObject data = getChartData();
			if(data == null){
				return null;
			}
			builder.appendField("data", data);
		} catch(Exception e){
			if(logErrors){
				errorLogger.accept("Failed to get data for custom chart with id " + chartId, e);
			}
			return null;
		}
		return builder.build();
	}

	/**
	 * Gets chart data.
	 *
	 * @return the chart data
	 *
	 * @throws Exception the exception
	 */
	protected abstract JsonObject getChartData() throws Exception;
}
