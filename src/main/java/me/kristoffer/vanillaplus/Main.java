package me.kristoffer.vanillaplus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.kristoffer.vanillacore.command.Afk;
import me.kristoffer.vanillacore.command.NameColor;

public class Main extends JavaPlugin implements Listener {
	public void onEnable() {
		getCommand("namecolor").setExecutor(new NameColor());
		getCommand("afk").setExecutor(new Afk());
		getServer().getPluginManager().registerEvents(this, this);
		for (Player player : Bukkit.getOnlinePlayers()) {
			updateName(player, null);
		}
	}

	@EventHandler
	public void onChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		Scoreboard sb = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		if (sb.getPlayerTeam(getServer().getOfflinePlayer(event.getPlayer().getName())) != null) {
			updateName(event.getPlayer(), event);
			return;
		}
		event.setFormat(String.valueOf(player.getDisplayName()) + ChatColor.RESET + ": " + event.getMessage());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		updateName(event.getPlayer(), null);
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		event.setYield(0.0F);
		event.setCancelled(true);
	}

	List<Player> sleeping = new ArrayList<Player>();

	@EventHandler
	public void onSleep(PlayerBedEnterEvent event) {
		int neededPlayers = (int) Math.ceil(getServer().getOnlinePlayers().size() / 5.0);
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

	public void onWake(PlayerBedLeaveEvent event) {
		sleeping.remove(event.getPlayer());
	}

	public static void updateName(Player player, PlayerChatEvent chatEvent) {
		Scoreboard sb = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		Team team = sb.getPlayerTeam(Bukkit.getServer().getOfflinePlayer(player.getName()));
		if (!(sb.getPlayerTeam(Bukkit.getServer().getOfflinePlayer(player.getName())) != null)) {
			return;
		}
		player.setPlayerListName(team.getPrefix() + player.getName());
		if (team.getName().equals("rainbow")) {
			if (chatEvent != null) {
				chatEvent.setFormat(team.getColor() + NameColor.rainbow(player.getName()) + ChatColor.RESET + ": "
						+ chatEvent.getMessage());
			}
			player.setPlayerListName(NameColor.rainbow(player.getName()));
			if (Afk.afkPlayers.contains(player)) {
				player.setPlayerListName(ChatColor.YELLOW + "[AFK] " + NameColor.rainbow(player.getName()));
			}
		} else {
			if (chatEvent != null) {
				chatEvent.setFormat(
						team.getColor() + player.getDisplayName() + ChatColor.RESET + ": " + chatEvent.getMessage());
			}
			if (Afk.afkPlayers.contains(player)) {
				player.setPlayerListName(ChatColor.YELLOW + "[AFK] " + team.getPrefix() + player.getName());
			}
		}
	}
}