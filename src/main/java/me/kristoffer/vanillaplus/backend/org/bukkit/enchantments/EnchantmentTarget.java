package me.kristoffer.vanillaplus.backend.org.bukkit.enchantments;

public class EnchantmentTarget {

	public org.bukkit.enchantments.EnchantmentTarget from(String name) {
		return org.bukkit.enchantments.EnchantmentTarget.valueOf(name);
	}

}
