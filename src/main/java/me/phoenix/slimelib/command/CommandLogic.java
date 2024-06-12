package me.phoenix.slimelib.command;

import me.phoenix.slimelib.SlimeLib;
import me.phoenix.slimelib.command.object.CommandInfo;
import me.phoenix.slimelib.other.Validate;
import me.phoenix.slimelib.visual.Styles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Locale;

/**
 * Contains the logic behind registering commands.
 */
public class CommandLogic implements CommandExecutor{

	@Override
	@ParametersAreNonnullByDefault
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args){
		for(CommandInfo commandInfo : SlimeLib.commandRegistry().commands()){
			if(!command.getName().equalsIgnoreCase(commandInfo.command())){
				continue;
			}
			if(!hasPermission(sender, commandInfo)){
				return false;
			}
			if(commandInfo.arguments() != null && args.length < commandInfo.arguments().length){
				sender.sendMessage(
						Styles.ERROR.apply("/" + commandInfo.command() + Arrays.toString(commandInfo.arguments())).appendNewline().append(
								Styles.text(Validate.notNull(commandInfo.syntax()) ? commandInfo.syntax() : command.getDescription())
						)
				);
				return false;
			}
			try{
				commandInfo.method().invoke(null, sender, args);
				return true;
			} catch(Exception e){
				return false;
			}
		}
		return false;
	}

	private boolean hasPermission(CommandSender sender, CommandInfo commandInfo){
		return sender.isOp()
				       || sender.hasPermission(commandInfo.plugin().getName().toLowerCase(Locale.ROOT)+".admin")
				       || (Validate.notNull(commandInfo.permission()) && sender.hasPermission(commandInfo.permission()));
	}
}
