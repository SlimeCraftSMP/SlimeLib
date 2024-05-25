package me.phoenix.slimelib.inventory.handler;

import me.phoenix.slimelib.inventory.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Handles close events of a menu.
 */
@FunctionalInterface
public interface MenuCloseHandler{

	/**
	 * Called when a menu is closed.
	 *
	 * @param player the player
	 * @param menu the menu
	 * @param reason the reason
	 */
	void onClose(Player player, Menu menu, InventoryCloseEvent.Reason reason);
}
