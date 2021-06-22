package me.kristoffer.vanillaplus;

import java.util.Arrays;
import java.util.List;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.kristoffer.vanillaplus.modules.AnvilColor;
import me.kristoffer.vanillaplus.modules.BeaconModule;
import me.kristoffer.vanillaplus.modules.Core;
import me.kristoffer.vanillaplus.modules.DragonElytra;
import me.kristoffer.vanillaplus.modules.ItemFrameFix;
import me.kristoffer.vanillaplus.modules.Module;
import me.kristoffer.vanillaplus.modules.Nametag;
import me.kristoffer.vanillaplus.modules.NoExplosions;
import me.kristoffer.vanillaplus.modules.NoStrip;

public class VanillaPlus extends JavaPlugin implements Listener {

	private Core core;
	private List<Module> standardModules;
	private List<Module> loadedModules;

	public void onEnable() {
		new ModuleLoader(this);
		core = new Core(this);
		standardModules = Arrays.asList(new Core(this), new DragonElytra(this), new AnvilColor(this),
				new BeaconModule(this), new ItemFrameFix(this), new Nametag(this), new NoExplosions(this),
				new NoStrip(this));
		core.afterModuleHook();
		loadedModules = standardModules;
		for (Module module : loadedModules) {
			module.onEnable();
		}
	}

	public void onDisable() {
		for (Module module : standardModules) {
			module.onDisable();
		}
	}

	public Core getCore() {
		return core;
	}

	public List<Module> getStandardModules() {
		return standardModules;
	}

	public List<Module> getLoadedModules() {
		return loadedModules;
	}

}