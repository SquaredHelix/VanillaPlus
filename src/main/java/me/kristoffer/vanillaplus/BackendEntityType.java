package me.kristoffer.vanillaplus;

import org.bukkit.entity.EntityType;

public class BackendEntityType {
	
	public EntityType from(String name) {
		return EntityType.valueOf(name);
	}
	
}
