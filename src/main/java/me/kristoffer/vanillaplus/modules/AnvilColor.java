package me.kristoffer.vanillaplus.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.kristoffer.vanillaplus.VanillaPlus;
import net.md_5.bungee.api.ChatColor;

public class AnvilColor extends Module {

	public AnvilColor(VanillaPlus plugin) {
		super(plugin);
		registerListeners();
	}

	@EventHandler
	public void onPrepareAnvil(PrepareAnvilEvent event) {
		ItemStack inputItem = event.getInventory().getItem(0);
		if (inputItem != null) {
			ItemMeta inputItemMeta = inputItem.getItemMeta();
			inputItemMeta.setDisplayName(inputItemMeta.getDisplayName().replace('§', '&'));
			inputItem.setItemMeta(inputItemMeta);
		}

		ItemStack outputItem = event.getResult();
		if (!outputItem.hasItemMeta()) {
			return;
		}
		ItemMeta outputItemMeta = outputItem.getItemMeta();
		if (outputItemMeta != null) {
			outputItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', outputItemMeta.getDisplayName()));
			outputItem.setItemMeta(outputItemMeta);
		}
		event.setResult(outputItem);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getClickedInventory() instanceof AnvilInventory) {
			AnvilInventory inventory = (AnvilInventory) event.getInventory();
			if (!(event.getSlot() == 0))
				return;
			new BukkitRunnable() {

				@Override
				public void run() {
					if (inventory.getItem(0) == null) {
						ItemStack cursorItem = event.getCursor();
						if (cursorItem != null) {
							ItemMeta cursorItemMeta = cursorItem.getItemMeta();
							if (!(cursorItemMeta == null)) {
							if (cursorItemMeta.hasDisplayName()) {
								cursorItemMeta.setDisplayName(
										ChatColor.translateAlternateColorCodes('&', cursorItemMeta.getDisplayName()));
							}
							}
							cursorItem.setItemMeta(cursorItemMeta);
							event.getWhoClicked().setItemOnCursor(cursorItem);
						}
					}
				}
			}.runTaskLater(plugin, 1L);
		}
	}

}
