package me.phoenix.slimelib.command.object;

import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * The Command object for easy storage.
 *
 * @param command the main command
 * @param arguments the command arguments
 * @param permission the command permission
 * @param syntax the command syntax
 * @param method the method
 * @param plugin the plugin
 */
public record CommandInfo(
		String command,
		@Nullable String[] arguments,
		@Nullable String permission,
		@Nullable String syntax,
		Method method,
		JavaPlugin plugin
){ }
