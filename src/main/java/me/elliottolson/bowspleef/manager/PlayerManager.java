package me.elliottolson.bowspleef.manager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class PlayerManager {

    public static void saveInventory(Player player){
        Inventory inventory = player.getInventory();
        String serialization = inventory.getSize() + ";";
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack is = inventory.getItem(i);

            if (is != null) {
                String serializedItemStack = new String();

                String isType = String.valueOf(is.getType().getId());
                serializedItemStack += "t@" + isType;

                if (is.getDurability() != 0) {
                    String isDurability = String.valueOf(is.getDurability());
                    serializedItemStack += ":d@" + isDurability;
                }

                if (is.getAmount() != 1) {
                    String isAmount = String.valueOf(is.getAmount());
                    serializedItemStack += ":a@" + isAmount;
                }

                Map<Enchantment,Integer> isEnch = is.getEnchantments();
                if (isEnch.size() > 0) {
                    for (Map.Entry<Enchantment,Integer> ench : isEnch.entrySet()) {
                        serializedItemStack += ":e@" + ench.getKey().getId() + "@" + ench.getValue();
                    }
                }

                serialization += i + "#" + serializedItemStack + ";";
            }
        }

        ConfigurationManager.getPlayerConfig().set(player.getName() + ".inventory", serialization);
    }

    public static void retrieveInventory(Player player){
        String inventoryString = ConfigurationManager.getPlayerConfig().getString(player.getName() + ".inventory");
        String[] serializedBlocks = inventoryString.split(";");
        String invInfo = serializedBlocks[0];
        Inventory deserializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(invInfo));

        for (int i = 1; i < serializedBlocks.length; i++) {
            String[] serializedBlock = serializedBlocks[i].split("#");
            int stackPosition = Integer.valueOf(serializedBlock[0]);

            if (stackPosition >= deserializedInventory.getSize()) {
                continue;
            }

            ItemStack is = null;
            Boolean createdItemStack = false;

            String[] serializedItemStack = serializedBlock[1].split(":");

            for (String itemInfo : serializedItemStack) {
                String[] itemAttribute = itemInfo.split("@");
                if (itemAttribute[0].equals("t")) {
                    is = new ItemStack(Material.getMaterial(Integer.valueOf(itemAttribute[1])));
                    createdItemStack = true;
                } else if (itemAttribute[0].equals("d") && createdItemStack) {
                    is.setDurability(Short.valueOf(itemAttribute[1]));
                } else if (itemAttribute[0].equals("a") && createdItemStack) {
                    is.setAmount(Integer.valueOf(itemAttribute[1]));
                } else if (itemAttribute[0].equals("e") && createdItemStack) {
                    is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1])), Integer.valueOf(itemAttribute[2]));
                }
            }
            deserializedInventory.setItem(stackPosition, is);
        }

        player.getInventory().setContents(deserializedInventory.getContents());
        player.updateInventory();
    }


}
