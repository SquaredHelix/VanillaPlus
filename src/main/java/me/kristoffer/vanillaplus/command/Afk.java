package me.kristoffer.vanillaplus.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.kristoffer.vanillacore.Main;

public class Afk implements CommandExecutor {
	public static List<Player> afkPlayers = new ArrayList<Player>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (afkPlayers.contains(player)) {
				afkPlayers.remove(player);
				Main.updateName(player, null);
				player.sendMessage("You are no longer AFK");
			} else {
				afkPlayers.add(player);
				Main.updateName(player, null);
				player.sendMessage("You are now AFK");
			}
		}
		return true;
	}

}
