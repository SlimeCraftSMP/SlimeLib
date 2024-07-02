package me.phoenix.slimelib.registry;

import me.phoenix.slimelib.config.Config;

import java.util.HashMap;

/**
 * Stores all the configs.
 */
public class ConfigRegistry{

	private final HashMap<String, HashMap<String, Config>> configs = new HashMap<>();

	/**
	 * Configs map.
	 *
	 * @return the hash map
	 */
	public HashMap<String, HashMap<String, Config>> configs(){
		return configs;
	}

	/**
	 * Put a config in the registry.
	 *
	 * @param plugin the plugin
	 * @param config the config
	 */
	public void config(String plugin, Config config){
		if(configs.containsKey(plugin)){
			configs.get(plugin).putIfAbsent(config.name(), config);
		} else{
			final HashMap<String, Config> map = new HashMap<>();
			map.putIfAbsent(config.name(), config);
			configs.putIfAbsent(plugin, map);
		}
	}

	/**
	 * Check if the config exists.
	 *
	 * @param args the args
	 *
	 * @return true if it exists, false otherwise
	 */
	public boolean isValid(String[] args){
		return configs.containsKey(args[0]) && configs.get(args[0]).containsKey(args[1]);
	}

	/**
	 * Get the config from the registry.
	 *
	 * @param args the args
	 *
	 * @return the config
	 */
	public Config config(String[] args){
		return configs.get(args[0]).get(args[1]);
	}
}
