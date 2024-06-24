package me.phoenix.slimelib.core;

import me.phoenix.slimelib.SlimeLib;
import me.phoenix.slimelib.command.PluginCommand;
import me.phoenix.slimelib.command.annotation.CommandAction;
import me.phoenix.slimelib.visual.Styles;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ReloadCommand extends PluginCommand{

	public ReloadCommand(JavaPlugin plugin){
		super(ReloadCommand.class, plugin);
	}

	@CommandAction(command = "configReload")
	public static void reloadCommand(CommandSender sender, String[] args){
		SlimeLib.config().reload();
		sender.sendMessage(Styles.SUCCESS.apply("Successfully reloaded configs!"));
	}
}
