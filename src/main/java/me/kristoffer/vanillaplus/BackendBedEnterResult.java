package me.kristoffer.vanillaplus;

import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

public class BackendBedEnterResult {
	
	public BedEnterResult getBedEnterResult(String name) {
		return BedEnterResult.valueOf(name);
	}
	
}
