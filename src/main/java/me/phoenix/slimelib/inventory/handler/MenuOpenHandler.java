package me.phoenix.slimelib.inventory.handler;

import me.phoenix.slimelib.inventory.Menu;
import org.bukkit.entity.Player;

/**
 * Handles open events of a menu.
 */
@FunctionalInterface
public interface MenuOpenHandler{

	/**
	 * Called when a menu is opened.
	 *
	 * @param player the player
	 * @param menu the menu
	 */
	void onOpen(Player player, Menu menu);
}
