package me.elliottolson.bowspleef.kit;


import me.elliottolson.bowspleef.kit.common.ArmourSet;
import me.elliottolson.bowspleef.kit.common.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Copyright Elliott Olson (c) 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class ClassicKit extends Kit {

    @Override
    public String getName() {
        return "Classic";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.DARK_AQUA;
    }

    @Override
    public ItemStack getIcon() { return new ItemStack(Material.BOW); }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public ArmourSet getArmour() {
        ArmourSet set = new ArmourSet(null, null, null, null);
        return set;
    }

    @Override
    public ItemStack getBow() {

        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "BOW"
            + ChatColor.GRAY.toString() + ChatColor.ITALIC + " - Classic");
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public ItemStack getSpecialItem() {
        return null;
    }

    @Override
    public List<ItemStack> getItems() {
        return null;
    }

    @Override
    public void execute(Player player) { }

    @Override
    public boolean hasCooldown() { return false; }
}