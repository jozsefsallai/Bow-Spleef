/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef.manager;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerManager {

	private static String escapeDisplayName(String displayName) {
		displayName = displayName.replaceAll(":", "[colon]");
		displayName = displayName.replaceAll(";", "[semi]");
		displayName = displayName.replaceAll("@", "[at]");
		displayName = displayName.replaceAll("#", "[hash]");
		return displayName;
	}

	private static String unescapeDisplayName(String displayName) {
		displayName = displayName.replaceAll("\\[colon\\]", ":");
		displayName = displayName.replaceAll("\\[semi\\]", ";");
		displayName = displayName.replaceAll("\\[at\\]", "@");
		displayName = displayName.replaceAll("\\[hash\\]", "#");
		return displayName;
	}

	public static void saveInventory(Player player) {
		Inventory inventory = player.getInventory();
		String output = inventory.getSize() + ";";

		for (int i = 0; i < inventory.getSize(); i++) {
			ItemStack is = inventory.getItem(i);

			if (is != null) {
				String serializedItemStack = new String();

				String type = String.valueOf(is.getType().name());
				serializedItemStack += "t@" + type;

				if (is.getDurability() != 0) {
					String durability = String.valueOf(is.getDurability());
					serializedItemStack += ":d@" + durability;
				}

				if (is.getAmount() != 1) {
					String amount = String.valueOf(is.getAmount());
					serializedItemStack += ":a@" + amount;
				}

				if (is.getItemMeta().hasDisplayName()) {
					String name = is.getItemMeta().getDisplayName();
					serializedItemStack += ":n@" + escapeDisplayName(name);
				}

				Map<Enchantment, Integer> enchantments = is.getEnchantments();
				if (enchantments.size() > 0) {
					for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
						serializedItemStack += ":e@" + enchantment.getKey().getName() + "@" + enchantment.getValue();
					}
				}

				output += i + "#" + serializedItemStack + ";";
			}
		}

		ConfigurationManager.getPlayerConfig().set(player.getUniqueId().toString() + ".inventory", output);
	}

	public static void retrieveInventory(Player player) {
		String inventoryString = ConfigurationManager.getPlayerConfig().getString(player.getUniqueId().toString() + ".inventory");
		String[] serializedBlocks = inventoryString.split(";");
		Integer invSize = Integer.valueOf(serializedBlocks[0]);

		ItemStack[] items = new ItemStack[invSize];

		for (int i = 1; i < serializedBlocks.length; i++) {
			String[] block = serializedBlocks[i].split("#");
			int stackPosition = Integer.valueOf(block[0]);

			if (stackPosition >= invSize) {
				continue;
			}

			ItemStack is = null;
			Boolean createdItemStack = false;

			String[] serializedItemStack = block[1].split(":");

			for (String itemInfo : serializedItemStack) {
				String[] attr = itemInfo.split("@");

				switch (attr[0]) {
					case "t":
						is = new ItemStack(Material.getMaterial(attr[1]));
						createdItemStack = true;
						break;
					case "d":
						if (createdItemStack) {
							is.setDurability(Short.valueOf(attr[1]));
						}
						break;
					case "a":
						if (createdItemStack) {
							is.setAmount(Integer.valueOf(attr[1]));
						}
						break;
					case "e":
						if (createdItemStack) {
							is.addUnsafeEnchantment(Enchantment.getByName(attr[1]), Integer.valueOf(attr[2]));
						}
						break;
					case "n":
						if (createdItemStack) {
							ItemMeta meta = is.getItemMeta();
							meta.setDisplayName(unescapeDisplayName(attr[1]));
							is.setItemMeta(meta);
						}
						break;
				}
			}

			items[stackPosition] = is;
		}

		player.getInventory().setContents(items);
	}
}
