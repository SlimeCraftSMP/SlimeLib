package me.phoenix.slimelib.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * A Yaml config implementation.
 */
public class Config{
    private final JavaPlugin plugin;
    private final File file;
    private YamlConfiguration config;

    /**
     * Instantiates a new Config.
     *
     * @param plugin the plugin
     * @param file the file
     * @param configuration the configuration
     * @param savePeriod the save period (-1 to disable auto-save)
     */
    public Config(JavaPlugin plugin, File file, YamlConfiguration configuration, long savePeriod){
        this.plugin = plugin;
        this.file = file;
        this.config = configuration;
        if(savePeriod != -1){
            new PeriodicConfigSaver(this).runTaskTimer(plugin, 20, savePeriod);
        }
    }

    /**
     * Instantiates a new Config.
     *
     * @param plugin the plugin
     * @param file the file
     * @param savePeriod the save period (-1 to disable auto-save)
     */
    public Config(JavaPlugin plugin, File file, long savePeriod){
        this(plugin, file, YamlConfiguration.loadConfiguration(file), savePeriod);
    }

    /**
     * Instantiates a new Config.
     *
     * @param plugin the plugin
     * @param path the path
     * @param savePeriod the save period (-1 to disable auto-save)
     */
    public Config(JavaPlugin plugin, String path, long savePeriod){
        this(plugin, new File(plugin.getDataFolder(), path), savePeriod);
    }

    /**
     * Get the value at a particular path as an object.
     *
     * @param path the path
     *
     * @return the string
     */
    public Object value(String path){
        return this.config.get(path);
    }

    /**
     * Get the value at a particular path as a string.
     *
     * @param path the path
     *
     * @return the string
     */
    public String stringValue(String path){
        return this.config.getString(path);
    }

    /**
     * Get the value at a particular path as a boolean.
     *
     * @param path the path
     *
     * @return the string
     */
    public boolean booleanValue(String path){
        return this.config.getBoolean(path);
    }

    /**
     * Set the value at a particular path.
     *
     * @param path the path
     * @param value the value
     */
    public void value(String path, Object value){
        this.config.set(path, value);
    }

    /**
     * Set the default value at a particular path.
     *
     * @param path the path
     * @param value the value
     */
    public void defaultValue(String path, Object value){
        if(!contains(path)){
            value(path, value);
        }
    }

    /**
     * Checks if the config contains a particular path.
     *
     * @param path the path
     *
     * @return true if it contains that path, false otherwise
     */
    public boolean contains(String path){
        return config.contains(path);
    }

    /**
     * Reloads the config.
     */
    public void reload(){
        this.config = YamlConfiguration.loadConfiguration(this.file);
        save();
    }

    /**
     * Saves the config.
     */
    public void save(){
        try{
            this.config.save(file);
        } catch(IOException ignored){ }
    }

    /**
     * Get the JavaPlugin that created this config.
     *
     * @return the plugin
     */
    public JavaPlugin plugin(){
        return this.plugin;
    }

    /**
     * Get the File object.
     *
     * @return the file
     */
    public File file(){
        return this.file;
    }

    /**
     * Get the YamlConfiguration object.
     *
     * @return the yaml configuration
     */
    public YamlConfiguration config(){
        return this.config;
    }
}
