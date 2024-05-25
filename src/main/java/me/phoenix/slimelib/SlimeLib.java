package me.phoenix.slimelib;

import me.phoenix.slimelib.config.Config;
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
    private static SlimeLib instance;

    // Configuration
    private final Config config = new Config(this, "config.yml", -1);

    // Service
    private final MetricsService metrics = new MetricsService(this, 21985);

    /**
     * Instance of SlimeLib.
     */
    public SlimeLib(){ instance = this;}

    /**
     * Instance of SlimeLib.
     *
     * @return the SlimeLib instance
     */
    public static SlimeLib instance(){ return instance;}

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

    private void cancelAllTasks(){
        Bukkit.getScheduler().cancelTasks(instance);
    }

    private void sendGoodByeMessage(){ printLogo(); }
}