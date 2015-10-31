package me.elliottolson.bowspleef.kit.common;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright Elliott Olson (c) 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class ArmourSet {

    private final ItemStack helmet;
    private final ItemStack chestplate;
    private final ItemStack leggings;
    private final ItemStack boots;

    public ArmourSet(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots){
        if (helmet == null)
            this.helmet = new ItemStack(Material.AIR);
        else
            this.helmet = helmet;

        if (chestplate == null)
            this.chestplate = new ItemStack(Material.AIR);
        else
            this.chestplate = chestplate;

        if (leggings == null)
            this.leggings = new ItemStack(Material.AIR);
        else
            this.leggings = leggings;

        if (boots == null)
            this.boots = new ItemStack(Material.AIR);
        else
            this.boots = boots;

    }

    public ItemStack getHelmet() {
        return helmet;
    }

    public ItemStack getChestplate() {
        return chestplate;
    }

    public ItemStack getLeggings() {
        return leggings;
    }

    public ItemStack getBoots() {
        return boots;
    }
}
