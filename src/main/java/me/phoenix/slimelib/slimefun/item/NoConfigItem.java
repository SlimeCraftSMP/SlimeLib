package me.phoenix.slimelib.slimefun.item;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotConfigurable;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * SlimefunItem that cannot be configured.
 */
public class NoConfigItem extends SlimefunItem implements NotConfigurable {

	/**
	 * Instantiates a new No config item.
	 *
	 * @param itemGroup the item group
	 * @param item the item
	 * @param type the type
	 * @param recipe the recipe
	 */
	public NoConfigItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(itemGroup, item, type, recipe);
    }

	/**
	 * Instantiates a new No config item.
	 *
	 * @param itemGroup the item group
	 * @param item the item
	 * @param type the type
	 * @param recipe the recipe
	 * @param output the output
	 */
	public NoConfigItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, @Nullable ItemStack output) {
        super(itemGroup, item, type, recipe, output);
    }
}
