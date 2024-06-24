package me.phoenix.slimelib.slimefun.block;

import javax.annotation.Nonnull;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.phoenix.slimelib.slimefun.block.container.MenuTemplate;
import me.phoenix.slimelib.item.CustomItem;
import me.phoenix.slimelib.visual.Styles;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;

/**
 * Represents a Block that has an in-built Menu.
 */
public class MenuContainer extends SlimefunItem {

	/**
	 * Instantiates a new Menu container.
	 *
	 * @param itemGroup the item group
	 * @param item the item
	 * @param recipeType the recipe type
	 * @param recipe the recipe
	 */
	public MenuContainer(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        new MenuTemplate(this);

        addItemHandler(
                new SimpleBlockBreakHandler() {
                    @Override
                    public void onBlockBreak(@Nonnull Block block) { onBreak(block);}
                },
                new BlockPlaceHandler(false) {
                    @Override
                    public void onPlayerPlace(@Nonnull BlockPlaceEvent event) {
                        onPlace(event);
                    }
                }
        );
    }

	/**
	 * Instantiates the UI.
	 *
	 * @param preset the preset
	 */
	public void setupPreset(BlockMenuPreset preset) {
        preset.drawBackground(border());
        preset.drawBackground(ChestMenuUtils.getInputSlotTexture(), inputBorder());
        preset.drawBackground(ChestMenuUtils.getOutputSlotTexture(), outputBorder());
        preset.addItem(statusSlot(), statusItem(), ChestMenuUtils.getEmptyClickHandler());
    }

	/**
	 * Get slots accessible by cargo.
	 *
	 * @param menu the menu
	 * @param flow the flow
	 * @param item the item
	 *
	 * @return the int [ ]
	 */
	public int[] accessSlots(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return switch (flow){
            case INSERT -> inputSlots();
            case WITHDRAW -> outputSlots();
        };
    }

	/**
	 * Handles block break event for the block.
	 *
	 * @param block the block
	 */
	public void onBreak(@Nonnull Block block) {
        final BlockMenu inv = BlockStorage.getInventory(block);
        final Location location = block.getLocation();

        if (inv != null) {
            inv.dropItems(location, inputSlots());
            inv.dropItems(location, outputSlots());
        }

        BlockStorage.clearBlockInfo(block);
    }

	/**
	 * Handles BlockPlaceEvent for the block.
	 *
	 * @param event the event
	 */
	public void onPlace(@Nonnull BlockPlaceEvent event) {
        // Do stuff
    }

	/**
	 * Updates the block.
	 *
	 * @param block the block
	 */
	public void updateBlock(Block block) {
        // Do Stuff
    }

	/**
	 * Execute code when a new instance is created.
	 *
	 * @param menu the menu
	 * @param block the block
	 */
	public void onNewInstance(BlockMenu menu, Block block) {
        // Do Stuff
    }

	/**
	 * Get border slots.
	 *
	 * @return the int [ ]
	 */
	public int[] border() {return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44};}

	/**
	 * Get input border slots.
	 *
	 * @return the int [ ]
	 */
	public int[] inputBorder(){
        return new int[]{9, 10, 11, 12, 18, 21, 27, 28, 29, 30};
    }

	/**
	 * Get output border slots.
	 *
	 * @return the int [ ]
	 */
	public int[] outputBorder() {
        return new int[]{14, 15, 16, 17, 23, 26, 32, 33, 34, 35};
    }

	/**
	 * Get input slots.
	 *
	 * @return the int [ ]
	 */
	public int[] inputSlots(){
        return new int[]{19, 20};
    }

	/**
	 * Get output slots.
	 *
	 * @return the int [ ]
	 */
	public int[] outputSlots(){
        return new int[]{24, 25};
    }

	/**
	 * Gets status slot.
	 *
	 * @return the status slot
	 */
	public int statusSlot() {return 22;}

	/**
	 * Gets status item.
	 *
	 * @return the status item
	 */
	public ItemStack statusItem() {return new CustomItem(Material.BARRIER, Styles.ERROR.apply("Please set a status bar."));}
}
