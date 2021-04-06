package me.kristoffer.vanillaplus.modules;

import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import me.kristoffer.vanillaplus.VanillaPlus;

public class Core extends Module {

	private YamlConfiguration settings;

	public Core(VanillaPlus plugin) {
		super("Core", plugin);
		setDataFolder(""); // Set data folder to back to root
		settings = getConfig("settings.yml");
	}

	public void afterModuleHook() {
		for (Module module : plugin.getModules()) {
			if (module instanceof Core) {
				return;
			}
			settings.addDefault("moduleToggles." + module.name, true);
		}
		try {
			settings.save(getFile("settings.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
