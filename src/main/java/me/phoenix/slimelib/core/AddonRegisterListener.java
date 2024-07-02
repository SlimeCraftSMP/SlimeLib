package me.phoenix.slimelib.core;

import me.phoenix.slimelib.SlimeLib;
import me.phoenix.slimelib.addon.SlimeAddon;
import me.phoenix.slimelib.registry.AddonRegistry;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Registers all the addons to {@link AddonRegistry}
 */
public class AddonRegisterListener implements Listener{

	/**
	 * Instantiates a new Addon register listener.
	 *
	 * @param plugin the plugin
	 */
	public AddonRegisterListener(JavaPlugin plugin){
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Registers addons
	 *
	 * @param event the event
	 */
	@EventHandler(ignoreCancelled = true)
	public void onServerStartup(ServerLoadEvent event){
		for(Plugin plugin : Bukkit.getPluginManager().getPlugins()){
			if(plugin instanceof SlimeAddon){
				SlimeLib.addonRegistry().addon(plugin);
			}
		}
	}
}
