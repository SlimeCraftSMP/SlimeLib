package me.phoenix.slimelib.item;

import me.phoenix.slimelib.visual.Styles;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Utilities for handling item lore.
 */
public class LoreUtils{

	private LoreUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Apply new lore into the ItemStack after editing the ItemMeta.
	 *
	 * @param item the item
	 * @param newLine the new line
	 * @param line the line
	 */
	public static void applyNewLore(ItemStack item, String newLine, int line) {
		final ItemMeta meta = item.getItemMeta();
		if(meta == null){
			return;
		}
		item.setItemMeta(setNewLore(meta, newLine, line));
	}

	/**
	 * Sets new lore into the meta without applying it to the ItemStack.
	 *
	 * @param meta the meta
	 * @param newLine the new line
	 * @param line the line
	 *
	 * @return the ItemMeta with new lore
	 */
	public static ItemMeta setNewLore(ItemMeta meta, String newLine, int line) {
		final List<Component> lore = meta.lore();
		if(lore == null){
			return meta;
		}
		lore.set(line, Styles.LORE.apply(newLine));
		meta.lore(lore);
		return meta;
	}
}
