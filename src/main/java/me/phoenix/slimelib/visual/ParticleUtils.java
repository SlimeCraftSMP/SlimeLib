package me.phoenix.slimelib.visual;

import me.phoenix.slimelib.world.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities to create visual effects using particles.
 */
public final class ParticleUtils {

	private ParticleUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Display particle randomly.
	 *
	 * @param entity the entity
	 * @param particle the particle
	 * @param rangeRadius the range radius
	 */
	@ParametersAreNonnullByDefault
    public static void displayParticleRandomly(Entity entity, Particle particle, int rangeRadius) {
        displayParticleRandomly(entity.getLocation(), particle, rangeRadius, 8);
    }

	/**
	 * Display particle randomly.
	 *
	 * @param location the location
	 * @param particle the particle
	 * @param rangeRadius the range radius
	 */
	@ParametersAreNonnullByDefault
    public static void displayParticleRandomly(Location location, Particle particle, int rangeRadius) {
        displayParticleRandomly(location, particle, rangeRadius, 8);
    }

	/**
	 * Display particle randomly.
	 *
	 * @param entity the entity
	 * @param particle the particle
	 * @param rangeRadius the range radius
	 * @param numberOfParticles the number of particles
	 */
	@ParametersAreNonnullByDefault
    public static void displayParticleRandomly(Entity entity, Particle particle, int rangeRadius, int numberOfParticles) {
        displayParticleRandomly(entity.getLocation().clone().add(0, 1, 0), particle, rangeRadius, numberOfParticles);
    }

	/**
	 * Display particle randomly.
	 *
	 * @param location the location
	 * @param particle the particle
	 * @param rangeRadius the range radius
	 * @param numberOfParticles the number of particles
	 */
	@ParametersAreNonnullByDefault
    public static void displayParticleRandomly(Location location, Particle particle, int rangeRadius, int numberOfParticles) {
        for (int i = 0; i < numberOfParticles; i++) {
            location.getWorld().spawnParticle(particle, LocationUtils.randomLocation(location, rangeRadius), 1);
        }
    }

	/**
	 * Display particle randomly.
	 *
	 * @param entity the entity
	 * @param rangeRadius the range radius
	 * @param dustOptions the dust options
	 */
	@ParametersAreNonnullByDefault
    public static void displayParticleRandomly(Entity entity, int rangeRadius, Particle.DustOptions dustOptions) {
        displayParticleRandomly(entity.getLocation(), rangeRadius, 8, dustOptions);
    }

	/**
	 * Display particle randomly.
	 *
	 * @param entity the entity
	 * @param rangeRadius the range radius
	 * @param numberOfParticles the number of particles
	 * @param dustOptions the dust options
	 */
	@ParametersAreNonnullByDefault
    public static void displayParticleRandomly(Entity entity, int rangeRadius, int numberOfParticles, Particle.DustOptions dustOptions) {
        displayParticleRandomly(entity.getLocation(), rangeRadius, numberOfParticles, dustOptions);
    }

	/**
	 * Display particle randomly.
	 *
	 * @param location the location
	 * @param rangeRadius the range radius
	 * @param numberOfParticles the number of particles
	 * @param dustOptions the dust options
	 */
	@ParametersAreNonnullByDefault
    public static void displayParticleRandomly(Location location, int rangeRadius, int numberOfParticles, Particle.DustOptions dustOptions) {
        for (int i = 0; i < numberOfParticles; i++) {
            location.getWorld().spawnParticle(Particle.REDSTONE, LocationUtils.randomLocation(location, rangeRadius), 1, dustOptions);
        }
    }

	/**
	 * Draw line.
	 *
	 * @param particle the particle
	 * @param start the start
	 * @param end the end
	 * @param space the space
	 */
	@ParametersAreNonnullByDefault
    public static void drawLine(Particle particle, Location start, Location end, double space) {
        drawLine(particle, start, end, space, null);
    }

