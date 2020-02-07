package me.kristoffer.vanillaplus.modules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

import me.kristoffer.vanillaplus.VanillaPlus;

public class Sleep extends Module {

	public Sleep(VanillaPlus plugin) {
		super(plugin);
		registerListeners();
	}

	private List<Player> sleeping = new ArrayList<Player>();

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		int neededPlayers = (int) Math.ceil(plugin.getServer().getOnlinePlayers().size() / 5.0);
		if (event.getBedEnterResult().equals(BedEnterResult.OK)) {
			sleeping.add(event.getPlayer());
			Bukkit.broadcastMessage(event.getPlayer().getName() + " is now sleeping " + ChatColor.YELLOW + "("
					+ sleeping.size() + "/" + neededPlayers + ")");
		}
		if (neededPlayers <= sleeping.size()) {
			event.getPlayer().getWorld().setFullTime(24000);
			sleeping.clear();
		}
	}

	@EventHandler
	public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
		sleeping.remove(event.getPlayer());
	}

}
