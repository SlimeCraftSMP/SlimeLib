package me.phoenix.slimelib.slimefun.block.container;

import javax.annotation.ParametersAreNonnullByDefault;

import me.phoenix.slimelib.slimefun.block.MenuContainer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;

/**
 * Default Menu template for a Menu container.
 */
@ParametersAreNonnullByDefault
public final class MenuTemplate extends BlockMenuPreset {

    private final MenuContainer container;

	/**
	 * Instantiates a new Menu template.
	 *
	 * @param container the container
	 */
	public MenuTemplate(MenuContainer container) {
        super(container.getId(), container.getItemName());
        this.container = container;
        container.setupPreset(this);
    }

    @Override
    public void init() {}

    @Override
    public void newInstance(BlockMenu menu, Block block) {
        container.updateBlock(block);
        container.onNewInstance(menu, block);
    }

    @Override
    public boolean canOpen(Block b, Player p) {
        return p.hasPermission("slimefun.inventory.bypass") || Slimefun.getProtectionManager().hasPermission(p, b.getLocation(), Interaction.INTERACT_BLOCK);
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
        return new int[0];
    }

    @Override
    public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        return container.accessSlots(menu, flow, item);
    }
}
