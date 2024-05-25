package me.phoenix.slimelib.slimefun.electric;

import me.phoenix.slimelib.visual.Styles;
import net.kyori.adventure.text.Component;

import java.text.DecimalFormat;

/**
 * Utilities for formatting lore.
 */
public class MachineLore{

	private MachineLore() {
		throw new IllegalStateException("Utility class");
	}

	private static final DecimalFormat FORMAT = new DecimalFormat("###,###,###,###,###,###.#");
	private static final String PREFIX = "<dark_gray>⇨</dark_gray><yellow> ⚡ </yellow><gray>";
	private static final String SUFFIX = "</gray>";

	/**
	 * Creates lore for power generated per tick.
	 *
	 * @param powerPerTick the power per tick
	 *
	 * @return the component
	 */
	public static Component powerPerTick(int powerPerTick) {
		return Styles.text(PREFIX + format(powerPerTick) + " J/t" + SUFFIX);
	}

	/**
	 * Creates lore for power buffer.
	 *
	 * @param buffer the buffer
	 *
	 * @return the component
	 */
	public static Component powerBuffer(int buffer) {
		return Styles.text(PREFIX + format(buffer) + " J Buffer" + SUFFIX);
	}

	/**
	 * Creates lore for power.
	 *
	 * @param power the power
	 *
	 * @return the component
	 */
	public static Component power(int power) {
		return Styles.text(PREFIX + format(power) + " J" + SUFFIX);
	}

	/**
	 * Creates lore for speed.
	 *
	 * @param speed the speed
	 *
	 * @return the component
	 */
	public static Component speed(int speed) {
		return Styles.text(PREFIX + "Speed: <aqua>" + format(speed) + "x</aqua>" + SUFFIX);
	}

	/**
	 * Formats the number into the decimal format.
	 *
	 * @param number the number
	 *
	 * @return the string
	 */
	public static String format(double number) {
		return FORMAT.format(number);
	}
}
