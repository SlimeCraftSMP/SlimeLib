package me.phoenix.slimelib.other;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utilities for randomization.
 */
public class RandomUtils {

	private RandomUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * The constant random.
	 */
	public static final ThreadLocalRandom random = ThreadLocalRandom.current();

	/**
	 * Random choice.
	 *
	 * @param <T> the type parameter
	 * @param list the list
	 *
	 * @return a random item from the list
	 */
	public static <T> T randomChoice(List<T> list) {
        return list.get(randomInt(0, list.size()));
    }

	/**
	 * Random integer.
	 *
	 * @param initial the initial
	 * @param end the end
	 *
	 * @return a random integer
	 */
	public static int randomInt(int initial, int end) {
        return random.nextInt(initial, end);
    }

	/**
	 * Random floating point.
	 *
	 * @param initial the initial
	 * @param end the end
	 *
	 * @return a random floating point
	 */
	public static double randomDouble(double initial, double end) {
        return random.nextDouble(initial, end);
    }
}
