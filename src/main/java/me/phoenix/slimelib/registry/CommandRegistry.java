package me.phoenix.slimelib.registry;

import me.phoenix.slimelib.command.object.CommandInfo;

import java.util.Set;
import java.util.HashSet;

/**
 * Stores all the commands.
 */
public class CommandRegistry{

	private final Set<CommandInfo> commands = new HashSet<>();

	/**
	 * Get a set of all the commands.
	 *
	 * @return the command set
	 */
	public Set<CommandInfo> commands(){
		return commands;
	}

	/**
	 * Add a command to the registry.
	 *
	 * @param command the command
	 */
	public void command(CommandInfo command){
		commands.add(command);
	}
}
