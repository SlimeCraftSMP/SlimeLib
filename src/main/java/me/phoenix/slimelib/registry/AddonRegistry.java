package me.phoenix.slimelib.registry;

import org.bukkit.plugin.Plugin;
import java.util.HashMap;


/**
 * Stores all the addons.
 */
public class AddonRegistry{

	private final HashMap<String, Plugin> addons = new HashMap<>();

	/**
	 * Addons map.
	 *
	 * @return the hash map
	 */
	public HashMap<String, Plugin> addons(){
		return addons;
	}

	/**
	 * Add an addon to the registry.
	 *
	 * @param addon the addon
	 */
	public void addon(Plugin addon){
		addons.putIfAbsent(addon.getName(), addon);
	}

	/**
	 * Get an addon from the registry.
	 *
	 * @param name the name
	 *
	 * @return the plugin
	 */
	public Plugin addon(String name){
		return addons.get(name);
	}

	/**
	 * Get the size of the addon registry.
	 *
	 * @return the int
	 */
	public int size(){
		return addons.size();
	}
}
