package me.elliottolson.bowspleef.kit;

import me.elliottolson.bowspleef.kit.common.ArmourSet;
import me.elliottolson.bowspleef.kit.common.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Copyright Elliott Olson (c) 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class ShotgunKit extends Kit {

    @Override
    public String getName() {
        return "Shotgun";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.RED;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.IRON_BARDING);
    }

    @Override
    public int getCost() {
        return 625;
    }

    @Override
    public ArmourSet getArmour() {
        ArmourSet set = new ArmourSet(null, null, null, null);
        return set;
    }

    @Override
    public ItemStack getBow() {
        //TODO: Compete Bow
        return null;
    }

    @Override
    public ItemStack getSpecialItem() {
        //TODO: Revamp this
        return null;
    }

    @Override
    public List<ItemStack> getItems() {
        return null;
    }

    @Override
    public void execute(Player player) {

    }

    @Override
    public boolean hasCooldown() {
        return false;
    }
}
