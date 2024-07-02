package me.phoenix.slimelib.core;

import me.phoenix.slimelib.SlimeLib;
import me.phoenix.slimelib.addon.SlimeAddon;
import me.phoenix.slimelib.command.PluginCommand;
import me.phoenix.slimelib.command.annotation.CommandAction;
import me.phoenix.slimelib.visual.Styles;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Reload command to reload addons/configs.
 */
public class ReloadCommand extends PluginCommand{

	/**
	 * Instantiates a new command class.
	 *
	 * @param plugin the plugin
	 */
	public ReloadCommand(JavaPlugin plugin){
		super(ReloadCommand.class, plugin);
	}

	/**
	 * Config reload command.
	 *
	 * @param sender the sender
	 * @param args the args
	 */
	@CommandAction(command = "configReload", argument = "%plugin% %file%")
	public static boolean configReload(CommandSender sender, String[] args){
		if(!SlimeLib.configRegistry().isValid(args)){
			sender.sendMessage(Styles.ERROR.apply("Plugin and/or config not in registry!"));
			return false;
		}
		SlimeLib.configRegistry().config(args).reload();
		sender.sendMessage(Styles.SUCCESS.apply("Successfully reloaded config - " + args[0] + "'s " + args[1]));
		return true;
	}

	/**
	 * Plugin reload command.
	 *
	 * @param sender the sender
	 * @param args the args
	 */
	@CommandAction(command = "pluginReload", argument = "%plugin%")
	public static boolean pluginReload(CommandSender sender, String[] args){
		final Plugin plugin = Bukkit.getPluginManager().getPlugin(args[0]);
		if(!(plugin instanceof SlimeAddon addon)){
			sender.sendMessage(Styles.ERROR.apply("Plugin is not reloadable!"));
			return false;
		}
		addon.onReload(sender);
		sender.sendMessage(Styles.SUCCESS.apply("Successfully reloaded addon - " + args[0]));
		return true;
	}
}
