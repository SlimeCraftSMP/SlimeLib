package me.phoenix.slimelib.slimefun.block.container;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.phoenix.slimelib.item.CustomItem;
import me.phoenix.slimelib.visual.Styles;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * UI component enum.
 */
public enum UIComponent {

	/**
	 * Processing item.
	 */
	PROCESSING(Material.LIME_STAINED_GLASS_PANE, Styles.SUCCESS.apply("Processing...")),
	/**
	 * No Power item.
	 */
	NO_POWER(Material.RED_STAINED_GLASS_PANE, Styles.ERROR.apply("Insufficient power supply!")),
	/**
	 * Idle item.
	 */
	IDLE(Material.BLACK_STAINED_GLASS_PANE, Styles.LORE.apply("Machine on Standby.")),
	/**
	 * Full storage item.
	 */
	FULL_STORAGE(Material.ORANGE_STAINED_GLASS_PANE, Styles.ERROR.apply("Insufficient storage space!")),
	/**
	 * Input border item.
	 */
	INPUT_BORDER(ChestMenuUtils.getInputSlotTexture()),
	/**
	 * Output border item.
	 */
	OUTPUT_BORDER(ChestMenuUtils.getOutputSlotTexture()),
	/**
	 * Border item.
	 */
	BORDER(ChestMenuUtils.getBackground());

    private final ItemStack item;

    @ParametersAreNonnullByDefault
    UIComponent(Material material, Component name) {
        this.item = new CustomItem(material, name);
    }

    @ParametersAreNonnullByDefault
    UIComponent(ItemStack item) {
        this.item = item;
    }

	/**
	 * Get the ItemStack related with the enum element.
	 *
	 * @return the item stack
	 */
	@Nonnull
    public ItemStack item() {
        return item;
    }
}
