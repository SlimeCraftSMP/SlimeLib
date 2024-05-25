package me.phoenix.slimelib.world;

import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Utilities handling dimensions.
 */
public class WorldUtils {

	private WorldUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Checks if the entity is in the overworld.
	 *
	 * @param entity the entity to check for
	 *
	 * @return true if entity is in the overworld, false otherwise
	 */
	public static boolean inOverworld(Entity entity) {
        return isOverworld(entity.getWorld());
    }

	/**
	 * Checks if the world is overworld.
	 *
	 * @param world the world to check for
	 *
	 * @return true if yes, false otherwise
	 */
	public static boolean isOverworld(World world) {
        return world.getEnvironment() == World.Environment.NORMAL;
    }

	/**
	 * Checks if the entity is in the nether.
	 *
	 * @param entity the entity to check for
	 *
	 * @return true if entity is in the nether, false otherwise
	 */
	public static boolean inNether(Entity entity) {
        return isNether(entity.getWorld());
    }

	/**
	 * Checks if the world is nether.
	 *
	 * @param world the world to check for
	 *
	 * @return true if yes, false otherwise
	 */
	public static boolean isNether(World world) {
        return world.getEnvironment() == World.Environment.NETHER;
    }

	/**
	 * Checks if the entity is in the end.
	 *
	 * @param entity the entity to check for
	 *
	 * @return true if entity is in the end, false otherwise
	 */
	public static boolean inEnd(Entity entity) {
        return isEnd(entity.getWorld());
    }

	/**
	 * Checks if the world is end.
	 *
	 * @param world the world to check for
	 *
	 * @return true if yes, false otherwise
	 */
	public static boolean isEnd(World world) {
        return world.getEnvironment() == World.Environment.THE_END;
    }
}
