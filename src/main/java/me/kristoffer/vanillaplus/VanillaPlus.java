package me.kristoffer.vanillaplus;

import java.util.Arrays;
import java.util.List;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.kristoffer.vanillaplus.modules.AnvilColor;
import me.kristoffer.vanillaplus.modules.ItemFrameFix;
import me.kristoffer.vanillaplus.modules.Module;
import me.kristoffer.vanillaplus.modules.Nametag;
import me.kristoffer.vanillaplus.modules.NoCropTrampling;
import me.kristoffer.vanillaplus.modules.NoExplosions;
import me.kristoffer.vanillaplus.modules.NoStrip;
import me.kristoffer.vanillaplus.modules.Sleep;

public class VanillaPlus extends JavaPlugin implements Listener {

	private List<Module> modules;

	public void onEnable() {
		modules = Arrays.asList(new AnvilColor(this), new ItemFrameFix(this), new Nametag(this), new NoCropTrampling(this), new NoExplosions(this), new NoStrip(this),
				new Sleep(this));
	}

	public List<Module> getModules() {
		return modules;
	}

}