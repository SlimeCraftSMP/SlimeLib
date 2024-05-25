package me.phoenix.slimelib.item;

import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import me.phoenix.slimelib.other.KeyUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataHolder;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Utilities to create items that have a cooldown.
 */
public final class CooldownUtils {

	/**
	 * The cooldown NamespacedKey.
	 */
	public static final NamespacedKey cooldownKey = KeyUtils.createKey("slimelib", "cooldown");

	/**
	 * Add a cooldown to an item.
	 *
	 * @param itemStack the item stack
	 * @param seconds the seconds
	 */
	@ParametersAreNonnullByDefault
    public static void addCooldown(ItemStack itemStack, int seconds) {
        addCooldown(itemStack, seconds * 1000L);
    }

	/**
	 * Add a cooldown to an item.
	 *
	 * @param itemStack the item stack
	 * @param milliSeconds the milliseconds
	 */
	@ParametersAreNonnullByDefault
    public static void addCooldown(ItemStack itemStack, long milliSeconds) {
        addCooldown(itemStack.getItemMeta(), milliSeconds);
    }

	/**
	 * Add a cooldown to a PDC.
	 *
	 * @param holder the holder
	 * @param seconds the seconds
	 */
	@ParametersAreNonnullByDefault
    public static void addCooldown(PersistentDataHolder holder, int seconds) {
        addCooldown(holder, seconds * 1000L);
    }

	/**
	 *Add a cooldown to a PDC.
	 *
	 * @param holder the holder
	 * @param milliSeconds the milliseconds
	 */
	@ParametersAreNonnullByDefault
    public static void addCooldown(PersistentDataHolder holder, long milliSeconds) {
        PersistentDataAPI.setLong(holder, cooldownKey, System.currentTimeMillis() + milliSeconds);
    }

	/**
	 * Check if the item is on cooldown.
	 *
	 * @param itemStack the item stack
	 *
	 * @return true if it is on cooldown, false otherwise
	 */
	@ParametersAreNonnullByDefault
    public static boolean isOnCooldown(ItemStack itemStack) {
        return isOnCooldown(itemStack.getItemMeta());
    }

	/**
	 * Check if the PDC is on cooldown.
	 *
	 * @param holder the holder
	 *
	 * @return true if it is on cooldown, false otherwise
	 */
	@ParametersAreNonnullByDefault
    public static boolean isOnCooldown(PersistentDataHolder holder) {
        return System.currentTimeMillis() < PersistentDataAPI.getLong(holder, cooldownKey, 0);
    }
}
