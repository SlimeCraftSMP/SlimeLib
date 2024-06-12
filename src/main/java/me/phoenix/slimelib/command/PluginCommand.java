package me.phoenix.slimelib.command;

import me.phoenix.slimelib.SlimeLib;
import me.phoenix.slimelib.command.annotation.CommandAction;
import me.phoenix.slimelib.command.object.CommandInfo;
import me.phoenix.slimelib.other.Validate;
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
		registerClass(clazz, plugin);
	}

	private void registerClass(Class<?> clazz, JavaPlugin plugin){
		for(Method method : clazz.getDeclaredMethods()){
			if(!method.isAnnotationPresent(CommandAction.class)){
				continue;
			}
			registerCommand(method, plugin, method.getAnnotation(CommandAction.class));
		}
	}

	private void registerCommand(Method method, JavaPlugin plugin, CommandAction action){
		SlimeLib.commandRegistry().command(new CommandInfo(
				action.command(), registerArray(action.argument()), registerString(action.permission()), registerString(action.syntax()), method, plugin
		));
		plugin.getCommand(action.command()).setExecutor(new CommandLogic());
	}

	private String registerString(String string){
		if(Validate.notNull(string)){
			return string;
		} else {
			return null;
		}
	}

	private String[] registerArray(String argument){
		if(Validate.notNull(argument)){
			if(argument.contains(" ")){
				return argument.split(" ");
			} else{
				return new String[]{argument};
			}
		} else{
			return null;
		}
	}

	/**
	 * A command template for creating command logic.
	 *
	 * @param sender the sender
	 * @param args the args
	 */
	public static void testCommand(CommandSender sender, String[] args){
		// Your code goes here
	}
}
