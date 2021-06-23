package me.kristoffer.vanillaplus.backend.org.bukkit.event.entity;

public class CreeperPowerEvent {

	public class PowerCause {

		public org.bukkit.event.entity.CreeperPowerEvent.PowerCause from(String name) {
			return org.bukkit.event.entity.CreeperPowerEvent.PowerCause.valueOf(name);
		}

	}

}
