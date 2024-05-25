package me.phoenix.slimelib.visual;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import me.phoenix.slimelib.item.CustomItem;
import me.phoenix.slimelib.other.TypeUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used to make the visuals of your addon uniform throughout.
 */
public class Styles{

	/**
	 * The constant MiniMessage reference.
	 */
	public static final MiniMessage minimessage = MiniMessage.miniMessage();

	/**
	 * Fix component. Used to fix the default italics text in components.
	 *
	 * @return the component
	 */
	public static Component fix(){
        return minimessage.deserialize("<!i>");
    }

	// Message

	/**
	 * Style for error messages.
	 */
    public static final Styles ERROR = new Styles(NamedTextColor.RED, "Error");
	/**
	 * Style for warning messages.
	 */
	public static final Styles WARNING = new Styles(NamedTextColor.YELLOW, "Warning");
	/**
	 * Style for success messages.
	 */
	public static final Styles SUCCESS = new Styles(NamedTextColor.GREEN, "Success");

	/**
	 * Style for lore.
	 */
	public static final Styles LORE = new Styles(NamedTextColor.GRAY);

	// Item

	/**
	 * Style for machine items.
	 */
    public static final Styles MACHINE = new Styles(TextColor.color(255, 255, 0), "Electric Machine");
	/**
	 * Style for generator items.
	 */
	public static final Styles GENERATOR = new Styles(TextColor.color(255, 255, 0), "Electric Generator");

	// Rarity

	/**
	 * Style for common items.
	 */
	public static final Styles COMMON = new Styles(TextColor.color(0, 255, 0), "Common Item");
	/**
	 * Style for rare items.
	 */
	public static final Styles RARE = new Styles(TextColor.color(0, 255, 255), "Rare Item");
	/**
	 * Style for epic items.
	 */
	public static final Styles EPIC = new Styles(TextColor.color(128, 0, 128), "Epic Item");
	/**
	 * Style for mythic items.
	 */
	public static final Styles MYTHIC = new Styles(TextColor.color(255, 215, 0), "Mythic Item");

    @Nonnull
    private final TextColor color;
    @Nonnull
    private final String loreLine;

	/**
	 * Instantiates a new Style.
	 *
	 * @param color the color
	 */
	public Styles(@Nonnull TextColor color) {
        this(color, "");
    }

	/**
	 * Instantiates a new Style.
	 *
	 * @param color the color
	 * @param loreLine the lore line
	 */
	public Styles(@Nonnull TextColor color, @Nonnull String loreLine) {
        this.color = color;
        this.loreLine = loreLine;
    }

	/**
	 * Convert a text to string while parsing minimessage.
	 *
	 * @param value the value
	 *
	 * @return the component
	 */
	@Nonnull
	public static Component text(@Nonnull Object value) {
		return minimessage.deserialize(TypeUtils.asString(value));
	}

	/**
	 * Apply style to an object as a component.
	 *
	 * @param value the value
	 *
	 * @return the component
	 */
	@Nonnull
    public Component apply(@Nonnull Object value) {
        return fix().append(Component.text(TypeUtils.asString(value), color));
    }

	/**
	 *Apply style to an object as a string.
	 *
	 * @param value the value
	 *
	 * @return the string
	 */
	@Nonnull
    public String applyAsString(@Nonnull Object value) {
        return PlainTextComponentSerializer.plainText().serialize(apply(value));
    }

	/**
	 * Gets the lore line.
	 *
	 * @return the lore line
	 */
	@Nonnull
    public String getLoreLine() {
        return loreLine;
    }

	/**
	 * Creates a slimefun item with the given style.
	 *
	 * @param id the id
	 * @param material the material
	 * @param style the style
	 * @param name the name
	 * @param lore the lore
	 *
	 * @return the slimefun item
	 */
	@Nonnull
	@ParametersAreNonnullByDefault
	public static SlimefunItemStack styledSfItem(String id, Material material, Styles style, String name, String... lore) {
		return styledSfItem(id, new ItemStack(material), style, name, lore);
	}

	/**
	 * Creates a slimefun item with the given style.
	 *
	 * @param id the id
	 * @param itemStack the item stack
	 * @param style the style
	 * @param name the name
	 * @param lore the lore
	 *
	 * @return the slimefun item
	 */
	@Nonnull
    @ParametersAreNonnullByDefault
    public static SlimefunItemStack styledSfItem(String id, ItemStack itemStack, Styles style, String name, String... lore) {
        return styledSfItem(id, itemStack, style, name, Arrays.asList(lore));
    }

