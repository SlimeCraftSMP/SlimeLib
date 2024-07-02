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
	 * The command, e.g. "reload"
	 *
	 * @return the command
	 */
	String command();

	/**
	 * The command description, e.g. "This command reloads your config"
	 *
	 * @return the description
	 */
	String description() default "null";

	/**
	 * The command argument, e.g. "%player%" or "%file%"
	 *
	 * @return the argument
	 */
	String argument() default "null";

	/**
	 * The permission to use the command, e.g. "yourPlugin.admin".
	 * If none mentioned, only operators and people with "yourPlugin.admin" can use it.
	 *
	 * @return the permission
	 */
	String permission() default "null";

	/**
	 * The syntax of the command, e.g. "%file% - The file to reload".
	 * If none mentioned, uses the one in plugin.yml
	 *
	 * @return the syntax
	 */
	String syntax() default "null";
}
