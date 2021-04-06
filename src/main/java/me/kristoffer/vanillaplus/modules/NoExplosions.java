package me.kristoffer.vanillaplus.modules;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.kristoffer.vanillaplus.VanillaPlus;

public class NoExplosions extends Module {

	public NoExplosions(VanillaPlus plugin) {
		super("NoExplosions", plugin);
	}

	public void onEnable() {
		registerListeners();
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		if (event.getEntityType().equals(EntityType.PRIMED_TNT)) {
			return;
		}
		event.setYield(0.0F);
		event.setCancelled(true);
	}

}
