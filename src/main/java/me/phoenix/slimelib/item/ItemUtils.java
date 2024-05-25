package me.phoenix.slimelib.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

/**
 * Utilities for item handling.
 */
public class ItemUtils {

	/**
	 * Create a potion item stack.
	 *
	 * @param potionType the potion type
	 *
	 * @return the item stack
	 */
	public static ItemStack createPotion(PotionType potionType){
        final ItemStack itemStack = new ItemStack(Material.POTION);
        final PotionMeta meta = (PotionMeta) itemStack.getItemMeta();

		meta.setBasePotionType(potionType);
        itemStack.setItemMeta(meta);

        return itemStack.clone();
    }
}
