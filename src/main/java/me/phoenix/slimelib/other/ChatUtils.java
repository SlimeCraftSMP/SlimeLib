package me.phoenix.slimelib.other;

import me.phoenix.slimelib.visual.Styles;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

/**
 * Utilities for handling chat messages.
 */
public class ChatUtils {

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
        player.sendMessage(
                Styles.fix()
                .append(prefix)
                .append(msg)
        );
    }
}
