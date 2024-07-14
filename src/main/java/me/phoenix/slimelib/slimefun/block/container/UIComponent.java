package me.phoenix.slimelib.slimefun.block.container;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import me.phoenix.slimelib.SlimeLib;
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
	INPUT_BORDER(new CustomItem(Material.CYAN_STAINED_GLASS_PANE,
			(meta) -> meta.setCustomModelData(SlimeLib.config().getInt("item-model.border")))
	),
	/**
	 * Output border item.
	 */
	OUTPUT_BORDER(new CustomItem(Material.ORANGE_STAINED_GLASS_PANE,
			(meta) -> meta.setCustomModelData(SlimeLib.config().getInt("item-model.input-border")))
	),
	/**
	 * Border item.
	 */
	BORDER(new CustomItem(Material.GRAY_STAINED_GLASS_PANE,
			(meta) -> meta.setCustomModelData(SlimeLib.config().getInt("item-model.output-border")))
	);

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
