package me.kristoffer.vanillaplus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

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

public class ModuleLoader {

	private Context polyglot;
	private GlobalEventListener globalEventListener;
	private VanillaPlus plugin;

	private boolean finishedLoading = false;

	public ModuleLoader(VanillaPlus plugin) {
		this.plugin = plugin;
		globalEventListener = new GlobalEventListener();
		globalEventListener.doStuff(plugin);

		polyglot = Context.newBuilder("js").allowHostClassLookup(s -> true).allowHostAccess(HostAccess.ALL)
				.allowIO(true).build();

		Value bindings = polyglot.getBindings("js");
		bindings.putMember("Bukkit", plugin.getServer());
		bindings.putMember("Runnable", Runnable.class);
		bindings.putMember("ItemStack", BackendItemStack.class);
		bindings.putMember("Material", new BackendMaterial());
		bindings.putMember("Action", new BackendAction());
		bindings.putMember("Math", Math.class);
		bindings.putMember("BedEnterResult", new BackendBedEnterResult());
		bindings.putMember("ChatColor", new BackendChatColor());
		bindings.putMember("AnvilInventory", AnvilInventory.class);
		bindings.putMember("InventoryType", new BackendInventoryType());
		bindings.putMember("EntityType", new BackendEntityType());
		bindings.putMember("loader", this);
		FileReader fileReader = null;
		for (String fileName : plugin.getDataFolder().list()) {
			if (!fileName.endsWith(".js")) {
				continue;
			}
			bindings.putMember("this", fileName);
			File file = new File(plugin.getDataFolder().getAbsolutePath() + "/" + fileName);
			if (file.isDirectory())
				continue;
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
			polyglot.eval(Source.create("js", strScript));
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
					function.executeVoid(player);
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
		System.out.println(polyglot.getBindings("js").getMember("this"));
		for (String listener : listeners) {
			globalEventListener.registerFunction(listener, event -> {
				Value bindings = polyglot.getBindings("js");
				bindings.putMember("event", event);
				polyglot.eval(Source.create("js", listener + "(event)"));
				bindings.removeMember("event");
			});
		}
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

}
