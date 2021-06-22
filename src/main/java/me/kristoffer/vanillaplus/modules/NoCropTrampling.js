function PlayerInteractEvent(event) {
	if (event.getAction().equals(Action.getAction("PHYSICAL"))) {
		if (event.getClickedBlock().getType().equals(Material.getMaterial("FARMLAND"))) {
			event.setCancelled(true)
		}
	}
}

loader.generateModule(["PlayerInteractEvent"])