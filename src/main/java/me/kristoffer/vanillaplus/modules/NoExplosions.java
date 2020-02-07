package me.kristoffer.vanillaplus.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.kristoffer.vanillaplus.VanillaPlus;

public class NoExplosions extends Module {

	public NoExplosions(VanillaPlus plugin) {
		super(plugin);
		registerListeners();
	}
	
	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		event.setYield(0.0F);
		event.setCancelled(true);
	}
	
}
