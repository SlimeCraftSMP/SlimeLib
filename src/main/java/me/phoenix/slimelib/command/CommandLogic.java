package me.phoenix.slimelib.command;

import me.phoenix.slimelib.SlimeLib;
import me.phoenix.slimelib.command.object.CommandInfo;
import me.phoenix.slimelib.visual.Styles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Contains the logic behind registering commands.
 */
public class CommandLogic implements CommandExecutor{

	@Override
	@ParametersAreNonnullByDefault
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args){
		final String commandName = command.getName();
		if(!SlimeLib.commandRegistry().isValid(commandName)){
			sender.sendMessage(Styles.ERROR.apply("Invalid command!"));
			return false;
		}
		final CommandInfo commandInfo = SlimeLib.commandRegistry().command(commandName);
		if(!commandInfo.hasPermission(sender, command)){
			sender.sendMessage(Styles.ERROR.apply("You do not have permission to use this command!"));
			return false;
		}
		if(commandInfo.arguments() != null && args.length < commandInfo.arguments().length){
			sender.sendMessage(
					Styles.ERROR.apply("/" + commandName + " " + commandInfo.argument() + " - " + commandInfo.description(command))
							.appendNewline().append(Styles.text(commandInfo.syntax(command)))
			);
			return false;
		}
		try{
			return (boolean) commandInfo.method().invoke(null, sender, args);
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
