package me.kristoffer.vanillaplus;

import org.bukkit.inventory.ItemStack;

import me.kristoffer.vanillaplus.backend.org.bukkit.Material;

public class BackendItemStack extends ItemStack {
	
	public BackendItemStack() {
		super(Material.AIR);
	}
	
	public BackendItemStack(Material material) {
		super(material);
	}
	
}
