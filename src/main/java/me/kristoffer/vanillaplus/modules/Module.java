package me.kristoffer.vanillaplus.modules;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import me.kristoffer.vanillaplus.VanillaPlus;

public class Module implements Listener {
	
	public VanillaPlus plugin;
	
	public Module(VanillaPlus plugin) {
		this.plugin = plugin;
	}
	
	public void registerListeners() {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void deregisterListeners() {
		HandlerList.unregisterAll(this);
	}
	
	public void registerCommand(String label) {
		if (this instanceof CommandExecutor) {
			plugin.getCommand(label).setExecutor((CommandExecutor) this);
		}
	}
	
}
