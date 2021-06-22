package me.kristoffer.vanillaplus;

import org.bukkit.event.block.Action;

public class BackendAction {

	public Action getAction(String name) {
		return Action.valueOf(name);
	}
	
}
