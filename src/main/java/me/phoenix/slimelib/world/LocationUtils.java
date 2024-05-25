package me.phoenix.slimelib.world;

import me.phoenix.slimelib.other.RandomUtils;
import org.bukkit.Location;
import javax.annotation.Nonnull;

/**
 * Utilities handling Location.
 */
public final class LocationUtils {

	private LocationUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates a random location from a given centre and range.
	 *
	 * @param centreLocation the centre location
	 * @param range the range
	 *
	 * @return the new location
	 */
	@Nonnull
    public static Location randomLocation(Location centreLocation, int range) {
        return randomLocation(centreLocation, range, range, range);
    }

	/**
	 * Creates a random location from a given centre and range.
	 *
	 * @param centreLocation the centre location
	 * @param rangeX the range x
	 * @param rangeY the range y
	 * @param rangeZ the range z
	 *
	 * @return the new location
	 */
	@Nonnull
    public static Location randomLocation(Location centreLocation, int rangeX, int rangeY, int rangeZ) {
        final double randomX = RandomUtils.randomInt(-rangeX, rangeX + 1);
        final double randomY = RandomUtils.randomInt(-rangeY, rangeY + 1);
        final double randomZ = RandomUtils.randomInt(-rangeZ, rangeZ + 1);
        return centreLocation.clone().add(randomX, randomY, randomZ);
    }

	/**
	 * Centres the given location.
	 *
	 * @param location the location
	 *
	 * @return the new location
	 */
	@Nonnull
    public static Location centre(Location location) {
        return location.clone().add(0.5, 0.5, 0.5);
    }
}
