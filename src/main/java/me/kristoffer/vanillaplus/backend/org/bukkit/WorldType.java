package me.kristoffer.vanillaplus.backend.org.bukkit;

public class WorldType {

	public org.bukkit.WorldType from(String name) {
		return org.bukkit.WorldType.valueOf(name);
	}

}
