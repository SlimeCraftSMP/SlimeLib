package me.phoenix.slimelib.other;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import java.util.function.Consumer;

/**
 * Utilities for scheduling tasks
 */
public class SchedulerUtils{

	/**
	 * Run a task later (sync).
	 *
	 * @param plugin the plugin
	 * @param task the task
	 * @param after the time after which the task should run (in Minecraft ticks)
	 */
	public static void runLater(JavaPlugin plugin, Consumer<BukkitTask> task, int after) {
        Bukkit.getScheduler().runTaskLater(plugin, task, after);
    }

	/**
	 * Run a task later (async).
	 *
	 * @param plugin the plugin
	 * @param task the task
	 * @param after the time after which the task should run (in Minecraft ticks)
	 */
	public static void runLaterAsync(JavaPlugin plugin, Consumer<BukkitTask> task, int after) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, after);
    }

	/**
	 * Run a task later (sync).
	 *
	 * @param plugin the plugin
	 * @param task the task
	 * @param after the time after which the task should run (in Minecraft ticks)
	 */
	public static void runLater(JavaPlugin plugin, Runnable task, int after) {
        Bukkit.getScheduler().runTaskLater(plugin, task, after);
    }

	/**
	 * Run a task later (async).
	 *
	 * @param plugin the plugin
	 * @param task the task
	 * @param after the time after which the task should run (in Minecraft ticks)
	 */
	public static void runLaterAsync(JavaPlugin plugin, Runnable task, int after) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, after);
    }

	/**
	 * Repeat a task (sync).
	 *
	 * @param plugin the plugin
	 * @param runnable the runnable
	 * @param every the time after which the task should repeat (in Minecraft ticks)
	 */
	public static void repeatTask(JavaPlugin plugin, Runnable runnable, int every) {
		Bukkit.getScheduler().runTaskTimer(plugin, runnable, 20, every);
	}

	/**
	 * Repeat a task (sync).
	 *
	 * @param plugin the plugin
	 * @param runnable the runnable
	 * @param every the time after which the task should repeat (in Minecraft ticks)
	 */
	public static void repeatTaskAsync(JavaPlugin plugin, Runnable runnable, int every) {
		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, 20, every);
	}

	/**
	 * Repeat a code certain number of times (sync).
	 *
	 * @param plugin the plugin
	 * @param runnable the runnable
	 * @param times the times the code should run
	 * @param every the time after which the task should repeat (in Minecraft ticks)
	 */
	public static void repeatTask(JavaPlugin plugin, Runnable runnable, int times, int every) {
        new BukkitRunnable() {
            private int iterations = 0;

            @Override
            public void run() {
                if (iterations >= times) {
                    cancel();
                    return;
                }

                runnable.run();
                ++iterations;
            }
        }.runTaskTimer(plugin, 20, every);
    }

	/**
	 * Repeat a code certain number of times (async).
	 *
	 * @param plugin the plugin
	 * @param runnable the runnable
	 * @param times the times the code should run
	 * @param every the time after which the task should repeat (in Minecraft ticks)
	 */
	public static void repeatTaskAsync(JavaPlugin plugin, Runnable runnable, int times, int every) {
        new BukkitRunnable() {
            private int iterations = 0;

            @Override
            public void run() {
                if (iterations >= times) {
                    cancel();
                    return;
                }

                runnable.run();
                ++iterations;
            }
        }.runTaskTimerAsynchronously(plugin, 20, every);
    }

}
