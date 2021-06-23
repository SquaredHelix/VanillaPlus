package me.kristoffer.vanillaplus;

import me.kristoffer.vanillaplus.backend.org.bukkit.Material;

public class BackendMaterial {

	public Material from(String name) {
		return Material.getMaterial(name);
	}
	
}
