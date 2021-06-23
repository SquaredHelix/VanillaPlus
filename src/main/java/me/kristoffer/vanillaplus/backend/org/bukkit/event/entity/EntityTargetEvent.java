package me.kristoffer.vanillaplus.backend.org.bukkit.event.entity;

public class EntityTargetEvent {

	public class TargetReason {

		public org.bukkit.event.entity.EntityTargetEvent.TargetReason from(String name) {
			return org.bukkit.event.entity.EntityTargetEvent.TargetReason.valueOf(name);
		}

	}

}
