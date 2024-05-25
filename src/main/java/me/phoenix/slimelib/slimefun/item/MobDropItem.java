package me.phoenix.slimelib.slimefun.item;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.settings.IntRangeSetting;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RandomMobDrop;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/**
 * SlimefunItem dropped by killing a mob.
 */
public class MobDropItem extends UnplaceableBlock implements RandomMobDrop {

	/**
	 * The chance that the mob drops it on death.
	 */
	public final ItemSetting<Integer> chance = new IntRangeSetting(this, "drop-chance", 0, 50, 100);

	/**
	 * Instantiates a new Mob drop item.
	 *
	 * @param itemGroup the item group
	 * @param item the item
	 * @param type the type
	 * @param recipe the recipe
	 */
	public MobDropItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(itemGroup, item, type, recipe);

        addItemSetting(this.chance);
    }

	/**
	 * Instantiates a new Mob drop item.
	 *
	 * @param itemGroup the item group
	 * @param item the item
	 * @param type the type
	 * @param recipe the recipe
	 * @param output the output
	 */
	public MobDropItem(ItemGroup itemGroup, SlimefunItemStack item, RecipeType type, ItemStack[] recipe, @Nullable ItemStack output) {
        super(itemGroup, item, type, recipe, output);

        addItemSetting(this.chance);
    }

    @Override
    public int getMobDropChance() {
        return chance.getValue();
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
