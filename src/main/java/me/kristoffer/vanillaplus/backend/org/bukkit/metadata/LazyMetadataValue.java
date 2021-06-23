package me.kristoffer.vanillaplus.backend.org.bukkit.metadata;

public class LazyMetadataValue {

	public class CacheStrategy {

		public org.bukkit.metadata.LazyMetadataValue.CacheStrategy from(String name) {
			return org.bukkit.metadata.LazyMetadataValue.CacheStrategy.valueOf(name);
		}

	}

}
