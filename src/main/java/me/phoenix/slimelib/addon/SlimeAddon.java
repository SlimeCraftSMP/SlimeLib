package me.phoenix.slimelib.addon;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class that needs to be extended to develop using SlimeLib.
 */
public abstract class SlimeAddon extends JavaPlugin{

	private static SlimeAddon instance;

	// Plugin Loading

	@Override
	public void onLoad(){
		instance = this;
		onPluginLoad();
	}

	@Override
	public void onEnable() {
		setupConfigs();
		setupMetrics();
		setupEvents();
		onPluginEnable();
	}

	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(instance);
		onPluginDisable();
	}

	// Required

	/**
	 * On plugin load.
	 */
	public abstract void onPluginLoad();

	/**
	 * On plugin enable.
	 */
	public abstract void onPluginEnable();

	/**
	 * On plugin disable.
	 */
	public abstract void onPluginDisable();

	/**
	 * Sets configs.
	 */
	public abstract void setupConfigs();

	/**
	 * Sets metrics.
	 */
	public abstract void setupMetrics();

	/**
	 * Sets events.
	 */
	public abstract void setupEvents();

	/**
	 * Executes actions on reload.
	 */
	public abstract void onReload(CommandSender sender);

	// Static Getters

	/**
	 * Instance of SlimeAddon. Override and cast it to your own plugin instance.
	 *
	 * @return the SlimeAddon instance
	 */
	public static SlimeAddon instance(){
		return instance;
	}
}
