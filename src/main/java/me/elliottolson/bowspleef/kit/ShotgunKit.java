package me.elliottolson.bowspleef.kit;

import me.elliottolson.bowspleef.kit.common.ArmourSet;
import me.elliottolson.bowspleef.kit.common.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

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
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "BOW"
                + ChatColor.GRAY.toString() + ChatColor.ITALIC + " - Shotgun");
        item.setItemMeta(meta);
        item.addEnchantment(Enchantment.ARROW_FIRE, 1);
        item.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        return item;
    }

    @Override
    public ItemStack getSpecialItem() {
        ItemStack item = new ItemStack(Material.IRON_BARDING);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "SHOTGUN BURST " + ChatColor.DARK_GRAY + "-" + ChatColor.GRAY.toString()
                + ChatColor.ITALIC + " (RIGHT CLICK)");
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public List<ItemStack> getItems() {
        return null;
    }

    @Override
    public void execute(Player player) {
        Random rand = new Random();

        for (int i = 0; i < 5; i++){
            Vector v = player.getLocation().getDirection().multiply(1); //Multiply the player's direction by the power
            v.add(new Vector(rand.nextDouble() - 0.5, rand.nextDouble() - 0.5, rand.nextDouble() - 0.5)); //Add the velocity by a random number

            player.getLocation().getWorld().spawnArrow(player.getLocation(), v, 2, 2);
        }
    }

    @Override
    public boolean hasCooldown() {
        return false;
    }
}
