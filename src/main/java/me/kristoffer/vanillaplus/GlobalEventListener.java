package me.kristoffer.vanillaplus;

import java.util.ArrayList;
/*
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;

public class GlobalEventListener implements Listener {

	private HashMap<String, ArrayList<Runnable<Event>>> functions = new HashMap<String, ArrayList<Runnable<Event>>>();

	public void doStuff(Plugin plugin) {
		// search event classes
		Reflections reflections = new Reflections("org.bukkit");// change to also find custom events
		Set<Class<? extends Event>> eventClasses = reflections.getSubTypesOf(Event.class).stream()
				.filter(clazz -> Arrays.stream(clazz.getDeclaredFields())
						.anyMatch(field -> field.getType().getName().endsWith("HandlerList")))
				.collect(Collectors.toSet());
		plugin.getLogger().info("Found " + eventClasses.size() + " available events!");
		plugin.getLogger().info(eventClasses.stream().map(Class::getName).collect(Collectors.joining(", ")));

		// register events
		EventExecutor eventExecutor = (listener, event) -> iGetCalledForEveryEvent(event);
		eventClasses.forEach(clazz -> plugin.getServer().getPluginManager().registerEvent(clazz, this,
				EventPriority.MONITOR, eventExecutor, plugin));
	}

	private final String[] ignored = { "VehicleBlockCollisionEvent", "EntityAirChangeEvent", "VehicleUpdateEvent",
			"ChunkUnloadEvent", "ChunkLoadEvent" };

	public void iGetCalledForEveryEvent(Event event) {
		try {
			if (Arrays.stream(ignored).anyMatch(ignored -> event.getEventName().equals(ignored))) {
				return;
			}
			// plugin.getLogger().info(event.getEventName() + " was called!");
			ArrayList<Runnable<Event>> functionList = functions.get(event.getEventName());
			if (functionList == null) {
				return;
			}
			if (functionList.size() < 1) {
				return;
			}
			for (Runnable<Event> runnable : functionList) {
				runnable.run(event);
			}
		} catch (ConcurrentModificationException ex) {
			System.err.println("!!! You cannot register listeners at runtime !!!");
		}
	}

	public void registerFunction(String listenerName, Runnable<Event> runnable) {
		if (functions.containsKey(listenerName)) {
			functions.get(listenerName).add(runnable); // maybe todo
		} else {
			ArrayList<Runnable<Event>> functionList = new ArrayList<Runnable<Event>>();
			functionList.add(runnable);
			functions.put(listenerName, functionList);
		}
	}

}