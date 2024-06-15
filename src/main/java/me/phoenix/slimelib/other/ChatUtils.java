package me.phoenix.slimelib.other;

import me.phoenix.slimelib.visual.Styles;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

/**
 * Utilities for handling chat messages.
 */
public class ChatUtils {

	private ChatUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Send a message to a player.
	 *
	 * @param player the player
	 * @param prefix the prefix
	 * @param msg the msg
	 */
	public static void send(Player player, Component prefix, String msg) {
        send(player, prefix, Styles.text(msg));
    }

	/**
	 * Send a message to a player.
	 *
	 * @param player the player
	 * @param prefix the prefix
	 * @param msg the msg
	 */
	public static void send(Player player, Component prefix, Component msg) {
        player.sendMessage(prefix.append(msg));
    }

	/**
	 * Send a message to an audience.
	 *
	 * @param audience the audience
	 * @param prefix the prefix
	 * @param msg the msg
	 */
	public static void send(Audience audience, Component prefix, String msg) {
		send(audience, prefix, Styles.text(msg));
	}

	/**
	 * Send a message to an audience.
	 *
	 * @param audience the audience
	 * @param prefix the prefix
	 * @param msg the msg
	 */
	public static void send(Audience audience, Component prefix, Component msg) {
		audience.sendMessage(prefix.append(msg));
	}
}
