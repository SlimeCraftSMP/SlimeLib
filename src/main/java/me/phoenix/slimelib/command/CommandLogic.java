package me.phoenix.slimelib.command;

import me.phoenix.slimelib.SlimeLib;
import me.phoenix.slimelib.command.object.CommandInfo;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Contains the logic behind registering commands.
 */
public class CommandLogic implements TabExecutor{

	@Override
	@ParametersAreNonnullByDefault
	public boolean onCommand(CommandSender sender, Command command, String s, String[] args){
		if(args.length == 0){
			return false;
		}
		for(CommandInfo commandInfo : SlimeLib.commandRegistry().getCommands()){
			if(args[0].equalsIgnoreCase(commandInfo.command()[0]) && hasPermission(sender, commandInfo)){
				if(args.length < commandInfo.command().length){
					sender.sendMessage("/" + Arrays.toString(commandInfo.command()) + commandInfo.syntax());
					break;
				}
				try{
					commandInfo.method().invoke(sender, (Object) args);
				} catch(Exception e){
					throw new RuntimeException(e);
				}
				break;
			}
		}
		return true;
	}

	@Override
	@ParametersAreNonnullByDefault
	public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args){
		final List<String> commands = new ArrayList<>();
		for(CommandInfo commandInfo : SlimeLib.commandRegistry().getCommands()){
			if(args.length != commandInfo.command().length){
				continue;
			}
			if(hasPermission(sender, commandInfo)){
				commands.add(commandInfo.command()[0]);
			}
		}
		return StringUtil.copyPartialMatches(args[0], commands, new ArrayList<>());
	}

	private boolean hasPermission(CommandSender sender, CommandInfo commandInfo){
		return sender.isOp()
				       || sender.hasPermission(commandInfo.permission())
				       || sender.hasPermission(commandInfo.plugin().getName().toLowerCase(Locale.ROOT)+".admin");
	}
}
