package me.phoenix.slimelib.other;

import org.bukkit.Bukkit;

/**
 * This enum holds all supported versions of Minecraft.
 */
public enum Versions{

	/**
	 * This constant represents Minecraft (Java Edition) Version 1.20.
	 * ("The Trails and Tales Update")
	 */
	MINECRAFT_1_20(20),

	/**
	 * This constant represents Minecraft (Java Edition) Version 1.21.
	 * ("The Tricky Trials Update")
	 */
	MINECRAFT_1_21(21),

	/**
	 * This constant represents an exceptional state where we couldn't determine the Minecraft Version in use.
	 */
	UNKNOWN(-1);

	private final int version;

	/**
	 * This constructs a new {@link Versions} with the given version.
	 *
	 * @param version The version of this {@link Versions}
	 */
	Versions(int version){
		this.version = version;
	}

	/**
	 * Parses a Minecraft version string and extracts the version number.
	 *
	 * @param versionString A string representing a Minecraft version, e.g. "1.20.x". 		You can obtain the version number by doing {@link Versions#serverVersion()}
	 * @param isMinor If true returns the minor Minecraft version, else returns the major Minecraft version
	 *
	 * @return The version number extracted from the input string.
	 */
	public static int minecraftVersion(String versionString, boolean isMinor){
		String[] parts = versionString.split("\\.");
		if(isMinor){
			return Integer.parseInt(parts[2]);
		} else{
			return Integer.parseInt(parts[1]);
		}
	}

	/**
	 * Parses a Minecraft version string and extracts the major version number.
	 *
	 * @param versionString A string representing a Minecraft version, e.g. "1.20.x". 		You can obtain the version number by doing {@link Versions#serverVersion()}
	 *
	 * @return The major version number extracted from the input string.
	 */
	public static int majorVersion(String versionString){
		return minecraftVersion(versionString, false);
	}

	/**
	 * Parses a Minecraft version string and extracts the minor version number.
	 *
	 * @param versionString A string representing a Minecraft version, e.g. "1.20.x". 		You can obtain the version number by doing {@link Versions#serverVersion()}
	 *
	 * @return The minor version number extracted from the input string.
	 */
	public static int minorVersion(String versionString){
		return minecraftVersion(versionString, true);
	}

	/**
	 * Retrieves the Server software name currently running on the server.
	 *
	 * @return A string representing the name, e.g., "Paper".
	 */
	public static String serverSoftware(){
		return Bukkit.getName();
	}

	/**
	 * Retrieves the Server version currently running on the server.
	 *
	 * @return A string representing the version, e.g., "git-Paper-build (MC: 1.20.x)".
	 */
	public static String serverVersion(){
		return Bukkit.getVersion();
	}

	/**
	 * Retrieves the Minecraft version currently running on the server.
	 *
	 * @return A string representing the version, e.g., "1.20.x".
	 */
	public static String minecraftVersion(){
		return Bukkit.getMinecraftVersion();
	}

	/**
	 * This returns the version of this {@link Versions} in a format suitable for comparison.
	 *
	 * @return The version of this {@link Versions}
	 */
	public int version(){
		return this.version;
	}

	/**
	 * This tests if the given minecraft version string matches with this {@link Versions}.
	 * <p>
	 * You can obtain the version number by doing {@link Versions#majorVersion(String)}.
	 * It is equivalent to the "major" version
	 * <p>
	 * Example: {@literal "1.13"} returns {@literal 13}
	 *
	 * @param versionString The {@link String} version to match
	 *
	 * @return Whether this {@link Versions} matches the specified version id
	 */
	public boolean isMinecraftVersion(String versionString){
		return this.version != -1 && this.version == majorVersion(versionString);
	}

	/**
	 * Checks whether the server version is newer than the specified {@link Versions}.
	 * An unknown version will default to {@literal false}.
	 *
	 * @return If the server version is newer than the given {@link Versions}
	 */
	public boolean isAtLeast(){
		if(this == UNKNOWN){
			return false;
		}

		return majorVersion(serverVersion()) >= this.version();
	}

}
