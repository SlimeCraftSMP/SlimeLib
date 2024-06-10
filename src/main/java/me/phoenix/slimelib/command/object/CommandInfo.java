package me.phoenix.slimelib.command.object;

import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

/**
 * The Command object for easy storage.
 */
public record CommandInfo(String[] command, String permission, String syntax, Method method, JavaPlugin plugin){ }
