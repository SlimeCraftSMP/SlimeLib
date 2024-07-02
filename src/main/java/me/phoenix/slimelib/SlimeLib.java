package me.phoenix.slimelib;

import me.phoenix.slimelib.addon.SlimeAddon;
import me.phoenix.slimelib.config.Config;
import me.phoenix.slimelib.core.AddonRegisterListener;
import me.phoenix.slimelib.core.ReloadCommand;
import me.phoenix.slimelib.core.VersionCommand;
import me.phoenix.slimelib.inventory.MenuListener;
import me.phoenix.slimelib.metrics.MetricsService;
import me.phoenix.slimelib.metrics.chart.pie.SimplePie;
import me.phoenix.slimelib.registry.AddonRegistry;
import me.phoenix.slimelib.registry.CommandRegistry;
import me.phoenix.slimelib.registry.ConfigRegistry;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

/**
 * The main SlimeLib class.
 */
public class SlimeLib extends SlimeAddon{

	// Important
	private static SlimeLib instance;

	// Config
	private Config config;

	// Registry
	private final AddonRegistry addonRegistry = new AddonRegistry();
	private final CommandRegistry commandRegistry = new CommandRegistry();
	private final ConfigRegistry configRegistry = new ConfigRegistry();

	@Override
	public void onPluginLoad(){
		instance = (SlimeLib) SlimeAddon.instance();
	}

	@Override
	public void onPluginEnable(){
		printLogo();
	}

	@Override
	public void onPluginDisable(){
		printLogo();
	}

	@Override
	public void setupConfigs(){
		config = new Config(instance, "config.yml");
	}

	@Override
	public void setupMetrics(){
		final MetricsService metrics = new MetricsService(instance, 21985);
		metrics.addCustomChart(
				new SimplePie("auto_update",
						() -> config.getBoolean("options.auto-update") ? "Enabled" : "Disabled"
				)
		);
	}

	@Override
	public void setupEvents(){
		new MenuListener(instance);
		new ReloadCommand(instance);
		new VersionCommand(instance);
		new AddonRegisterListener(instance);
	}

	@Override
	public void onReload(CommandSender sender){
		config.reload();
	}

	/**
	 * Get the instance of SlimeLib.
	 *
	 * @return the SlimeLib instance
	 */
	public static SlimeLib getInstance(){
		return instance;
	}

	/**
	 * Create a namespaced key.
	 *
	 * @param key the key
	 *
	 * @return the namespaced key
	 */
	public static NamespacedKey key(String key){ return new NamespacedKey(instance, key); }

	/**
	 * Get the main config.
	 *
	 * @return the config
	 */
	public static Config config(){
		return instance.config;
	}

	/**
	 * Get the logger.
	 *
	 * @return the logger
	 */
	public static Logger logger(){
		return instance.getLogger();
	}


	/**
	 * Get the addon registry.
	 *
	 * @return the addon registry
	 */
	public static AddonRegistry addonRegistry(){
		return instance.addonRegistry;
	}

	/**
	 * Get the command registry.
	 *
	 * @return the command registry
	 */
	public static CommandRegistry commandRegistry(){
		return instance.commandRegistry;
	}

	/**
	 * Get the config registry.
	 *
	 * @return the config registry
	 */
	public static ConfigRegistry configRegistry(){
		return instance.configRegistry;
	}

	private void printLogo(){
		logger().info("███████╗██╗     ██╗███╗   ███╗███████╗██╗     ██╗██████╗");
		logger().info("██╔════╝██║     ██║████╗ ████║██╔════╝██║     ██║██╔══██╗");
		logger().info("███████╗██║     ██║██╔████╔██║█████╗  ██║     ██║██████╔╝");
		logger().info("╚════██║██║     ██║██║╚██╔╝██║██╔══╝  ██║     ██║██╔══██╗");
		logger().info("███████║███████╗██║██║ ╚═╝ ██║███████╗███████╗██║██████╔╝");
		logger().info("╚══════╝╚══════╝╚═╝╚═╝     ╚═╝╚══════╝╚══════╝╚═╝╚═════╝");
	}
}
