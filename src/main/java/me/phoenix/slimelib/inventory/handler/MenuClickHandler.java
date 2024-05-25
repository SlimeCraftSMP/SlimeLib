package me.phoenix.slimelib.inventory.handler;

import me.phoenix.slimelib.inventory.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

/**
 * Handles click events within a menu.
 */
@FunctionalInterface
public interface MenuClickHandler{

	/**
	 * Called when an item in the menu is clicked.
	 *
	 * @param player the player
	 * @param menu the menu
	 * @param action the action
	 * @param clickType the click type
	 * @param item the item
	 * @param slot the slot
	 *
	 * @return true if the even should be cancelled, false otherwise.
	 */
	boolean onItemClick(Player player, Menu menu, InventoryAction action, ClickType clickType, ItemStack item, int slot);
}
