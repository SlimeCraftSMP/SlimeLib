package me.phoenix.slimelib.slimefun.electric;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.operations.FuelOperation;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import io.github.thebusybiscuit.slimefun4.utils.NumberUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import me.phoenix.slimelib.slimefun.block.TickingMenuContainer;
import me.phoenix.slimelib.slimefun.block.container.UIComponent;
import me.phoenix.slimelib.visual.Styles;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Modularized implementation of AGenerator.
 */
public class Generator extends TickingMenuContainer implements EnergyNetProvider, MachineProcessHolder<FuelOperation>, RecipeDisplayItem {

	/**
	 * The fuels that the generator can accept.
	 */
	public final Set<MachineFuel> FUELS = new HashSet<>();
	/**
	 * The generator processor.
	 */
	public final MachineProcessor<FuelOperation> PROCESSOR = new MachineProcessor<>(this);
	/**
	 * The energy capacity of the generator.
	 */
	public int capacity;
	/**
	 * The energy production of the generator.
	 */
	public int production;

	/**
	 * Instantiates a new Generator.
	 *
	 * @param itemGroup the item group
	 * @param item the item
	 * @param recipeType the recipe type
	 * @param recipe the recipe
	 * @param capacity the cap
	 * @param production the production
	 */
	protected Generator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int capacity, int production) {
        super(itemGroup, item, recipeType, recipe);

        this.capacity = capacity;
        this.production = production;

        PROCESSOR.setProgressBar(statusItem());

        registerFuels();
    }

	/**
	 * Register fuels.
	 */
	public void registerFuels() {}

	@Override
    public void onBreak(@Nonnull Block block) {
        final BlockMenu inv = BlockStorage.getInventory(block);
        final Location location = block.getLocation();

        if (inv != null) {
            inv.dropItems(location, inputSlots());
            inv.dropItems(location, outputSlots());
        }

        PROCESSOR.endOperation(block);
        BlockStorage.clearBlockInfo(block);
    }

    @Override
    @ParametersAreNonnullByDefault
    public int getGeneratedOutput(Location l, @Deprecated Config data) {
        final FuelOperation operation = PROCESSOR.getOperation(l);
        if(operation == null){
            return nullOperation(l);
        } else{
            return nonNullOperation(l, operation);
        }
    }

	/**
	 * Handles how an idle generator behaves. Generator is idle when there is no current operation.
	 *
	 * @param location the location
	 *
	 * @return power that the generator should generate
	 */
	public int nullOperation(Location location) {
        final BlockMenu inv = BlockStorage.getInventory(location);
        final Map<Integer, Integer> found = new HashMap<>();
        final MachineFuel fuel = findRecipe(inv, found);

        if (fuel != null) {
            if(shouldConsume()){
                for (Map.Entry<Integer, Integer> entry : found.entrySet()) {
                    inv.consumeItem(entry.getKey(), entry.getValue());
                }
            }

            final FuelOperation operation = new FuelOperation(fuel.getInput(), fuel.getOutput(), fuel.getTicks());
            PROCESSOR.startOperation(location, operation);
            PROCESSOR.updateProgressBar(inv, statusSlot(), operation);
        }

        return 0;
    }

	/**
	 * Handles how a working generator behaves. Generator is working when there is a current operation.
	 *
	 * @param location the location
	 * @param operation the operation
	 *
	 * @return power that the generator should generate
	 */
	public int nonNullOperation(Location location, FuelOperation operation) {
        if(operation.isFinished()){
            return onFinish(location, operation);
        } else {
            return progress(location, operation);
        }
    }

	/**
	 * Handles how a generator behaves when its current operation is over.
	 *
	 * @param location the location
	 * @param operation the operation
	 *
	 * @return power that the generator should generate
	 */
	public int onFinish(Location location, FuelOperation operation) {
        final BlockMenu inv = BlockStorage.getInventory(location);
        if (isBucket(operation.getIngredient())) {
            inv.pushItem(new ItemStack(Material.BUCKET), outputSlots());
        }
        inv.replaceExistingItem(statusSlot(), UIComponent.IDLE.item());
        PROCESSOR.endOperation(location);
        return 0;
    }

	/**
	 * Handles how a generator behaves when its current operation is in progress.
	 *
	 * @param location the location
	 * @param operation the operation
	 *
	 * @return power that the generator should generate
	 */
	public int progress(Location location, FuelOperation operation) {
        if (getCapacity() - getCharge(location) <= production()) {
            return 0;
        }

        operation.addProgress(1);
        PROCESSOR.updateProgressBar(BlockStorage.getInventory(location), statusSlot(), operation);
        return production();
    }

    private boolean isBucket(@Nullable ItemStack item) {
        if (item == null) {
            return false;
        }

        return item.getType() == Material.LAVA_BUCKET
                || SlimefunUtils.isItemSimilar(ItemStackWrapper.wrap(item), SlimefunItems.FUEL_BUCKET, false, false, false)
                || SlimefunUtils.isItemSimilar(ItemStackWrapper.wrap(item), SlimefunItems.OIL_BUCKET, false, false, false);
    }

	/**
	 * Find machine fuel to consume.
	 *
	 * @param menu the menu
	 * @param found the found
	 *
	 * @return the machine fuel
	 */
	public MachineFuel findRecipe(BlockMenu menu, Map<Integer, Integer> found) {
        for (MachineFuel fuel : FUELS) {
            for (int slot : inputSlots()) {
                if (fuel.test(menu.getItemInSlot(slot))) {
                    found.put(slot, fuel.getInput().getAmount());
                    return fuel;
                }
            }
        }

        return null;
    }

	/**
	 * Sets fuel.
	 *
	 * @param seconds the seconds
	 * @param input the input
	 * @param output the output
	 */
	public void fuel(int seconds, ItemStack input, ItemStack output) {FUELS.add(new MachineFuel(seconds, input, output));}

    @Override
    @Nonnull
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> list = new ArrayList<>();

        for (MachineFuel fuel : FUELS) {
            final ItemStack item = fuel.getInput().clone();
	        final ItemMeta im = item.getItemMeta();
	        final List<Component> lore = new ArrayList<>();
            lore.add(Styles.text("<dark_gray>⇨</dark_gray><gray>Lasts"+ NumberUtils.getTimeLeft(fuel.getTicks() / 2) +"</gray>"));
            lore.add(MachineLore.powerPerTick(production()));
            lore.add(MachineLore.power(fuel.getTicks() * production()));
            im.lore(lore);
            item.setItemMeta(im);
            list.add(item);
        }

        return list;
    }

    @Nonnull
    @Override
    public MachineProcessor<FuelOperation> getMachineProcessor() {return PROCESSOR;}

    public int getCapacity() {
        return this.capacity;
    }

	/**
	 * Gets the energy production.
	 *
	 * @return the energy production
	 */
	public int production() {return this.production;}

	/**
	 * Sets the capacity.
	 *
	 * @param capacity the capacity
	 */
	public void capacity(int capacity) { this.capacity = capacity; }

	/**
	 * Sets the energy production.
	 *
	 * @param production the energy production
	 */
	public void production(int production) {this.production = production;}

	/**
	 * Returns whether the fuel should be consumed or not.
	 *
	 * @return true if the fuel should be consumed, false otherwise
	 */
	public boolean shouldConsume() {
        return true;
    }
}
