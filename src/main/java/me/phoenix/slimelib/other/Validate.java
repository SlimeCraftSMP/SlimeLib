package me.phoenix.slimelib.other;

/**
 * Utility to validate strings.
 */
public class Validate{
	private Validate() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Checks if the string is null or not.
	 *
	 * @param string the string
	 *
	 * @return true if the string is not null, false otherwise
	 */
	public static boolean notNull(String string){
		return string != null && !string.equalsIgnoreCase("null");
	}
}
