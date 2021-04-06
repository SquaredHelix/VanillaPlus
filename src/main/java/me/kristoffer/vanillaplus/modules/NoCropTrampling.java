package me.kristoffer.vanillaplus.modules;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.kristoffer.vanillaplus.VanillaPlus;

public class NoCropTrampling extends Module {

	public NoCropTrampling(VanillaPlus plugin) {
		super("NoCropTrampling", plugin);
	}
	
	public void onEnable() {
		registerListeners();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.PHYSICAL)) {
			if (event.getClickedBlock().getType().equals(Material.FARMLAND)) {
				event.setCancelled(true);
			}
		}
	}

}
