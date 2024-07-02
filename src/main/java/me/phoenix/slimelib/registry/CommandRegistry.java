package me.phoenix.slimelib.registry;

import me.phoenix.slimelib.command.object.CommandInfo;
import java.util.HashMap;

/**
 * Stores all the commands.
 */
public class CommandRegistry{

	private final HashMap<String, CommandInfo> commands = new HashMap<>();

	/**
	 * Get a map of all the commands.
	 *
	 * @return the command map
	 */
	public HashMap<String, CommandInfo> commands(){
		return commands;
	}

	/**
	 * Add a command to the registry.
	 *
	 * @param name the name
	 * @param command the command
	 */
	public void command(String name, CommandInfo command){
		commands.putIfAbsent(name, command);
	}

	/**
	 * Check if the given command name exists
	 *
	 * @param name the command name
	 *
	 * @return true if it exists, false otherwise
	 */
	public boolean isValid(String name){
		return commands.containsKey(name);
	}

	/**
	 * Gets the command object from name
	 *
	 * @param name the command name
	 *
	 * @return the command object
	 */
	public CommandInfo command(String name){
		return commands.get(name);
	}
}
