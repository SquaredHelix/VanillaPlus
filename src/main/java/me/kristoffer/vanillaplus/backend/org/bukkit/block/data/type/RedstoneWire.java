package me.kristoffer.vanillaplus.backend.org.bukkit.block.data.type;

public class RedstoneWire {

	public class Connection {

		public org.bukkit.block.data.type.RedstoneWire.Connection from(String name) {
			return org.bukkit.block.data.type.RedstoneWire.Connection.valueOf(name);
		}

	}

}
