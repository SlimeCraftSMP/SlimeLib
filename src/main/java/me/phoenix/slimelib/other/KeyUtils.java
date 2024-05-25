package me.phoenix.slimelib.other;

import org.bukkit.NamespacedKey;

/**
 * Method for quick creation of NamespacedKey.
 */
public class KeyUtils{

	/**
	 * Create a namespaced key.
	 *
	 * @param namespace the namespace
	 * @param key the key
	 *
	 * @return the namespaced key
	 */
	public static NamespacedKey createKey(String namespace, String key) {
        return new NamespacedKey(namespace, key);
    }
}
