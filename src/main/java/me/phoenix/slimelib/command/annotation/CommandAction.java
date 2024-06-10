package me.phoenix.slimelib.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to define a command.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAction{
	/**
	 * The command, e.g. "reload", "reload <file>"
	 *
	 * @return the command
	 */
	String command();

	/**
	 * The permission to use the command, e.g. "plugin.admin".
	 *
	 * @return the permission
	 */
	String permission();

	/**
	 * The syntax of the command, e.g. "<file> - The file to reload".
	 *
	 * @return the syntax
	 */
	String syntax();
}
