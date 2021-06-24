package me.kristoffer.vanillaplus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import me.kristoffer.vanillaplus.backend.org.bukkit.Art;
import me.kristoffer.vanillaplus.backend.org.bukkit.Axis;
import me.kristoffer.vanillaplus.backend.org.bukkit.BanList;
import me.kristoffer.vanillaplus.backend.org.bukkit.ChatColor;
import me.kristoffer.vanillaplus.backend.org.bukkit.CoalType;
import me.kristoffer.vanillaplus.backend.org.bukkit.CropState;
import me.kristoffer.vanillaplus.backend.org.bukkit.Difficulty;
import me.kristoffer.vanillaplus.backend.org.bukkit.DyeColor;
import me.kristoffer.vanillaplus.backend.org.bukkit.Effect;
import me.kristoffer.vanillaplus.backend.org.bukkit.EntityEffect;
import me.kristoffer.vanillaplus.backend.org.bukkit.FireworkEffect;
import me.kristoffer.vanillaplus.backend.org.bukkit.Fluid;
import me.kristoffer.vanillaplus.backend.org.bukkit.FluidCollisionMode;
import me.kristoffer.vanillaplus.backend.org.bukkit.GameMode;
import me.kristoffer.vanillaplus.backend.org.bukkit.GrassSpecies;
import me.kristoffer.vanillaplus.backend.org.bukkit.HeightMap;
import me.kristoffer.vanillaplus.backend.org.bukkit.Instrument;
import me.kristoffer.vanillaplus.backend.org.bukkit.Material;
import me.kristoffer.vanillaplus.backend.org.bukkit.NetherWartsState;
import me.kristoffer.vanillaplus.backend.org.bukkit.Note;
import me.kristoffer.vanillaplus.backend.org.bukkit.Particle;
import me.kristoffer.vanillaplus.backend.org.bukkit.PortalType;
import me.kristoffer.vanillaplus.backend.org.bukkit.Raid;
import me.kristoffer.vanillaplus.backend.org.bukkit.Rotation;
import me.kristoffer.vanillaplus.backend.org.bukkit.SandstoneType;
import me.kristoffer.vanillaplus.backend.org.bukkit.Sound;
import me.kristoffer.vanillaplus.backend.org.bukkit.SoundCategory;
import me.kristoffer.vanillaplus.backend.org.bukkit.Statistic;
import me.kristoffer.vanillaplus.backend.org.bukkit.TreeSpecies;
import me.kristoffer.vanillaplus.backend.org.bukkit.TreeType;
import me.kristoffer.vanillaplus.backend.org.bukkit.Warning;
import me.kristoffer.vanillaplus.backend.org.bukkit.WeatherType;
import me.kristoffer.vanillaplus.backend.org.bukkit.World;
import me.kristoffer.vanillaplus.backend.org.bukkit.WorldType;
import me.kristoffer.vanillaplus.backend.org.bukkit.attribute.Attribute;
import me.kristoffer.vanillaplus.backend.org.bukkit.attribute.AttributeModifier;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.Biome;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.BlockFace;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.PistonMoveReaction;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.banner.PatternType;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.Bisected;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.FaceAttachable;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.Rail;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.Bamboo;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.Bed;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.Bell;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.BigDripleaf;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.Chest;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.Comparator;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.Door;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.Jigsaw;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.PointedDripstone;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.RedstoneWire;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.SculkSensor;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.Slab;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.Stairs;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.StructureBlock;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.TechnicalPiston;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type.Wall;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.structure.Mirror;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.structure.StructureRotation;
import me.kristoffer.vanillaplus.backend.org.bukkit.block.structure.UsageMode;
import me.kristoffer.vanillaplus.backend.org.bukkit.boss.BarColor;
import me.kristoffer.vanillaplus.backend.org.bukkit.boss.BarFlag;
import me.kristoffer.vanillaplus.backend.org.bukkit.boss.BarStyle;
import me.kristoffer.vanillaplus.backend.org.bukkit.boss.DragonBattle;
import me.kristoffer.vanillaplus.backend.org.bukkit.conversations.Conversation;
import me.kristoffer.vanillaplus.backend.org.bukkit.enchantments.EnchantmentTarget;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.AbstractArrow;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.ArmorStand;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Axolotl;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Cat;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.EntityCategory;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.EntityType;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.FishHook;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Fox;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Horse;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Llama;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.MushroomCow;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Panda;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Parrot;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Pose;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Rabbit;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Spellcaster;
import me.kristoffer.vanillaplus.backend.org.bukkit.entity.Villager;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.Event;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.EventPriority;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.block.Action;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.block.BlockIgniteEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.block.CauldronLevelChangeEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.entity.CreatureSpawnEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.entity.CreeperPowerEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.entity.EntityDamageEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.entity.EntityExhaustionEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.entity.EntityPotionEffectEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.entity.EntityRegainHealthEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.entity.EntityTargetEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.entity.EntityTransformEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.entity.EntityUnleashEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.entity.VillagerCareerChangeEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.hanging.HangingBreakEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.inventory.ClickType;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.inventory.DragType;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.inventory.InventoryAction;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.inventory.InventoryType;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.player.PlayerAnimationType;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.player.PlayerBedEnterEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.player.PlayerFishEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.player.PlayerLoginEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.player.PlayerResourcePackStatusEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.player.PlayerTeleportEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.raid.RaidStopEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.server.ServerLoadEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.weather.LightningStrikeEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.world.PortalCreateEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.event.world.TimeSkipEvent;
import me.kristoffer.vanillaplus.backend.org.bukkit.inventory.EquipmentSlot;
import me.kristoffer.vanillaplus.backend.org.bukkit.inventory.InventoryView;
import me.kristoffer.vanillaplus.backend.org.bukkit.inventory.ItemFlag;
import me.kristoffer.vanillaplus.backend.org.bukkit.inventory.MainHand;
import me.kristoffer.vanillaplus.backend.org.bukkit.loot.LootTables;
import me.kristoffer.vanillaplus.backend.org.bukkit.map.MapCursor;
import me.kristoffer.vanillaplus.backend.org.bukkit.material.types.MushroomBlockTexture;
import me.kristoffer.vanillaplus.backend.org.bukkit.metadata.LazyMetadataValue;
import me.kristoffer.vanillaplus.backend.org.bukkit.permissions.PermissionDefault;
import me.kristoffer.vanillaplus.backend.org.bukkit.plugin.PluginLoadOrder;
import me.kristoffer.vanillaplus.backend.org.bukkit.plugin.ServicePriority;
import me.kristoffer.vanillaplus.backend.org.bukkit.plugin.messaging.PluginChannelDirection;
import me.kristoffer.vanillaplus.backend.org.bukkit.potion.PotionType;
import me.kristoffer.vanillaplus.backend.org.bukkit.scoreboard.DisplaySlot;
import me.kristoffer.vanillaplus.backend.org.bukkit.scoreboard.RenderType;
import me.kristoffer.vanillaplus.backend.org.bukkit.scoreboard.Team;

