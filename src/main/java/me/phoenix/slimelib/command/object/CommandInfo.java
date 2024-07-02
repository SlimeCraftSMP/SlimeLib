package me.phoenix.slimelib.command.object;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

/**
 * The Command object for easy storage.
 *
 * @param arguments the command arguments
 * @param description the command description
 * @param permission the command permission
 * @param syntax the command syntax
 * @param method the method
 * @param plugin the plugin
 */
public record CommandInfo(
		@Nullable String[] arguments,
		@Nullable String description,
		@Nullable String permission,
		@Nullable String syntax,
		Method method,
		JavaPlugin plugin
){
	@Nonnull
	public String argument(){
		return Arrays.toString(this.arguments());
	}

	@Nonnull
	public String description(Command command){
		return this.description() != null ? this.description() : command.getDescription();
	}

	public boolean hasPermission(CommandSender sender, Command command){
		return sender.isOp() || sender.hasPermission(this.plugin().getName().toLowerCase(Locale.ROOT)+".admin")
				       || (this.permission() != null ? sender.hasPermission(this.permission()) : command.getPermission() != null && sender.hasPermission(command.getPermission()));
	}

	@Nonnull
	public String syntax(Command command){
		return this.syntax() != null ? this.syntax() : command.getUsage();
	}
}
