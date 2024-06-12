package me.phoenix.slimelib;

import me.phoenix.slimelib.registry.CommandRegistry;
import me.phoenix.slimelib.config.Config;
import me.phoenix.slimelib.inventory.MenuListener;
import me.phoenix.slimelib.metrics.MetricsService;
import me.phoenix.slimelib.metrics.chart.pie.SimplePie;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Main class of SlimeLib library.
 */
public final class SlimeLib extends JavaPlugin {

    // Important
    private static final SlimeLib instance = new SlimeLib();

    // Configuration
    private final Config config = new Config(this, "config.yml", -1);

    // Service
    private final MetricsService metrics = new MetricsService(this, 21985);

    // Registry
    private final CommandRegistry commandRegistry = new CommandRegistry();

    /**
     * Instance of SlimeLib.
     *
     * @return the SlimeLib instance
     */
    public static SlimeLib instance(){ return instance;}

    /**
     * Instance of SlimeLib's Config.
     *
     * @return the SlimeLib Config
     */
    public static Config config(){ return instance.config;}

    /**
     * Instance of CommandRegistry.
     *
     * @return the CommandRegistry instance
     */
    public static CommandRegistry commandRegistry(){ return instance.commandRegistry;}

    /**
     * SlimeLib's logger.
     *
     * @return the Logger
     */
    public static Logger logger(){ return instance.getLogger();}
    
    @Override
    public void onEnable() {
        sendStartupMessage();
        setupMetrics();
        setupEvents();
    }

    @Override
    public void onDisable() {
        cancelAllTasks();
        sendGoodByeMessage();
    }

    private void printLogo(){
        logger().info("███████╗██╗     ██╗███╗   ███╗███████╗██╗     ██╗██████╗");
        logger().info("██╔════╝██║     ██║████╗ ████║██╔════╝██║     ██║██╔══██╗");
        logger().info("███████╗██║     ██║██╔████╔██║█████╗  ██║     ██║██████╔╝");
        logger().info("╚════██║██║     ██║██║╚██╔╝██║██╔══╝  ██║     ██║██╔══██╗");
        logger().info("███████║███████╗██║██║ ╚═╝ ██║███████╗███████╗██║██████╔╝");
        logger().info("╚══════╝╚══════╝╚═╝╚═╝     ╚═╝╚══════╝╚══════╝╚═╝╚═════╝");
    }

    private void sendStartupMessage(){ printLogo(); }

    private void setupMetrics(){
        metrics.addCustomChart(
                new SimplePie("auto_update",
                        () -> config.booleanValue("options.auto-update") ? "Enabled" : "Disabled"
                )
        );
    }

    private void setupEvents(){
        new MenuListener(this);
    }

    private void cancelAllTasks(){
        Bukkit.getScheduler().cancelTasks(instance);
    }

    private void sendGoodByeMessage(){ printLogo(); }
}
