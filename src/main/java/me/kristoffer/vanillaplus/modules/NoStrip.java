package me.kristoffer.vanillaplus.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import me.kristoffer.vanillaplus.VanillaPlus;

public class NoStrip extends Module {

	private List<Recipe> recipes = new ArrayList<Recipe>();

	public NoStrip(VanillaPlus plugin) {
		super(plugin);
		registerListeners();

		for (Material material : logMaterials) {
			for (Material material2 : axeMaterials) {
				NamespacedKey key = new NamespacedKey(plugin, material.getKey().toString().replace("minecraft:", "")
						+ "_" + material2.getKey().toString().replace("minecraft:", ""));
				ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(translateLog(material)));
				recipe.addIngredient(material);
				recipe.addIngredient(material2);
				recipes.add(recipe);
				Bukkit.addRecipe(recipe);
			}
		}
	}

	private List<Material> logMaterials = Arrays.asList(Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
			Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG, Material.CRIMSON_STEM,
			Material.WARPED_STEM);
	private List<Material> axeMaterials = Arrays.asList(Material.IRON_AXE, Material.WOODEN_AXE, Material.STONE_AXE,
			Material.DIAMOND_AXE, Material.GOLDEN_AXE, Material.NETHERITE_AXE);

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			if (!logMaterials.contains(event.getClickedBlock().getType()))
				return;
			if (!axeMaterials.contains(player.getInventory().getItemInMainHand().getType())
					&& !axeMaterials.contains(player.getInventory().getItemInOffHand().getType()))
				return;
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getInventory() instanceof CraftingInventory))
			return;
		CraftingInventory inventory = (CraftingInventory) event.getInventory();
		int axeCount = 0;
		ItemStack axeItemstack = null;
		for (Material material : axeMaterials) {
			if (inventory.contains(material)) {
				axeCount++;
				for (ItemStack itemstack : inventory.getContents()) {
					if (itemstack.getType().equals(material)) {
						axeItemstack = itemstack;
					}
				}
			}
		}
		if (axeCount == 0 || axeCount > 1)
			return;
		int logCount = 0;
		for (Material material : logMaterials) {
			if (inventory.contains(material)) {
				logCount++;
			}
		}
		if (logCount == 0 || logCount > 1)
			return;
		int logAmount = 0;
		for (ItemStack itemStack : inventory) {
			if (itemStack != null) {
				if (logMaterials.contains(itemStack.getType())) {
					if (logAmount > 0) {
						return;
					}
					logAmount += itemStack.getAmount();
				}
			}
		}
		if (logCount == 0 || logCount > 64)
			return;
		ItemStack result = inventory.getResult();
		result.setAmount(logAmount);
		inventory.setResult(result);
		if (event.getRawSlot() == 0) {
			inventory.clear();
			event.getViewers().get(0).getInventory().addItem(axeItemstack);
			event.getViewers().get(0).getInventory().addItem(result);
		}
	}

	private Material translateLog(Material material) {
		switch (material) {
		case OAK_LOG:
			return Material.STRIPPED_OAK_LOG;
		case SPRUCE_LOG:
			return Material.STRIPPED_SPRUCE_LOG;
		case BIRCH_LOG:
			return Material.STRIPPED_BIRCH_LOG;
		case JUNGLE_LOG:
			return Material.STRIPPED_JUNGLE_LOG;
		case ACACIA_LOG:
			return Material.STRIPPED_ACACIA_LOG;
		case DARK_OAK_LOG:
			return Material.STRIPPED_DARK_OAK_LOG;
		case CRIMSON_STEM:
			return Material.STRIPPED_CRIMSON_STEM;
		case WARPED_STEM:
			return Material.STRIPPED_WARPED_STEM;
		default:
			break;
		}
		return material;
	}

}
