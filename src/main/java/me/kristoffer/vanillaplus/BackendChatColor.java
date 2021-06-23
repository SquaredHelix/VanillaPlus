package me.kristoffer.vanillaplus;

import me.kristoffer.vanillaplus.backend.org.bukkit.ChatColor;

public class BackendChatColor {
	
	public ChatColor from(String name) {
		return ChatColor.valueOf(name);
	}
	
	public String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
		return ChatColor.translateAlternateColorCodes(altColorChar, textToTranslate);
	}
	
	public ChatColor getByChar(String code) {
		return ChatColor.getByChar(code);
	}
	
}
