package me.phoenix.slimelib.command.object;

import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * The Command object for easy storage.
 */
public record CommandInfo(
		String command,
		@Nullable String[] arguments,
		String permission,
		String syntax,
		Method method,
		JavaPlugin plugin
){ }
