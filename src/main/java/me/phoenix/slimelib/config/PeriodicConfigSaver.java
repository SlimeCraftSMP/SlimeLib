package me.phoenix.slimelib.config;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * A BukkitRunnable to automatically save configs based on an interval.
 */
public class PeriodicConfigSaver extends BukkitRunnable {
    private final Config config;

    /**
     * Instantiates a new PeriodicConfigSaver.
     *
     * @param config the config
     */
    public PeriodicConfigSaver(Config config) {
        this.config = config;
    }

    @Override
    public void run() {
        config.save();
    }
}
