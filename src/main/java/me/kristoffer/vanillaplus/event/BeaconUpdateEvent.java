package me.kristoffer.vanillaplus.event;

import org.bukkit.block.Beacon;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BeaconUpdateEvent extends Event {

	private final Beacon beacon;

	public BeaconUpdateEvent(Beacon beacon) {
		this.beacon = beacon;
	}

	public Beacon getBeacon() {
		return beacon;
	}

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
