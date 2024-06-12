package me.phoenix.slimelib.slimefun.electric;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.inventory.InvUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.phoenix.slimelib.slimefun.block.TickingMenuContainer;
import me.phoenix.slimelib.slimefun.block.container.UIComponent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Modularized implementation of AContainer.
 */
public class Machine extends TickingMenuContainer implements EnergyNetComponent, MachineProcessHolder<CraftingOperation>, RecipeDisplayItem {

	/**
	 * The recipes that the generator can accept.
	 */
	public final List<MachineRecipe> MACHINE_RECIPES = new ArrayList<>();
	/**
	 * The mmachine processor.
	 */
	public final MachineProcessor<CraftingOperation> MACHINE_PROCESSOR = new MachineProcessor<>(this);
	/**
	 * The energy capacity of the machine.
	 */
	private int capacity;
	/**
	 * The energy consumption of the machine.
	 */
	private int consumption;
	/**
	 * The speed of the machine.
	 */
	private int speed;

	/**
	 * Instantiates a new Machine.
	 *
	 * @param itemGroup the item group
	 * @param item the item
	 * @param recipeType the recipe type
	 * @param recipe the recipe
	 * @param capacity the capacity
	 * @param consumption the consumption
	 * @param speed the speed
	 */
	public Machine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int capacity, int consumption, int speed) {
        super(itemGroup, item, recipeType, recipe);

        this.capacity = capacity;
        this.consumption = consumption;
        this.speed = speed;

        MACHINE_PROCESSOR.setProgressBar(statusItem());

        registerRecipes();
    }

	/**
	 * Register recipes.
	 */
	public void registerRecipes() {
		// Use this to register recipes
	}

    @Override
    public void onBreak(@NotNull Block block) {
        final BlockMenu inv = BlockStorage.getInventory(block);
        final Location location = block.getLocation();

        if (inv != null) {
            inv.dropItems(location, inputSlots());
            inv.dropItems(location, outputSlots());
        }

        MACHINE_PROCESSOR.endOperation(block);
        BlockStorage.clearBlockInfo(block);
    }

    @Override
    public void onTick(@NotNull Block block) {
        final CraftingOperation operation = MACHINE_PROCESSOR.getOperation(block);
        if(operation == null){
            nullOperation(block);
        } else{
            nonNullOperation(block, operation);
        }
    }

	/**
	 * Handles how an idle machine behaves. Machine is idle when there is no current operation.
	 *
	 * @param block the block
	 */
	public void nullOperation(Block block) {
		final BlockMenu inv = BlockStorage.getInventory(block);
		final MachineRecipe next = findRecipe(inv);
		if(next == null){
			return;
		}
		final CraftingOperation operation = new CraftingOperation(next);
		MACHINE_PROCESSOR.startOperation(block, operation);
		MACHINE_PROCESSOR.updateProgressBar(inv, statusSlot(), operation);
	}

	/**
	 * Handles how a working machine behaves. Machine is working when there is a current operation.
	 *
	 * @param block the block
	 * @param operation the operation
	 */
	public void nonNullOperation(Block block, CraftingOperation operation) {
        if(getCharge(block.getLocation()) < consumption()){
            return;
        }
        removeCharge(block.getLocation(), consumption());
        if(operation.getRemainingTicks() <= speed()){
            onFinish(block, operation);
        } else {
            executeProgress(block, operation);
        }
    }

	/**
	 * Handles how a machine behaves when its current operation is over.
	 *
	 * @param block the block
	 * @param operation the operation
	 */
	public void onFinish(Block block, CraftingOperation operation) {
        final BlockMenu inv = BlockStorage.getInventory(block);
        inv.replaceExistingItem(statusSlot(), UIComponent.IDLE.item());
        for (ItemStack output : operation.getResults()) {
            inv.pushItem(output.clone(), outputSlots());
        }
        MACHINE_PROCESSOR.endOperation(block);
    }

	/**
	 * Handles how a machine behaves when its current operation is in progress.
	 *
	 * @param block the block
	 * @param operation the operation
	 */
	public void executeProgress(Block block, CraftingOperation operation) {
        operation.addProgress(speed());
        MACHINE_PROCESSOR.updateProgressBar(BlockStorage.getInventory(block), statusSlot(), operation);
    }

	/**
	 * Find next recipe for the machine.
	 *
	 * @param inv the inv
	 *
	 * @return the machine recipe
	 */
	public MachineRecipe findRecipe(BlockMenu inv) {
        final Map<Integer, ItemStack> inventory = new HashMap<>();

        ItemStack tempItem;
        for (int slot : inputSlots()) {
            tempItem = inv.getItemInSlot(slot);
            if (tempItem != null) {
                inventory.put(slot, ItemStackWrapper.wrap(tempItem));
            }
        }

        final Map<Integer, Integer> found = new HashMap<>();
        for (MachineRecipe recipe : MACHINE_RECIPES) {
            for (ItemStack input : recipe.getInput()) {
                for (int slot : inputSlots()) {
                    if (SlimefunUtils.isItemSimilar(inventory.get(slot), input, false, true, false)) {
                        found.put(slot, input.getAmount());
                        break;
                    }
                }
            }

            if (found.size() == recipe.getInput().length) {
                if (!InvUtils.fitAll(inv.toInventory(), recipe.getOutput(), outputSlots())) {
                    return null;
                }
                if(shouldConsume()){
                    for (Map.Entry<Integer, Integer> entry : found.entrySet()) {
                        inv.consumeItem(entry.getKey(), entry.getValue());
                    }
                }
                return recipe;
            } else {
                found.clear();
            }
        }

        return null;
    }

    @Override
    @Nonnull
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>(MACHINE_RECIPES.size() * 2);

        for (MachineRecipe recipe : MACHINE_RECIPES) {
            displayRecipes.add(recipe.getInput()[0]);
            displayRecipes.add(recipe.getOutput()[recipe.getOutput().length - 1]);
        }

        return displayRecipes;
    }

	/**
	 * Sets recipe.
	 *
	 * @param seconds the seconds
	 * @param input the input
	 * @param output the output
	 */
	public void setRecipe(int seconds, ItemStack input, ItemStack output) { MACHINE_RECIPES.add(new MachineRecipe(seconds, new ItemStack[] {input}, new ItemStack[] {output}));}

	/**
	 * Sets recipe.
	 *
	 * @param seconds the seconds
	 * @param input the input
	 * @param output the output
	 */
	public void setRecipe(int seconds, ItemStack[] input, ItemStack[] output) { MACHINE_RECIPES.add(new MachineRecipe(seconds, input, output));}

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Nonnull
    @Override
    public MachineProcessor<CraftingOperation> getMachineProcessor() {
        return MACHINE_PROCESSOR;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

	/**
	 * Gets the energy consumption.
	 *
	 * @return the energy consumption
	 */
	public int consumption() {
        return this.consumption;
    }

	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public int speed() {
        return this.speed;
    }

	/**
	 * Sets the capacity.
	 *
	 * @param capacity the capacity
	 */
	public void capacity(int capacity) { this.capacity = capacity; }

	/**
	 * Sets the energy production.
	 *
	 * @param consumption the energy production
	 */
	public void consumption(int consumption) {this.consumption = consumption;}

	/**
	 * Sets the speed.
	 *
	 * @param speed the speed
	 */
	public void speed(int speed) { this.speed = speed; }

	/**
	 * Returns whether the ingredients should be consumed or not.
	 *
	 * @return true if the ingredients should be consumed, false otherwise
	 */
	public boolean shouldConsume() {
        return true;
    }
}
