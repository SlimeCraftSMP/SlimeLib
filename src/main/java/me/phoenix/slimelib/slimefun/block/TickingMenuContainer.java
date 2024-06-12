package me.phoenix.slimelib.slimefun.block;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * Represents a Block that has an in-built Menu which ticks periodically.
 */
public class TickingMenuContainer extends MenuContainer {

	/**
	 * Instantiates a new Ticking menu container.
	 *
	 * @param itemGroup the item group
	 * @param item the item
	 * @param recipeType the recipe type
	 * @param recipe the recipe
	 */
	public TickingMenuContainer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return sync();
            }
            @Override
            public void tick(@Nonnull Block block, SlimefunItem item, @Deprecated Config data) {TickingMenuContainer.this.onTick(block);}
        });
    }

	/**
	 * If the block runs synchronized or not.
	 *
	 * @return true if its synchronized, false otherwise
	 */
	public boolean sync() {
        return false;
    }

	/**
	 * Executes this method per Slimefun tick.
	 *
	 * @param block the block
	 */
    public void onTick(@Nonnull Block block) {
        //Do Stuff
    }
}
