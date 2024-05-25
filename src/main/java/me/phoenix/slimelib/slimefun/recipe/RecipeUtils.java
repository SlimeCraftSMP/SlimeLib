package me.phoenix.slimelib.slimefun.recipe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Utilities for recipe handling.
 */
public class RecipeUtils{

	private RecipeUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Build 3 x 3 recipe using a Material.
	 *
	 * @param material the material
	 *
	 * @return the 3 x 3 item recipe
	 */
	public static ItemStack[] build3x3Recipe(Material material) {
		return build3x3Recipe(new ItemStack(material));
	}

	/**
	 * Build 3 x 3 recipe using an ItemStack.
	 *
	 * @param item the item
	 *
	 * @return the 3 x 3 item recipe
	 */
	public static ItemStack[] build3x3Recipe(ItemStack item) {
		return new ItemStack[] {item, item, item, item, item, item, item, item, item};
	}

	/**
	 * Build a ring recipe using a Material. A "ring recipe" is a recipe where every slot has the same item except the middle one which is empty.
	 *
	 * @param material the material
	 *
	 * @return the ring item recipe
	 */
	public static ItemStack[] buildRingRecipe(Material material) {
		return buildRingRecipe(new ItemStack(material));
	}

	/**
	 * Build a ring recipe using an ItemStack. A "ring recipe" is a recipe where every slot has the same item except the middle one which is empty.
	 *
	 * @param item the item
	 *
	 * @return the ring item recipe
	 */
	public static ItemStack[] buildRingRecipe(ItemStack item) {
		return new ItemStack[] {item, item, item, item, null, item, item, item, item};
	}

	/**
	 * Build a filled ring recipe using a Material. A "filled ring recipe" is a recipe where every slot has the same item except the middle one which is different.
	 *
	 * @param border the border item
	 * @param centre the centre item
	 *
	 * @return the filled ring item recipe
	 */
	public static ItemStack[] buildFilledRingRecipe(Material border, Material centre) {
		return buildFilledRingRecipe(new ItemStack(border), new ItemStack(centre));
	}

	/**
	 * Build a filled ring recipe using an ItemStack. A "filled ring recipe" is a recipe where every slot has the same item except the middle one which is different.
	 *
	 * @param border the border item
	 * @param centre the centre item
	 *
	 * @return the filled ring item recipe
	 */
	public static ItemStack[] buildFilledRingRecipe(ItemStack border, ItemStack centre) {
		return new ItemStack[] {border, border, border, border, centre, border, border, border, border};
	}
}
