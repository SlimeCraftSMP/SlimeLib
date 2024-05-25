package me.phoenix.slimelib.metrics;

import me.phoenix.slimelib.metrics.chart.CustomChart;
import me.phoenix.slimelib.metrics.object.JsonObjectBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Main Metrics service class.
 */
@SuppressWarnings("UnstableApiUsage")
public class MetricsService{

	private final JavaPlugin plugin;

	private final MetricsBase metricsBase;

	/**
	 * Instantiates a new Metrics service.
	 *
	 * @param plugin the plugin
	 * @param serviceId the service id
	 */
	public MetricsService(JavaPlugin plugin, int serviceId){
		this.plugin = plugin;
		// Get the config file
		final File bStatsFolder = new File(plugin.getDataFolder().getParentFile(), "bStats");
		final File configFile = new File(bStatsFolder, "config.yml");
		final YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		if(!config.isSet("serverUuid")){
			config.addDefault("enabled", true);
			config.addDefault("serverUuid", UUID.randomUUID().toString());
			config.addDefault("logFailedRequests", false);
			config.addDefault("logSentData", false);
			config.addDefault("logResponseStatusText", false);
			// Inform the server owners about bStats
			config.options().setHeader(List.of("bStats (https://bStats.org) collects some basic information for plugin authors, like how " + "many people use their plugin and their total player count. It's recommended to keep bStats " + "enabled, but if you're not comfortable with this, you can turn this setting off. There is no " + "performance penalty associated with having metrics enabled, and data sent to bStats is fully " + "anonymous.")).copyDefaults(true);
			try{
				config.save(configFile);
			} catch(IOException ignored){ }
		}
		// Load the data
		final String serverUUID = config.getString("serverUuid");
		final boolean enabled = config.getBoolean("enabled", true);
		final boolean logErrors = config.getBoolean("logFailedRequests", false);
		final boolean logSentData = config.getBoolean("logSentData", false);
		final boolean logResponseStatusText = config.getBoolean("logResponseStatusText", false);
		metricsBase = new MetricsBase("bukkit", serverUUID, serviceId, enabled, this::appendPlatformData, this::appendServiceData,
				submitDataTask -> Bukkit.getScheduler().runTask(plugin, submitDataTask), plugin::isEnabled,
				(message, error) -> this.plugin.getLogger().log(Level.WARNING, message, error),
				(message) -> this.plugin.getLogger().log(Level.INFO, message), logErrors, logSentData, logResponseStatusText
		);
	}

	/**
	 * Add a custom chart.
	 *
	 * @param chart the chart to add
	 */
	public void addCustomChart(CustomChart chart){
		metricsBase.addCustomChart(chart);
	}

	private void appendPlatformData(JsonObjectBuilder builder){
		builder.appendField("playerAmount", Bukkit.getOnlinePlayers().size());
		builder.appendField("onlineMode", Bukkit.getOnlineMode() ? 1 : 0);
		builder.appendField("bukkitVersion", Bukkit.getVersion());
		builder.appendField("bukkitName", Bukkit.getName());
		builder.appendField("javaVersion", System.getProperty("java.version"));
		builder.appendField("osName", System.getProperty("os.name"));
		builder.appendField("osArch", System.getProperty("os.arch"));
		builder.appendField("osVersion", System.getProperty("os.version"));
		builder.appendField("coreCount", Runtime.getRuntime().availableProcessors());
	}

	private void appendServiceData(JsonObjectBuilder builder){
		builder.appendField("pluginVersion", plugin.getPluginMeta().getVersion());
	}

}
