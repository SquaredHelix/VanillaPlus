package me.kristoffer.vanillaplus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BackendItemStack extends ItemStack {
	
	public BackendItemStack() {
		super(Material.AIR);
	}
	
	public BackendItemStack(Material material) {
		super(material);
	}
	
}
