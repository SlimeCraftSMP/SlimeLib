package me.phoenix.slimelib.slimefun.item;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.PiglinBarterDrop;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * SlimefunItem dropped by bartering with a piglin.
 */
public class PiglinDropItem extends UnplaceableBlock implements PiglinBarterDrop {

	/**
	 * The chance that the piglin drops it on bartering.
	 */
	public final ItemSetting<Integer> chance = new IntRangeSetting(this, "trade-chance", 0, 50, 100);

	/**
	 * Instantiates a new Piglin drop item.
	 *
	 * @param itemGroup the item group
	 * @param item the item
	 * @param type the type
	 * @param recipe the recipe
	 * @param chance the chance
	 */
	public PiglinDropItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, int chance) {
        super(itemGroup, item, type, recipe);

        addItemSetting(this.chance);
    }

	/**
	 * Instantiates a new Piglin drop item.
	 *
	 * @param itemGroup the item group
	 * @param item the item
	 * @param type the type
	 * @param recipe the recipe
	 * @param output the output
	 */
	public PiglinDropItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, @Nullable ItemStack output) {
        super(itemGroup, item, type, recipe, output);

        addItemSetting(this.chance);
    }

    @Override
    public int getBarteringLootChance() {
        return this.chance.getValue();
    }

	/**
	 * Sets the default chance.
	 *
	 * @param chance the chance
	 */
	public void chance(int chance){
		if(this.chance.getValue().equals(this.chance.getDefaultValue())){
			this.chance.update(chance);
		}
	}
}
