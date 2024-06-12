package me.phoenix.slimelib.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import me.phoenix.slimelib.visual.Styles;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;

/**
 * Utility class to quickly create ItemStacks with custom properties.
 */
public class CustomItem extends ItemStack {

	/**
	 * Instantiates a new Custom item.
	 *
	 * @param item the item
	 */
	public CustomItem(ItemStack item) {
        super(item);
    }

	/**
	 * Instantiates a new Custom item.
	 *
	 * @param type the type
	 */
	public CustomItem(Material type) {
        super(type);
    }

	/**
	 * Instantiates a new Custom item.
	 *
	 * @param item the item
	 * @param meta the meta
	 */
	public CustomItem(ItemStack item, Consumer<ItemMeta> meta) {
        super(item);
        final ItemMeta im = getItemMeta();
        meta.accept(im);
        setItemMeta(im);
    }

	/**
	 * Instantiates a new Custom item.
	 *
	 * @param type the type
	 * @param meta the meta
	 */
	public CustomItem(Material type, Consumer<ItemMeta> meta) {
        this(new ItemStack(type), meta);
    }

	/**
	 * Instantiates a new Custom item.
	 *
	 * @param item the item
	 * @param name the name
	 * @param lore the lore
	 */
	public CustomItem(ItemStack item, Component name, Component... lore) {
        this(item, im -> {
            if (name != null) {
                im.displayName(Styles.fix().append(name));
            }

            if (lore.length > 0) {
				final List<Component> lines = new ArrayList<>();
	            for(Component component : lore){
					lines.add(Styles.fix().append(component));
	            }
	            im.lore(lines);
            }
        });
    }

	/**
	 * Instantiates a new Custom item.
	 *
	 * @param item the item
	 * @param color the color
	 * @param name the name
	 * @param lore the lore
	 */
	public CustomItem(ItemStack item, Color color, Component name, Component... lore) {
        this(item, im -> {
            if (name != null) {
                im.displayName(Styles.fix().append(name));
            }

            if (lore.length > 0) {
	            final List<Component> lines = new ArrayList<>();
	            for(Component component : lore){
		            lines.add(Styles.fix().append(component));
	            }
	            im.lore(lines);
            }

            if (im instanceof LeatherArmorMeta leatherArmorMeta) {
	            leatherArmorMeta.setColor(color);
            }
            if (im instanceof PotionMeta potionMeta) {
	            potionMeta.setColor(color);
            }
        });
    }

	/**
	 * Add flags custom item.
	 *
	 * @param flags the flags
	 *
	 * @return the custom item
	 */
	public CustomItem addFlags(ItemFlag... flags) {
        final ItemMeta im = getItemMeta();
        im.addItemFlags(flags);
        setItemMeta(im);

        return this;
    }

	/**
	 * Sets custom model.
	 *
	 * @param data the data
	 *
	 * @return the custom model
	 */
	public CustomItem setCustomModel(int data) {
        ItemMeta im = getItemMeta();
        im.setCustomModelData(data == 0 ? null : data);
        setItemMeta(im);

        return this;
    }

	/**
	 * Instantiates a new Custom item.
	 *
	 * @param type the type
	 * @param name the name
	 * @param lore the lore
	 */
	public CustomItem(Material type, Component name, Component... lore) {
        this(new ItemStack(type), name, lore);
    }

	/**
	 * Instantiates a new Custom item.
	 *
	 * @param item the item
	 * @param amount the amount
	 */
	public CustomItem(ItemStack item, int amount) {
        super(item);
        setAmount(amount);
    }

}