	/**
	 * Creates a slimefun item with the given style.
	 *
	 * @param id the id
	 * @param itemStack the item stack
	 * @param style the style
	 * @param name the name
	 * @param lore the lore
	 *
	 * @return the slimefun item
	 */
	@Nonnull
    @ParametersAreNonnullByDefault
    public static SlimefunItemStack styledSfItem(String id, ItemStack itemStack, Styles style, String name, List<String> lore) {
        final List<Component> finalLore = new ArrayList<>();
		finalLore.add(Component.space());
		if(!lore.isEmpty()){
			for (String s : lore) {
				finalLore.add(LORE.apply(s));
			}
			finalLore.add(Component.space());
		}
        finalLore.add(style.apply(style.getLoreLine())); 
        return new SlimefunItemStack(
                id,
                itemStack,
                style.apply(name),
                finalLore.toArray(new Component[finalLore.size() - 1])
        );
    }

	/**
	 * Creates a slimefun item with the given style.
	 *
	 * @param id the id
	 * @param material the material
	 * @param color the color
	 * @param style the style
	 * @param name the name
	 * @param lore the lore
	 *
	 * @return the slimefun item
	 */
	@Nonnull
	@ParametersAreNonnullByDefault
	public static SlimefunItemStack styledSfItem(String id, Material material, Color color, Styles style, String name, String... lore) {
		return styledSfItem(id, material, color, style, name, Arrays.asList(lore));
	}

	/**
	 * Creates a slimefun item with the given style.
	 *
	 * @param id the id
	 * @param material the material
	 * @param color the color
	 * @param style the style
	 * @param name the name
	 * @param lore the lore
	 *
	 * @return the slimefun item
	 */
	@Nonnull
    @ParametersAreNonnullByDefault
    public static SlimefunItemStack styledSfItem(String id, Material material, Color color, Styles style, String name, List<String> lore) {
		final List<Component> finalLore = new ArrayList<>();
        finalLore.add(Component.space());
		if(!lore.isEmpty()){
			for (String s : lore) {
				finalLore.add(LORE.apply(s));
			}
			finalLore.add(Component.space());
		}
        finalLore.add(style.apply(style.getLoreLine())); 
        return new SlimefunItemStack(
                id,
                material,
                color,
                style.apply(name),
                finalLore.toArray(new Component[finalLore.size() - 1])
        );
    }

	/**
	 * Creates an item with the given style.
	 *
	 * @param material the material
	 * @param style the style
	 * @param name the name
	 * @param lore the lore
	 *
	 * @return the item
	 */
	@Nonnull
	@ParametersAreNonnullByDefault
	public static ItemStack styledItem(Material material, Styles style, String name, String... lore) {
		return styledItem(new ItemStack(material), style, name, lore);
	}

	/**
	 * Creates an item with the given style.
	 *
	 * @param itemStack the item stack
	 * @param style the style
	 * @param name the name
	 * @param lore the lore
	 *
	 * @return the item
	 */
	@Nonnull
	@ParametersAreNonnullByDefault
	public static ItemStack styledItem(ItemStack itemStack, Styles style, String name, String... lore) {
		return styledItem(itemStack, style, name, Arrays.asList(lore));
	}

	/**
	 * Creates an item with the given style.
	 *
	 * @param itemStack the item stack
	 * @param style the style
	 * @param name the name
	 * @param lore the lore
	 *
	 * @return the item
	 */
	@Nonnull
    @ParametersAreNonnullByDefault
    public static ItemStack styledItem(ItemStack itemStack, Styles style, String name, List<String> lore) {
		final List<Component> finalLore = new ArrayList<>();
		finalLore.add(Component.space());
		if(!lore.isEmpty()){
			for (String s : lore) {
				finalLore.add(LORE.apply(s));
			}
			finalLore.add(Component.space());
		}
        finalLore.add(style.apply(style.getLoreLine())); 
        return new CustomItem(
		        itemStack,
                style.apply(name),
                finalLore.toArray(new Component[finalLore.size() - 1])
        );
    }
}
