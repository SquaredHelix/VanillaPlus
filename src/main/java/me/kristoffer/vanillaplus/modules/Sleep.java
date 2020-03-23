package me.kristoffer.vanillaplus.modules;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;

import me.kristoffer.vanillaplus.VanillaPlus;

public class Sleep extends Module {
	
	public VanillaPlus plugin;
	
	public int nightstart = 12541;
	public int nightstop = 23458;
	
	public Sleep(VanillaPlus plugin) {
		super(plugin);
		this.plugin = plugin;
		registerListeners();
	}

	private List<Player> sleeping = new ArrayList<Player>();
	private List<Player> cooldown = new ArrayList<Player>();
	
	private BukkitTask sleepTask;
	
	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		if (cooldown.contains(event.getPlayer())) {
			event.setCancelled(true); // Cancel if player is in cooldown
			return;
		}
		if (sleepTask != null) {
			if (!sleepTask.isCancelled()) {
				event.setCancelled(true); // Prevent player sleeping if sleep is already scheduled
				return;
			}
		}
		int neededPlayers = (int) Math.ceil(plugin.getServer().getOnlinePlayers().size() / 5.0);
		if (event.getBedEnterResult().equals(BedEnterResult.OK)) {
			sleeping.add(event.getPlayer());
			Bukkit.broadcastMessage(event.getPlayer().getName() + " is now sleeping " + ChatColor.YELLOW + "("
					+ sleeping.size() + "/" + neededPlayers + ")");
		}
		if (neededPlayers <= sleeping.size()) {
			if (isNight() || getMainworld().isThundering()) {
				sleepTask = new BukkitRunnable() {
					
					@Override
					public void run() {
						event.getPlayer().getWorld().setFullTime(24000);
						if (getMainworld().isThundering()) {
							getMainworld().setWeatherDuration(1);
						}
						sleeping.clear();
						sleepTask = null;
					}
				}.runTaskLater(plugin, 101L); // Schedule sleep in 101 ticks (To simulate the same wait as vanilla)
			}
		}
	}

	@EventHandler
	public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
		sleeping.remove(event.getPlayer());
		if (isNight() || getMainworld().isThundering()) {
			Bukkit.broadcastMessage(event.getPlayer().getName() + " has stopped sleeping");
			cooldown.add(event.getPlayer());
			new BukkitRunnable() {
				
				@Override
				public void run() {
					cooldown.remove(event.getPlayer());
				}
			}.runTaskLater(plugin, 20L*5);
		}
		if (sleepTask != null) {
			sleepTask.cancel();
		}
	}
	
	private World getMainworld() {
		return Bukkit.getWorlds().get(0);
	}
	
	private long getTime() {
		return getMainworld().getTime();
	}
	
	private boolean isNight() {
		long time = getTime();
		if (time >= nightstart && time <= nightstop) {
			return true;
		}
		return false;
	}

}
