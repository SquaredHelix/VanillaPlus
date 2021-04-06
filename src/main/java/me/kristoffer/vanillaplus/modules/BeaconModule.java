package me.kristoffer.vanillaplus.modules;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import me.kristoffer.vanillaplus.VanillaPlus;

public class BeaconModule extends Module {

	private Material protectorMaterial = Material.NETHERITE_BLOCK;
	private int tiers = 4;

	public BeaconModule(VanillaPlus plugin) {
		super("BeaconModule", plugin);
	}

	public void onEnable() {
		registerListeners();
		getConfig("data.dat");
	}

	@EventHandler
	public void onBlockPlacement(BlockPlaceEvent event) {
		if (event.getBlock().getType() == protectorMaterial) {
			Location location = event.getBlock().getLocation();
			// Loop all 4 tiers above placed block
			boolean validBeacon = false;
			for (int i = 0; i <= tiers; i++) {
				Block block = location.getWorld().getBlockAt(location.add(0, 1, 0));
				validBeacon = checkNetheriteValidity(block);
				if (validBeacon)
					break;
			}
			// Store (valid) beacon to file
		}
	}

	public boolean checkNetheriteValidity(Block block) {
		if (!(block.getType() == Material.BEACON)) {
			return false;
		}
		Beacon beacon = (Beacon) block.getState();
		if (beacon.getTier() == 0) {
			return false;
		}
		return true;
	}

}
