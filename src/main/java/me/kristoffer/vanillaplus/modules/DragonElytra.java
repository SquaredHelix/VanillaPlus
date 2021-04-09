package me.kristoffer.vanillaplus.modules;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.kristoffer.vanillaplus.VanillaPlus;

public class DragonElytra extends Module {

	private ArrayList<Player> rewardPlayers = new ArrayList<Player>();

	public DragonElytra(VanillaPlus plugin) {
		super("DragonElytra", plugin);
	}

	public void onEnable() {
		registerListeners();
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (!event.getEntityType().equals(EntityType.ENDER_DRAGON)) {
			return;
		}
		if (event.getDamager().getType().equals(EntityType.PLAYER)) {
			Player damager = (Player) event.getDamager();
			if (!rewardPlayers.contains(damager))
				rewardPlayers.add(damager);
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if (!event.getEntityType().equals(EntityType.ENDER_DRAGON)) {
			return;
		}
		ItemStack elytra = new ItemStack(Material.ELYTRA);
		for (Player player : rewardPlayers) {
			player.getInventory().addItem(elytra);
		}
		rewardPlayers.clear();
	}

}
