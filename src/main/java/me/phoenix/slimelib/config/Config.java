package me.phoenix.slimelib.config;

import me.phoenix.slimelib.other.SchedulerUtils;
import me.phoenix.slimelib.other.TypeUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Config implementation with various utilities for plugin development.
 */
public class Config extends YamlConfiguration{

	private YamlConfiguration config;
	private File file;
	private JavaPlugin plugin;
	private String path;

	private Config(YamlConfiguration config){
		this.config = config;
	}

	private Config(File file){
		this(YamlConfiguration.loadConfiguration(file));
		this.file = file;
	}

	/**
	 * Creates a new Config.
	 *
	 * @param plugin the plugin
	 * @param path the path
	 * @param savePeriod the save period, if you wish to disable auto saving use {@link Config(JavaPlugin, String)}
	 */
	public Config(JavaPlugin plugin, String path, int savePeriod){
		this(new File(plugin.getDataFolder(), path));
		this.plugin = plugin;
		this.path = path;
		if(invalidConfig()){
			plugin.saveResource(path, true);
		}
		if(savePeriod != -1){
			SchedulerUtils.repeatTask(plugin, this::save, savePeriod);
		}
	}

	/**
	 * Creates a new Config without auto-save.
	 *
	 * @param plugin the plugin
	 * @param path the path
	 */
	public Config(JavaPlugin plugin, String path){
		this(plugin, path, -1);
	}

	/**
	 * Checks if a file is invalid.
	 *
	 * @return true if its invalid, false otherwise
	 */
	public boolean invalidFile(){
		return file == null || !file.exists();
	}

	/**
	 * Checks if config is invalid.
	 *
	 * @return true if its invalid, false otherwise
	 */
	public boolean invalidConfig(){
		return invalidFile() || config == null;
	}

	/**
	 * Resets the file.
	 * @implNote This WILL delete all configurations made by the server owner, use cautiously
	 */
	public void resetFile(){
		file = new File(plugin.getDataFolder(), path);
		plugin.saveResource(path, true);
	}

	/**
	 * Reset the config.
	 * @implNote This WILL delete all configurations made by the server owner, use cautiously
	 */
	public void resetConfig(){
		resetFile();
		config = YamlConfiguration.loadConfiguration(file);
	}

	/**
	 * Get a list of ItemStacks of the format - "ITEM:AMOUNT".
	 *
	 * @param path the path
	 *
	 * @return the list
	 */
	public List<ItemStack> getItems(String path){
		if(invalidConfig()){
			resetConfig();
		}
		final List<ItemStack> items = new ArrayList<>();
		for(String itemString : getStringList(path)){
			final String[] parts = itemString.split(":");
			if(parts.length != 2){
				continue;
			}
			final Material material = Material.getMaterial(parts[0]);
			if(material == null){
				continue;
			}
			final int amount = TypeUtils.asInt(parts[1]);
			if(amount == -1){
				continue;
			}
			items.add(new ItemStack(material, amount));
		}
		return items;
	}

	/**
	 * Get a map of EntityType and their amount of the format - "ENTITY:AMOUNT".
	 *
	 * @param path the path
	 *
	 * @return the map
	 */
	public Map<EntityType, Integer> getEntities(String path){
		if(invalidConfig()){
			resetConfig();
		}
		final Map<EntityType, Integer> entities = new HashMap<>();
		for(String mobString : getStringList(path)){
			final String[] parts = mobString.split(" ");
			if(parts.length != 2){
				continue;
			}
			final EntityType entity = EntityType.fromName(parts[0]);
			if(entity == null){
				continue;
			}
			final int amount = TypeUtils.asInt(parts[1]);
			if(amount == -1){
				continue;
			}
			entities.putIfAbsent(entity, amount);
		}
		return entities;
	}

	/**
	 * Sets the default value at a path.
	 *
	 * @param path the path
	 * @param value the value
	 */
	public void defaultValue(String path, Object value){
		if(invalidConfig()){
			resetConfig();
		}
		if(!contains(path)){
			set(path, value);
		}
	}

	/**
	 * Saves the config.
	 */
	public void save(){
		if(invalidConfig()){
			resetConfig();
		} else{
			try{
				super.save(file);
			} catch(IOException e){
				throw new RuntimeException("Could not save config!");
			}
		}
	}

	/**
	 * Reloads the config.
	 */
	public void reload(){
		if(invalidConfig()){
			resetConfig();
		} else{
			config = YamlConfiguration.loadConfiguration(file);
			save();
		}
	}

	/**
	 * Returns the plugin that made the config.
	 *
	 * @return the java plugin
	 */
	public JavaPlugin plugin(){
		return plugin;
	}

	/**
	 * Returns the file the config is stored in, resets it if its invalid.
	 *
	 * @return the file
	 */
	public File file(){
		if(invalidFile()){
			resetConfig();
		}
		return file;
	}

	/**
	 * Returns the config object, resets it if its invalid.
	 *
	 * @return the yaml configuration
	 */
	public YamlConfiguration config(){
		if(invalidConfig()){
			resetConfig();
		}
		return config;
	}
}
