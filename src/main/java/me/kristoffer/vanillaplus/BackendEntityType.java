package me.kristoffer.vanillaplus;

import me.kristoffer.vanillaplus.backend.org.bukkit.entity.EntityType;

public class BackendEntityType {
	
	public EntityType from(String name) {
		return EntityType.valueOf(name);
	}
	
}