	/**
	 * Draw line.
	 *
	 * @param dustOptions the dust options
	 * @param start the start
	 * @param end the end
	 * @param space the space
	 */
	@ParametersAreNonnullByDefault
    public static void drawLine(Particle.DustOptions dustOptions, Location start, Location end, double space) {
        drawLine(Particle.REDSTONE, start, end, space, dustOptions);
    }

	/**
	 * Draw line.
	 *
	 * @param particle the particle
	 * @param start the start
	 * @param end the end
	 * @param space the space
	 * @param dustOptions the dust options
	 */
	@ParametersAreNonnullByDefault
    public static void drawLine(Particle particle, Location start, Location end, double space, @Nullable Particle.DustOptions dustOptions) {
        double currentPoint = 0;
        final Vector startVector = start.toVector();
        final Vector vector = end.toVector().clone().subtract(startVector).normalize().multiply(space);
        final World world = start.getWorld();

        while (currentPoint < start.distance(end)) {
            if (dustOptions != null) {
                world.spawnParticle(
                        particle,
                        startVector.getX(),
                        startVector.getY(),
                        startVector.getZ(),
                        1,
                        dustOptions
                );
            } else {
                world.spawnParticle(
                        particle,
                        startVector.getX(),
                        startVector.getY(),
                        startVector.getZ(),
                        1
                );
            }
            currentPoint += space;
            startVector.add(vector);
        }
    }

	/**
	 * Gets a list of locations from start to end in a straight line.
	 *
	 * @param start the start
	 * @param end the end
	 * @param space the space
	 *
	 * @return the list of locations
	 */
	@Nonnull
    @ParametersAreNonnullByDefault
    public static List<Location> getLine(Location start, Location end, double space) {
        double currentPoint = 0;
        final Vector startVector = start.toVector();
        final Vector vector = end.toVector().clone().subtract(startVector).normalize().multiply(space);
        final World world = start.getWorld();

        final List<Location> locations = new ArrayList<>();

        while (currentPoint < start.distance(end)) {
            locations.add(new Location(
                    world,
                    startVector.getX(),
                    startVector.getY(),
                    startVector.getZ()
            ));

            currentPoint += space;
            startVector.add(vector);
        }
        return locations;
    }

	/**
	 * Draw cube.
	 *
	 * @param particle the particle
	 * @param corner1 the corner 1
	 * @param corner2 the corner 2
	 * @param space the space
	 */
	@ParametersAreNonnullByDefault
    public static void drawCube(Particle particle, Location corner1, Location corner2, double space) {
        drawCube(particle, corner1, corner2, space, null);
    }

	/**
	 * Draw cube.
	 *
	 * @param dustOptions the dust options
	 * @param corner1 the corner 1
	 * @param corner2 the corner 2
	 * @param space the space
	 */
	@ParametersAreNonnullByDefault
    public static void drawCube(Particle.DustOptions dustOptions, Location corner1, Location corner2, double space) {
        drawCube(Particle.REDSTONE, corner1, corner2, space, dustOptions);
    }

	/**
	 * Draw cube.
	 *
	 * @param particle the particle
	 * @param corner1 the corner 1
	 * @param corner2 the corner 2
	 * @param particleDistance the particle distance
	 * @param dustOptions the dust options
	 */
	@ParametersAreNonnullByDefault
    public static void drawCube(Particle particle, Location corner1, Location corner2, double particleDistance, @Nullable Particle.DustOptions dustOptions) {
        final World world = corner1.getWorld();
        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());
        double maxX = Math.max(corner1.getX(), corner2.getX());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        for (double x = minX; x <= maxX; x += particleDistance) {
            for (double y = minY; y <= maxY; y += particleDistance) {
                for (double z = minZ; z <= maxZ; z += particleDistance) {
                    int components = 0;
                    if (x == minX || x == maxX) {
                        components++;
                    }
                    if (y == minY || y == maxY) {
                        components++;
                    }
                    if (z == minZ || z == maxZ) {
                        components++;
                    }
                    if (components >= 2) {
                        if (dustOptions != null) {
                            world.spawnParticle(particle, x, y, z, 1, dustOptions);
                        } else {
                            world.spawnParticle(particle, x, y, z, 1);
                        }
                    }
                }
            }
        }
    }
}
