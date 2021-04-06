package me.kristoffer.vanillaplus.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.kristoffer.vanillaplus.VanillaPlus;

public class Nametag extends Module implements CommandExecutor {

	public Nametag(VanillaPlus plugin) {
		super("Nametag", plugin);
	}
	
	public void onEnable() {
		registerListeners();
		registerCommand("afk");
		registerCommand("namecolor");
		
		namecolorSetup();
		for (Player player : Bukkit.getOnlinePlayers()) {
			updateName(player, null);
		}
	}

	private HashMap<Material, String> allowedDye = new HashMap<Material, String>();
	private final List<String> RAINBOW = Arrays.asList("&9", "&a", "&b", "&c", "&d", "&e");
	private List<String> afkPlayers = new ArrayList<String>();

	private void namecolorSetup() {
		Scoreboard sb = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		allowedDye.put(Material.BLACK_DYE, "&0");
		allowedDye.put(Material.BLUE_DYE, "&1");
		allowedDye.put(Material.GREEN_DYE, "&2");
		allowedDye.put(Material.CYAN_DYE, "&3");
		allowedDye.put(Material.RED_DYE, "&c");
		allowedDye.put(Material.PURPLE_DYE, "&5");
		allowedDye.put(Material.ORANGE_DYE, "&6");
		allowedDye.put(Material.LIGHT_GRAY_DYE, "&7");
		allowedDye.put(Material.GRAY_DYE, "&8");
		allowedDye.put(Material.LIGHT_BLUE_DYE, "&9");
		allowedDye.put(Material.LIME_DYE, "&a");
		allowedDye.put(Material.PINK_DYE, "&d");
		allowedDye.put(Material.YELLOW_DYE, "&e");
		allowedDye.put(Material.WHITE_DYE, "&f");
		allowedDye.put(Material.NAME_TAG, "rainbow");
		for (String color : this.allowedDye.values()) {
			String teamName = color.replace('&', 'c');
			boolean exist = false;
			for (Team scoreboardTeam : sb.getTeams()) {
				if (scoreboardTeam.getName().equals(teamName)) {
					exist = true;
				}
			}
			if (!exist) {
				sb.registerNewTeam(teamName);
			}
			Team team = sb.getTeam(teamName);
			if (team.getName().equals("rainbow")) {
				team.setColor(ChatColor.WHITE);
			} else {
				team.setColor(ChatColor.getByChar(color.replace("&", "")));
			}
			team.setAllowFriendlyFire(true);
			team.setCanSeeFriendlyInvisibles(false);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("afk")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (afkPlayers.contains(player.getName())) {
					afkPlayers.remove(player.getName());
					updateName(player, null);
					player.sendMessage("You are no longer AFK");
				} else {
					afkPlayers.add(player.getName());
					updateName(player, null);
					player.sendMessage("You are now AFK");
				}
			}
			return true;
		}
		if (label.equalsIgnoreCase("namecolor")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				Scoreboard sb = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
				if (this.allowedDye.containsKey(player.getInventory().getItemInMainHand().getType())) {
					String teamName = ((String) this.allowedDye
							.get(player.getInventory().getItemInMainHand().getType())).replace('&', 'c');
					sb.getTeam(teamName).addEntry(player.getName());
					updateName(player, null);
				} else {
					player.sendMessage("Use one of the following dyes to set your name color: ");
					for (Material dye : this.allowedDye.keySet()) {
						if (dye == Material.NAME_TAG) {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r" + rainbow(dye.name())));
						} else {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&',
									String.valueOf((String) this.allowedDye.get(dye)) + dye.name()));
						}
					}
				}
			}
			return true;
		}
		return true;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		Scoreboard sb = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		if (sb.getEntryTeam(event.getPlayer().getName()) != null) {
			updateName(event.getPlayer(), event);
			return;
		}
		event.setFormat(String.valueOf(player.getDisplayName()) + ChatColor.RESET + ": " + event.getMessage());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		updateName(event.getPlayer(), null);
	}

	private void updateName(Player player, AsyncPlayerChatEvent chatEvent) {
		Scoreboard sb = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		if (sb.getEntryTeam(player.getName()) == null) {
			sb.getTeam("cf").addEntry(player.getName());
		}
		Team team = sb.getEntryTeam(player.getName());
		player.setPlayerListName(team.getPrefix() + player.getName());
		if (team.getName().equals("rainbow")) {
			if (chatEvent != null) {
				chatEvent.setFormat(
						team.getColor() + rainbow(player.getName()) + ChatColor.RESET + ": " + chatEvent.getMessage());
			}
			player.setPlayerListName(rainbow(player.getName()));
			if (afkPlayers.contains(player.getName())) {
				player.setPlayerListName(ChatColor.YELLOW + "[AFK] " + rainbow(player.getName()));
			}
		} else {
			if (chatEvent != null) {
				chatEvent.setFormat(
						team.getColor() + player.getDisplayName() + ChatColor.RESET + ": " + chatEvent.getMessage());
			}
			if (afkPlayers.contains(player.getName())) {
				player.setPlayerListName(
						ChatColor.YELLOW + "[AFK] " + team.getColor() + team.getPrefix() + player.getName());
			}
		}
	}

	private String rainbow(String str) {
		int spot = 0;
		String fancyText = "";
		for (char l : str.toCharArray()) {
			String letter = Character.toString(l);
			String t1 = fancyText;
			if (!letter.equalsIgnoreCase(" ")) {
				fancyText = t1 + ChatColor.translateAlternateColorCodes('&', RAINBOW.get(spot)) + letter;
				if (spot == RAINBOW.size() - 1) {
					spot = 0;
				} else {
					spot++;
				}
			} else {
				fancyText = t1 + letter;
			}
		}
		return fancyText;
	}

}
