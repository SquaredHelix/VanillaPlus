package me.kristoffer.vanillaplus.backend.org.bukkit;

public class Raid {

	public class RaidStatus {

		public org.bukkit.Raid.RaidStatus from(String name) {
			return org.bukkit.Raid.RaidStatus.valueOf(name);
		}

	}

}
