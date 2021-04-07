package me.kristoffer.vanillaplus.event;

import org.bukkit.block.Beacon;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.kristoffer.vanillaplus.datawrapper.BeaconStatusWrapper;

public class BeaconUpdateEvent extends Event {

	private final Beacon beacon;
	private final BeaconStatusWrapper beaconStatus;

	public BeaconUpdateEvent(Beacon beacon, BeaconStatusWrapper beaconStatus) {
		this.beacon = beacon;
		this.beaconStatus = beaconStatus;
	}

	public Beacon getBeacon() {
		return beacon;
	}

	public BeaconStatusWrapper getBeaconStatus() {
		return beaconStatus;
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
