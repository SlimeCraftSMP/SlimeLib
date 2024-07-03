package me.phoenix.slimelib.core;

import io.papermc.paper.plugin.configuration.PluginMeta;
import me.phoenix.slimelib.SlimeLib;
import me.phoenix.slimelib.command.PluginCommand;
import me.phoenix.slimelib.command.annotation.CommandAction;
import me.phoenix.slimelib.other.Versions;
import me.phoenix.slimelib.visual.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class VersionCommand extends PluginCommand{

	/**
	 * Instantiates a new command class.
	 *
	 * @param plugin the plugin
	 */
	public VersionCommand(JavaPlugin plugin){
		super(VersionCommand.class, plugin);
	}

	/**
	 * Server Version command.
	 *
	 * @param sender the sender
	 * @param args the args
	 */
	@SuppressWarnings("UnstableApiUsage")
	@CommandAction(command = "serverVersion")
	public static boolean serverVersion(CommandSender sender, String[] args){
		final TextComponent.Builder component = Component.text().color(NamedTextColor.GREEN);

		serverInfo(component);

		for(Map.Entry<String, Plugin> entry : SlimeLib.addonRegistry().addons().entrySet()){
			pluginInfo(component, entry.getKey(), entry.getValue().getPluginMeta());
		}

		sender.sendMessage(component.build());
		return true;
	}

	private static void serverInfo(TextComponent.Builder component){
		ComponentUtils.nextLine(component, Component.text("Server Info").decorate(TextDecoration.BOLD, TextDecoration.UNDERLINED));
		ComponentUtils.nextLine(component, Versions.serverSoftware() + " " + Versions.serverVersion());
		ComponentUtils.nextLine(component, "Java " + Runtime.version().feature());
		ComponentUtils.nextLine(component, Component.text("Installed Addons: (" + SlimeLib.addonRegistry().size() + ")").decorate(TextDecoration.BOLD, TextDecoration.UNDERLINED));
	}

	@SuppressWarnings("UnstableApiUsage")
	private static void pluginInfo(TextComponent.Builder component, String name, PluginMeta pluginMeta){
		final TextComponent.Builder pluginInfo = Component.text().color(NamedTextColor.GREEN);
		ComponentUtils.nextLine(pluginInfo, "Author: " + (pluginMeta.getAuthors().isEmpty() ? "SlimeCraftSMP" : pluginMeta.getAuthors()));
		ComponentUtils.nextLine(pluginInfo, "Contributors: " + (pluginMeta.getContributors().isEmpty() ? "None" : pluginMeta.getContributors()));
		ComponentUtils.nextLine(pluginInfo, "About: " + pluginMeta.getDescription());

		if(pluginMeta.getWebsite() != null){
			ComponentUtils.nextLine(pluginInfo, "Click to visit website!");
			ComponentUtils.nextLine(component,
					Component.text(name + " v" + pluginMeta.getVersion())
							.hoverEvent(HoverEvent.showText(pluginInfo))
							.clickEvent(ClickEvent.openUrl(pluginMeta.getWebsite()))
			);
		} else{
			ComponentUtils.nextLine(component,
					Component.text(name + " v" + pluginMeta.getVersion())
							.hoverEvent(HoverEvent.showText(pluginInfo))
			);
		}
	}
}
