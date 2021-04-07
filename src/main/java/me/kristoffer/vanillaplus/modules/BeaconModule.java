package me.kristoffer.vanillaplus.modules;

it.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import me.kristoffer.vanillaplus.VanillaPlus;
import me.kristoffer.vanillaplus.event.BeaconUpdateEvent;

public class BeaconModule extends Module {

	private final Material protectorMaterial = Material.NETHERITE_BLOCK;
	private final List<Material> beaconStructureMaterials = Arrays.asList(Material.IRON_BLOCK, Material.GOLD_BLOCK,
			Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK, Material.NETHERITE_BLOCK);
	private final int tiers = 4;
	private YamlConfiguration data;

	public BeaconModule(VanillaPlus plugin) {
		super("BeaconModule", plugin);
	}

	public void onEnable() {
		registerListeners();
		data = getConfig("data.dat");
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

	@EventHandler
	public void onBeaconUpdate(BeaconUpdateEvent event) {
		Beacon beacon = event.getBeacon();
		if (beacon.getTier() == 0) {
			updateStoredBeacon(beacon);
		}
	}

	public void updateStoredBeacon(Beacon beacon) {
		Location loc = beacon.getLocation();
		String serializedLocation = loc.getBlockX() + "@" + loc.getBlockY() + "@" + loc.getBlockZ();
		data.set(serializedLocation + ".valid", validityCheck(beacon));
	}

	public boolean validityCheck(Beacon beacon) {
		Location loc = beacon.getLocation();
		int tier = beacon.getTier();
		if (tier == 0) {
			return false;
		}
	}

	public Beacon getBeaconFromStructureBlock(Block block) {
		if (!beaconStructureMaterials.contains(block.getType())) {
			return null;
		}
		int tiersLeft = 5; // Include 5th tier just in case there is overlap between protector material and
							// the structure materials
		Block upperBlock = block;
		while (tiersLeft > 0) {
			upperBlock = upperBlock.getRelative(BlockFace.UP);
			Material upperType = upperBlock.getType();
			if (!(beaconStructureMaterials.contains(upperType) || upperType == Material.BEACON)) {
				break;
			}
			if (upperType == Material.BEACON) {
				return (Beacon) upperBlock;
			}
			tiersLeft--;
		}
		return null;
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
