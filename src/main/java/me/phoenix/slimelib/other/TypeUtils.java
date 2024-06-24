package me.phoenix.slimelib.other;

/**
 * Utility class containing methods for converting data types from one form to another.
 */
public class TypeUtils {

    private TypeUtils() {
        throw new IllegalStateException("Utility class");
    }

	/**
	 * Converts a String to a byte.
	 *
	 * @param str the String to be converted
	 *
	 * @return the byte value of the String, or -1 if conversion fails
	 */
	public static byte asByte(String str) {
        try {
            return Byte.parseByte(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

	/**
	 * Converts a String to a short.
	 *
	 * @param str the String to be converted
	 *
	 * @return the short value of the String, or -1 if conversion fails
	 */
	public static short asShort(String str) {
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

	/**
	 * Converts a String to an integer.
	 *
	 * @param str the String to be converted
	 *
	 * @return the integer value of the String, or -1 if conversion fails
	 */
	public static int asInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

	/**
	 * Converts a String to a long.
	 *
	 * @param str the String to be converted
	 *
	 * @return the long value of the String, or -1 if conversion fails
	 */
	public static long asLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

	/**
	 * Converts a String to a float.
	 *
	 * @param str the String to be converted
	 *
	 * @return the float value of the String, or -1 if conversion fails
	 */
	public static float asFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

	/**
	 * Converts a String to a double.
	 *
	 * @param str the String to be converted
	 *
	 * @return the double value of the String, or -1 if conversion fails
	 */
	public static double asDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

	/**
	 * Converts a String to a boolean.
	 *
	 * @param str the String to be converted
	 *
	 * @return true if the String is "true" (case-insensitive), false otherwise
	 */
	public static boolean asBoolean(String str) {
        return Boolean.parseBoolean(str);
    }

	/**
	 * Converts an Object to a String.
	 *
	 * @param value the Object value to be converted
	 *
	 * @return the String representation of the object
	 */
	public static String asString(Object value) {
        return String.valueOf(value);
    }
}
