package me.phoenix.slimelib.inventory;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Utilities for handling inventories.
 */
public class InventoryUtils{

	/**
	 * Find a particular Slimefun item in the inventory.
	 *
	 * @param inventory the inventory
	 * @param slimefunItem the Slimefun item
	 *
	 * @return the slot where the item is present, returns -1 if not present
	 */
	public static int findSfItem(Inventory inventory, ItemStack slimefunItem) {
		for (int i = 0; i <= inventory.getSize(); i++) {
			if(SlimefunUtils.isItemSimilar(inventory.getItem(i), slimefunItem, false, false, false)){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Consume a particular Slimefun item in the inventory.
	 *
	 * @param inventory the inventory
	 * @param slimefunItem the Slimefun item
	 * @param amount the amount of items to consume
	 *
	 * @return true if the item was consumed successfully, false otherwise
	 */
	public static boolean consumeSfItem(Inventory inventory, ItemStack slimefunItem, int amount) {
		final int slot = findSfItem(inventory, slimefunItem);
		if(slot == -1){
			return false;
		}
		final ItemStack item = inventory.getItem(slot);
		if(item.getAmount() < amount){
			return false;
		}
		item.subtract(amount);
		return true;
	}
}
