package me.phoenix.slimelib.visual;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

/**
 * Utilities related to {@link Component}
 */
public class ComponentUtils{

	private ComponentUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Appends text to the builder and switches to the next line.
	 *
	 * @param builder the builder
	 * @param string the string
	 */
	public static void nextLine(TextComponent.Builder builder, String string){
		builder.append(Component.text(string)).appendNewline();
	}

	/**
	 * Appends component to the builder and switches to the next line.
	 *
	 * @param builder the builder
	 * @param component the component
	 */
	public static void nextLine(TextComponent.Builder builder, Component component){
		builder.append(component).appendNewline();
	}
}
