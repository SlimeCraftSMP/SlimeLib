package me.phoenix.slimelib.inventory;

import me.phoenix.slimelib.inventory.handler.MenuClickHandler;
import me.phoenix.slimelib.slimefun.block.container.UIComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Various utilities for menu management.
 */
public class MenuUtils{

	private static final MenuClickHandler EMPTY_CLICK_HANDLER = (player, menu, action, click, item, slot) -> false;

	private MenuUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Set the background of the menu.
	 *
	 * @param menu the menu
	 * @param slots the slots
	 */
	public static void background(Menu menu, int... slots) {
		fill(menu, UIComponent.BORDER.item(), slots);
	}

	/**
	 * Fill items in the menu.
	 *
	 * @param menu the menu
	 * @param material the material
	 * @param slots the slots
	 */
	public static void fill(Menu menu, Material material, int... slots) {
		fill(menu, new ItemStack(material), slots);
	}

	/**
	 * Fill items in the menu.
	 *
	 * @param menu the menu
	 * @param item the item
	 * @param slots the slots
	 */
	public static void fill(Menu menu, ItemStack item, int... slots) {
		for (int slot : slots) {
			menu.item(slot, item);
		}
	}

	/**
	 * Get a click handler that does nothing when an item is clicked.
	 *
	 * @return the menu click handler
	 */
	public static MenuClickHandler emptyClickHandler() {
		return EMPTY_CLICK_HANDLER;
	}
}
