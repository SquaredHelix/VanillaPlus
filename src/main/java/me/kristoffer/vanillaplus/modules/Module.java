package me.kristoffer.vanillaplus.modules;

import java.io.File;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import me.kristoffer.vanillaplus.VanillaPlus;

public class Module implements Listener {
	
	public VanillaPlus plugin;
	public YamlConfiguration config;
	
	public Module(String name, VanillaPlus plugin) {
		this.plugin = plugin;
		File configFile = new File(plugin.getDataFolder(), name + ".config");
		this.config = YamlConfiguration.loadConfiguration(configFile);
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
	
	public void onDisable() {
		
	}
	
}
