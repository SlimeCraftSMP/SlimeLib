package me.phoenix.slimelib.inventory;

import me.phoenix.slimelib.inventory.handler.MenuClickHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Event handling within a Menu.
 */
public class MenuListener implements Listener{

	/**
	 * Map of player UUIDs and the menu they have currently opened.
	 */
	protected static final Map<UUID, Menu> menus = new HashMap<>();

	/**
	 * Instantiates a new Menu listener.
	 *
	 * @param plugin the plugin
	 */
	public MenuListener(JavaPlugin plugin){
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	/**
	 * Executes on click.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onClick(InventoryClickEvent event){
		final Player player = (Player) event.getWhoClicked();
		final Menu menu = menus.get(player.getUniqueId());
		if(menu == null){
			return;
		}
		final MenuClickHandler onClick = menu.menuClickHandler(event.getRawSlot());
		if(onClick == null){
			event.setCancelled(!menu.playerInteractable());
			return;
		}
		event.setCancelled(onClick.onItemClick(player, menu, event.getAction(), event.getClick(), event.getCurrentItem(), event.getRawSlot()));
	}

	/**
	 * Executes on close.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onClose(InventoryCloseEvent event){
		final Player player = (Player) event.getPlayer();
		final Menu menu = menus.remove(player.getUniqueId());
		if(menu != null){
			menu.menuCloseHandler().onClose(player, menu, event.getReason());
		}
	}
}
