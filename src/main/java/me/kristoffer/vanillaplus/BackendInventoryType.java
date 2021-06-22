package me.kristoffer.vanillaplus;

import org.bukkit.event.inventory.InventoryType;

public class BackendInventoryType {
	
	public InventoryType from(String name) {
		return InventoryType.valueOf(name);
	}
	
}
