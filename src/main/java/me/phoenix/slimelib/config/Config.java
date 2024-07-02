package me.phoenix.slimelib.config;

import me.phoenix.slimelib.SlimeLib;
import me.phoenix.slimelib.other.SchedulerUtils;
import me.phoenix.slimelib.other.TypeUtils;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

	private final JavaPlugin plugin;
	private final File file;
	private YamlConfiguration config;

	private Config(JavaPlugin plugin, String path, File file, boolean reloadAble){
		this.plugin = plugin;

		this.file = file;
		plugin.saveResource(path, false);
		load();
		save();

		this.config = YamlConfiguration.loadConfiguration(file);
		this.config.options().copyDefaults(true);

		if (reloadAble) SlimeLib.configRegistry().config(plugin.getName(), this);
	}

	/**
	 * Creates a new Config.
	 *
	 * @param plugin the plugin
	 * @param path the path
	 * @param savePeriod the save period, if you wish to disable auto saving set it as -1
	 * @param reloadAble if the config should be reloadable or not, if you wish to disable this set it as false
	 */
	public Config(JavaPlugin plugin, String path, int savePeriod, boolean reloadAble){
		this(plugin, path, new File(plugin.getDataFolder(), path), reloadAble);

		if(savePeriod != -1){
			SchedulerUtils.repeatTask(plugin, this::save, savePeriod);
		}
	}

	/**
	 * Creates a new Config without auto-save and reloadable by default.
	 *
	 * @param plugin the plugin
	 * @param path the path
	 */
	public Config(JavaPlugin plugin, String path){
		this(plugin, path, -1, true);
	}

	/**
	 * Get a list of ItemStacks of the format - "ITEM:AMOUNT".
	 *
	 * @param path the path
	 *
	 * @return the list
	 */
	public List<ItemStack> getItems(String path){
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

	@Override
	public void set(@Nonnull final String path, @Nullable final Object value){
		super.set(path, value);
		save();
	}

	/**
	 * Sets the default value at a path.
	 *
	 * @param path the path
	 * @param value the value
	 */
	public void defaultValue(String path, Object value){
		if(!contains(path)){
			this.set(path, value);
			save();
		}
	}

	/**
	 * Loads the config.
	 */
	public void load(){
		try{
			super.load(file);
		} catch(IOException | InvalidConfigurationException e){
			throw new RuntimeException("Could not save config!");
		}
	}

	/**
	 * Saves the config.
	 */
	public void save(){
		try{
			super.save(file);
		} catch(IOException e){
			throw new RuntimeException("Could not save config!");
		}
	}

	/**
	 * Reloads the config.
	 */
	public void reload(){
		load();
		save();
		config = YamlConfiguration.loadConfiguration(file);
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
	 * Returns the name of the config
	 *
	 * @return the name
	 */
	public String name(){
		final String fileName = file.getName();
		final int dotIndex = fileName.lastIndexOf('.');
		return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
	}

	/**
	 * Returns the file the config is stored in
	 *
	 * @return the file
	 */
	public File file(){
		return file;
	}

	/**
	 * Returns the config object
	 *
	 * @return the yaml configuration
	 */
	public YamlConfiguration config(){
		return config;
	}
}
