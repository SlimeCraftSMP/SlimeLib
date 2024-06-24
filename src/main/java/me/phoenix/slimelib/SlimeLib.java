package me.phoenix.slimelib;

import me.phoenix.slimelib.config.Config;
import me.phoenix.slimelib.core.ReloadCommand;
import me.phoenix.slimelib.inventory.MenuListener;
import me.phoenix.slimelib.metrics.MetricsService;
import me.phoenix.slimelib.metrics.chart.pie.SimplePie;
import me.phoenix.slimelib.registry.CommandRegistry;
import org.bukkit.NamespacedKey;

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
	private final CommandRegistry commandRegistry = new CommandRegistry();

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
		config = new Config(getInstance(), "config.yml");
	}

	@Override
	public void setupMetrics(){
		final MetricsService metrics = new MetricsService(getInstance(), 21985);
		metrics.addCustomChart(
				new SimplePie("auto_update",
						() -> config.getBoolean("options.auto-update") ? "Enabled" : "Disabled"
				)
		);
	}

	@Override
	public void setupEvents(){
		new MenuListener(getInstance());
		new ReloadCommand(getInstance());
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
	 * Get he logger logger.
	 *
	 * @return the logger
	 */
	public static Logger logger(){
		return instance.getLogger();
	}

	/**
	 * Get the command registry.
	 *
	 * @return the command registry
	 */
	public static CommandRegistry commandRegistry(){
		return instance.commandRegistry;
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
