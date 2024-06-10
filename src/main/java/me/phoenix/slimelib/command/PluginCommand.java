package me.phoenix.slimelib.command;

import me.phoenix.slimelib.SlimeLib;
import me.phoenix.slimelib.command.annotation.CommandAction;
import me.phoenix.slimelib.command.object.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

/**
 * The class which has to be extended to register commands.
 */
public class PluginCommand {

	/**
	 * Instantiates a new command class.
	 *
	 * @param clazz the clazz
	 * @param plugin the plugin
	 */
	public PluginCommand(Class<?> clazz, JavaPlugin plugin){
		register(clazz, plugin);
	}

	private void register(Class<?> clazz, JavaPlugin plugin){
		for(Method method : clazz.getDeclaredMethods()){
			if(!method.isAnnotationPresent(CommandAction.class)){
				continue;
			}
			final CommandAction commandAction = method.getAnnotation(CommandAction.class);
			if(commandAction.command().contains(" ")){
				SlimeLib.commandRegistry().addCommand(new CommandInfo(
						commandAction.command().split(" "), commandAction.permission(), commandAction.syntax(), method, plugin
				));
			} else{
				SlimeLib.commandRegistry().addCommand(new CommandInfo(
						new String[]{commandAction.command()}, commandAction.permission(), commandAction.syntax(), method, plugin
				));
			}
		}
	}

	/**
	 * A command template for creating command logic.
	 *
	 * @param sender the sender
	 * @param args the args
	 */
	public void testCommand(CommandSender sender, String[] args){
		// Your code goes here
	}
}