public class ModuleLoader {

	private GlobalEventListener globalEventListener;
	private VanillaPlus plugin;
	private Context currentContext;

	private boolean finishedLoading = false;

	public ModuleLoader(VanillaPlus plugin) {
		this.plugin = plugin;
		globalEventListener = new GlobalEventListener();
		globalEventListener.doStuff(plugin);

		try {
			Files.walk(Paths.get(plugin.getDataFolder().getAbsolutePath())).filter(Files::isRegularFile)
					.forEach(path -> {
						Context context = newContext();
						currentContext = context;
						Value bindings = context.getBindings("js");

						FileReader fileReader = null;
						String localPath = path.toString()
								.replace(plugin.getDataFolder().getAbsolutePath().toString() + "\\", "");
						String fileName = path.toString();

						if (!(fileName.endsWith(".js") || fileName.endsWith(".mjs"))) {
							return;
						}
						bindings.putMember("this", localPath);
						File file = new File(plugin.getDataFolder().getAbsolutePath() + "/" + localPath);
						if (file.isDirectory())
							return;
						try {
							fileReader = new FileReader(file.getAbsolutePath());
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						BufferedReader bufferedReader = new BufferedReader(fileReader);
						ArrayList<String> script = new ArrayList<String>();
						try {
							while (bufferedReader.ready()) {
								String line = bufferedReader.readLine();
								if (!line.endsWith(";")) {
									// line += ';'; --- probably not needed
								}
								script.add(line);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						String strScript = "";
						for (String line : script) {
							strScript += line + "\n";
						}
						try {
							context.eval(Source.newBuilder("js", file).build());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						Bukkit.getConsoleSender().sendMessage(
								"Loaded " + org.bukkit.ChatColor.GREEN + context.getBindings("js").getMember("this"));
						try {
							fileReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}

					});
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		finishedLoading = true;
	}

	public void onCommand(String command, Value function) {
		addCommand(command, function);
	}

	public void addCommand(String command, Value function) {
		try {
			Field bukkitCmdMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			bukkitCmdMap.setAccessible(true);
			final CommandMap commandMap = (CommandMap) bukkitCmdMap.get(Bukkit.getServer());
			commandMap.register("vanillaplus", new Command(command) {

				@Override
				public boolean execute(CommandSender sender, String commandLabel, String[] args) {
					Player player = (Player) sender;
					function.executeVoid(player, args);
					return true;
				}

			});
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void generateModule(String[] listeners) {
		if (finishedLoading) {
			System.err.println("!!! You cannot generate modules at runtime !!!");
			return;
		}
		for (String listener : listeners) {
			globalEventListener.registerFunction(listener, event -> {
				Value bindings = currentContext.getBindings("js");
				bindings.putMember("event", event);
				currentContext.eval(Source.create("js", listener + "(event)"));
				bindings.removeMember("event");
			});
		}
	}

	public void exec(String exec) {
		currentContext.eval(Source.create("js", exec));
	}

	public void exec(String exec, Map<String, Object> map) {
		Value bindings = currentContext.getBindings("js");
		for (String key : map.keySet()) {
			bindings.putMember(key, map.get(key));
		}
		currentContext.eval(Source.create("js", exec));
	}

	public BukkitTask scheduleDelayed(int ticks, Value function) {
		BukkitTask task = new BukkitRunnable() {

			@Override
			public void run() {
				function.executeVoid();
			}

		}.runTaskLater(plugin, ticks);
		return task;
	}

	public BukkitTask scheduleRepeating(int delay, int period, Value function) {
		BukkitTask task = new BukkitRunnable() {

			@Override
			public void run() {
				function.executeVoid();
			}

		}.runTaskTimer(plugin, delay, period);
		return task;
	}

	public VanillaPlus getPlugin() {
		return plugin;
	}

	public Context newContext() {
		Context polyglot = Context.newBuilder("js").allowHostClassLookup(s -> true).allowHostAccess(HostAccess.ALL)
				.allowIO(true).build();

		Value bindings = polyglot.getBindings("js");
		bindings.putMember("Bukkit", plugin.getServer());
		bindings.putMember("Runnable", Runnable.class);
		bindings.putMember("Math", Math.class);
		bindings.putMember("AnvilInventory", AnvilInventory.class);
		bindings.putMember("loader", this);
		bindings.putMember("import", ImportObj.class);

		// AUTOMATICALLY GENERATED BINDINGS
		bindings.putMember("Art", new Art());
		bindings.putMember("Attribute", new Attribute());
		bindings.putMember("AttributeModifier", new AttributeModifier());
		bindings.putMember("Axis", new Axis());
		bindings.putMember("BanList", new BanList());
		bindings.putMember("PatternType", new PatternType());
		bindings.putMember("Biome", new Biome());
		bindings.putMember("BlockFace", new BlockFace());
		bindings.putMember("Bisected", new Bisected());
		bindings.putMember("FaceAttachable", new FaceAttachable());
		bindings.putMember("Rail", new Rail());
		bindings.putMember("Bamboo", new Bamboo());
		bindings.putMember("Bed", new Bed());
		bindings.putMember("Bell", new Bell());
		bindings.putMember("BigDripleaf", new BigDripleaf());
		bindings.putMember("Chest", new Chest());
		bindings.putMember("Comparator", new Comparator());
		bindings.putMember("Door", new Door());
		bindings.putMember("Jigsaw", new Jigsaw());
		bindings.putMember("PointedDripstone", new PointedDripstone());
		bindings.putMember("RedstoneWire", new RedstoneWire());
		bindings.putMember("SculkSensor", new SculkSensor());
		bindings.putMember("Slab", new Slab());
		bindings.putMember("Stairs", new Stairs());
		bindings.putMember("StructureBlock", new StructureBlock());
		bindings.putMember("TechnicalPiston", new TechnicalPiston());
		bindings.putMember("Wall", new Wall());
		bindings.putMember("PistonMoveReaction", new PistonMoveReaction());
		bindings.putMember("Mirror", new Mirror());
		bindings.putMember("StructureRotation", new StructureRotation());
		bindings.putMember("UsageMode", new UsageMode());
		bindings.putMember("BarColor", new BarColor());
		bindings.putMember("BarFlag", new BarFlag());
		bindings.putMember("BarStyle", new BarStyle());
		bindings.putMember("DragonBattle", new DragonBattle());
		bindings.putMember("ChatColor", new ChatColor());
		bindings.putMember("CoalType", new CoalType());
		bindings.putMember("Conversation", new Conversation());
		bindings.putMember("CropState", new CropState());
		bindings.putMember("Difficulty", new Difficulty());
		bindings.putMember("DyeColor", new DyeColor());
		bindings.putMember("Effect", new Effect());
		bindings.putMember("EnchantmentTarget", new EnchantmentTarget());
		bindings.putMember("AbstractArrow", new AbstractArrow());
		bindings.putMember("ArmorStand", new ArmorStand());
		bindings.putMember("Axolotl", new Axolotl());
		bindings.putMember("Cat", new Cat());
		bindings.putMember("EntityCategory", new EntityCategory());
		bindings.putMember("EntityType", new EntityType());
		bindings.putMember("FishHook", new FishHook());
		bindings.putMember("Fox", new Fox());
		bindings.putMember("Horse", new Horse());
		bindings.putMember("Llama", new Llama());
		bindings.putMember("MushroomCow", new MushroomCow());
		bindings.putMember("Panda", new Panda());
		bindings.putMember("Parrot", new Parrot());
		bindings.putMember("Pose", new Pose());
		bindings.putMember("Rabbit", new Rabbit());
		bindings.putMember("Spellcaster", new Spellcaster());
		bindings.putMember("Villager", new Villager());
		bindings.putMember("EntityEffect", new EntityEffect());
		bindings.putMember("Action", new Action());
		bindings.putMember("BlockIgniteEvent", new BlockIgniteEvent());
		bindings.putMember("CauldronLevelChangeEvent", new CauldronLevelChangeEvent());
		bindings.putMember("CreatureSpawnEvent", new CreatureSpawnEvent());
		bindings.putMember("CreeperPowerEvent", new CreeperPowerEvent());
		bindings.putMember("EntityDamageEvent", new EntityDamageEvent());
		bindings.putMember("EntityExhaustionEvent", new EntityExhaustionEvent());
		bindings.putMember("EntityPotionEffectEvent", new EntityPotionEffectEvent());
		bindings.putMember("EntityRegainHealthEvent", new EntityRegainHealthEvent());
		bindings.putMember("EntityTargetEvent", new EntityTargetEvent());
		bindings.putMember("EntityTransformEvent", new EntityTransformEvent());
		bindings.putMember("EntityUnleashEvent", new EntityUnleashEvent());
		bindings.putMember("VillagerCareerChangeEvent", new VillagerCareerChangeEvent());
		bindings.putMember("Event", new Event());
		bindings.putMember("EventPriority", new EventPriority());
		bindings.putMember("HangingBreakEvent", new HangingBreakEvent());
		bindings.putMember("ClickType", new ClickType());
		bindings.putMember("DragType", new DragType());
		bindings.putMember("InventoryAction", new InventoryAction());
		bindings.putMember("InventoryType", new InventoryType());
		bindings.putMember("AsyncPlayerPreLoginEvent", new AsyncPlayerPreLoginEvent());
		bindings.putMember("PlayerAnimationType", new PlayerAnimationType());
		bindings.putMember("PlayerBedEnterEvent", new PlayerBedEnterEvent());
		bindings.putMember("PlayerFishEvent", new PlayerFishEvent());
		bindings.putMember("PlayerLoginEvent", new PlayerLoginEvent());
		bindings.putMember("PlayerResourcePackStatusEvent", new PlayerResourcePackStatusEvent());
		bindings.putMember("PlayerTeleportEvent", new PlayerTeleportEvent());
		bindings.putMember("RaidStopEvent", new RaidStopEvent());
		bindings.putMember("ServerLoadEvent", new ServerLoadEvent());
		bindings.putMember("LightningStrikeEvent", new LightningStrikeEvent());
		bindings.putMember("PortalCreateEvent", new PortalCreateEvent());
		bindings.putMember("TimeSkipEvent", new TimeSkipEvent());
		bindings.putMember("FireworkEffect", new FireworkEffect());
		bindings.putMember("Fluid", new Fluid());
		bindings.putMember("FluidCollisionMode", new FluidCollisionMode());
		bindings.putMember("GameMode", new GameMode());
		bindings.putMember("GrassSpecies", new GrassSpecies());
		bindings.putMember("HeightMap", new HeightMap());
		bindings.putMember("Instrument", new Instrument());
		bindings.putMember("EquipmentSlot", new EquipmentSlot());
		bindings.putMember("InventoryView", new InventoryView());
		bindings.putMember("ItemFlag", new ItemFlag());
		bindings.putMember("MainHand", new MainHand());
		bindings.putMember("LootTables", new LootTables());
		bindings.putMember("MapCursor", new MapCursor());
		bindings.putMember("MushroomBlockTexture", new MushroomBlockTexture());
		bindings.putMember("Material", new Material());
		bindings.putMember("LazyMetadataValue", new LazyMetadataValue());
		bindings.putMember("NetherWartsState", new NetherWartsState());
		bindings.putMember("Note", new Note());
		bindings.putMember("Particle", new Particle());
		bindings.putMember("PermissionDefault", new PermissionDefault());
		bindings.putMember("PluginChannelDirection", new PluginChannelDirection());
		bindings.putMember("PluginLoadOrder", new PluginLoadOrder());
		bindings.putMember("ServicePriority", new ServicePriority());
		bindings.putMember("PortalType", new PortalType());
		bindings.putMember("PotionType", new PotionType());
		bindings.putMember("Raid", new Raid());
		bindings.putMember("Rotation", new Rotation());
		bindings.putMember("SandstoneType", new SandstoneType());
		bindings.putMember("DisplaySlot", new DisplaySlot());
		bindings.putMember("RenderType", new RenderType());
		bindings.putMember("Team", new Team());
		bindings.putMember("Sound", new Sound());
		bindings.putMember("SoundCategory", new SoundCategory());
		bindings.putMember("Statistic", new Statistic());
		bindings.putMember("TreeSpecies", new TreeSpecies());
		bindings.putMember("TreeType", new TreeType());
		bindings.putMember("Warning", new Warning());
		bindings.putMember("WeatherType", new WeatherType());
		bindings.putMember("World", new World());
		bindings.putMember("WorldType", new WorldType());

		return polyglot;
	}

	public class ImportObj {

		public ImportObj(String localPath) {
			System.out.println(localPath);
		}

	}

}
