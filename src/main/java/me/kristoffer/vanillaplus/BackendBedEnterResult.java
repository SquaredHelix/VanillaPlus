package me.kristoffer.vanillaplus;

import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

public class BackendBedEnterResult {
	
	public BedEnterResult from(String name) {
		return BedEnterResult.valueOf(name);
	}
	
}
