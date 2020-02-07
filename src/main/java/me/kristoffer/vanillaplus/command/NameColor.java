package me.kristoffer.vanillaplus.command;

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
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.kristoffer.vanillacore.Main;

public class NameColor implements CommandExecutor {
	private HashMap<Material, String> allowedDye = new HashMap();
	private static final List<String> RAINBOW = Arrays.asList("§9", "§a", "§b", "§c", "§d", "§e");

	public NameColor() {
		Scoreboard sb = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
		this.allowedDye.put(Material.BLACK_DYE, "&0");
		this.allowedDye.put(Material.BLUE_DYE, "&1");
		this.allowedDye.put(Material.GREEN_DYE, "&2");
		this.allowedDye.put(Material.CYAN_DYE, "&3");
		this.allowedDye.put(Material.RED_DYE, "&c");
		this.allowedDye.put(Material.PURPLE_DYE, "&5");
		this.allowedDye.put(Material.ORANGE_DYE, "&6");
		this.allowedDye.put(Material.LIGHT_GRAY_DYE, "&7");
		this.allowedDye.put(Material.GRAY_DYE, "&8");
		this.allowedDye.put(Material.LIGHT_BLUE_DYE, "&9");
		this.allowedDye.put(Material.LIME_DYE, "&a");
		this.allowedDye.put(Material.PINK_DYE, "&d");
		this.allowedDye.put(Material.YELLOW_DYE, "&e");
		this.allowedDye.put(Material.WHITE_DYE, "&f");
		this.allowedDye.put(Material.NAME_TAG, "rainbow");
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

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Scoreboard sb = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
			if (this.allowedDye.containsKey(player.getItemInHand().getType())) {
				String teamName = ((String) this.allowedDye.get(player.getItemInHand().getType())).replace('&', 'c');
				sb.getTeam(teamName).addPlayer(player);
				Main.updateName(player, null);
			} else {
				player.sendMessage("Use one of the following dyes to set your name color: ");
				for (Material dye : this.allowedDye.keySet()) {
					if (dye == Material.NAME_TAG) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								"&r" + rainbow(dye.name())));
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								String.valueOf((String) this.allowedDye.get(dye)) + dye.name()));
					}
				}
			}
		}
		return true;
	}
	
	public static String rainbow(String str) {
		int spot = 0;
        String fancyText = "";
        for(char l : str.toCharArray()){
            String letter = Character.toString(l);
            String t1 = fancyText;
            if(!letter.equalsIgnoreCase(" ")){
                fancyText = t1 + RAINBOW.get(spot) + letter;
                if(spot == RAINBOW.size() - 1){
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