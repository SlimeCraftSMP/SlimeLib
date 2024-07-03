package me.phoenix.slimelib.inventory;

import me.phoenix.slimelib.inventory.handler.MenuClickHandler;
import me.phoenix.slimelib.inventory.handler.MenuCloseHandler;
import me.phoenix.slimelib.inventory.handler.MenuOpenHandler;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * A custom inventory Menu.
 */
public class Menu implements InventoryHolder{

	private final Inventory inventory;
	private final Map<Integer, MenuClickHandler> onItemClick = new HashMap<>();
	private boolean playerInteractable;
	private MenuOpenHandler onOpen;
	private MenuCloseHandler onClose;

	/**
	 * Instantiates a new Menu.
	 *
	 * @param plugin the plugin
	 * @param size the size of the menu (9, 18, 27, 36, 45, 54)
	 * @param title the title of the menu
	 */
	public Menu(JavaPlugin plugin, int size, Component title){
		this.inventory = plugin.getServer().createInventory(this, size, title);
		this.playerInteractable = false;
	}

	/**
	 * Toggle whether the player can interact with the menu or not.
	 * If this is set to true then players will be able to take out items without a click handler associated with them.
	 *
	 * @param isInteractable true to make it interactable, false otherwise
	 */
	public void playerInteractable(boolean isInteractable){
		this.playerInteractable = isInteractable;
	}

	/**
	 * Get if the menu is player interactable or not.
	 *
	 * @return the boolean
	 */
	public boolean playerInteractable(){
		return this.playerInteractable;
	}

	private void setItem(int slot, ItemStack item){
		if(slot > this.inventory.getSize() || slot < 0){
			throw new IllegalArgumentException("Invalid Slot: " + slot);
		} else{
			this.inventory.setItem(slot, item);
		}
	}

	/**
	 * Put an item in the menu.
	 *
	 * @param slot the slot
	 * @param item the item
	 * @param clickHandler the click handler
	 */
	public void item(int slot, ItemStack item, MenuClickHandler clickHandler){
		setItem(slot, item);
		menuClickHandler(slot, clickHandler);
	}

	/**
	 * Put an item in the menu with an empty click handler.
	 *
	 * @param slot the slot
	 * @param item the item
	 */
	public void item(int slot, ItemStack item){
		item(slot, item, MenuUtils.emptyClickHandler());
	}

	/**
	 * Get an item from the menu.
	 *
	 * @param slot the slot
	 *
	 * @return the item stack
	 */
	public ItemStack item(int slot){
		if(slot > this.inventory.getSize() || slot < 0){
			throw new IllegalArgumentException("Invalid Slot: " + slot);
		} else{
			return this.inventory.getItem(slot);
		}
	}

	/**
	 * Opens the menu.
	 *
	 * @param players the players
	 */
	public void open(Player... players){
		for(Player player : players){
			player.openInventory(this.inventory);
			MenuListener.menus.put(player.getUniqueId(), this);
			if(this.onOpen != null){
				this.onOpen.onOpen(player, this);
			}
		}
	}

	/**
	 * Set the MenuClickHandler.
	 *
	 * @param slot the slot
	 * @param handler the handler
	 */
	public void menuClickHandler(int slot, MenuClickHandler handler){
		this.onItemClick.put(slot, handler);
	}

	/**
	 * Set the MenuCloseHandler.
	 *
	 * @param handler the handler
	 */
	public void menuCloseHandler(MenuCloseHandler handler){
		this.onClose = handler;
	}

	/**
	 * Set the MenuOpenHandler.
	 *
	 * @param handler the handler
	 */
	public void menuOpenHandler(MenuOpenHandler handler){
		this.onOpen = handler;
	}

	/**
	 * Get the MenuClickHandler.
	 *
	 * @param slot the slot
	 *
	 * @return the menu click handler
	 */
	public MenuClickHandler menuClickHandler(int slot){
		return this.onItemClick.get(slot);
	}

	/**
	 * Get the MenuCloseHandler.
	 *
	 * @return the menu close handler
	 */
	public MenuCloseHandler menuCloseHandler(){
		return this.onClose;
	}

	/**
	 * Get the MenuOpenHandler.
	 *
	 * @return the menu open handler
	 */
	public MenuOpenHandler menuOpenHandler(){
		return this.onOpen;
	}

	@Override
	@Nonnull
	public Inventory getInventory(){
		return this.inventory;
	}
}
