package me.elliottolson.bowspleef.kit;

import me.elliottolson.bowspleef.kit.common.ArmourSet;
import me.elliottolson.bowspleef.kit.common.Kit;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class JumperKit extends Kit {

    @Override
    public String getName() {
        return "Jumper";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.DIAMOND_BOOTS);
    }

    @Override
    public int getCost() {
        return 625;
    }

    @Override
    public ArmourSet getArmour() {
        return null;
    }

    @Override
    public ItemStack getBow() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "BOW"
                + ChatColor.GRAY.toString() + ChatColor.ITALIC + " - Jumper");
        item.setItemMeta(meta);
        item.addEnchantment(Enchantment.ARROW_FIRE, 1);
        item.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        return item;
    }

    @Override
    public ItemStack getSpecialItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "JUMP BOOST " + ChatColor.DARK_GRAY + "-" + ChatColor.GRAY.toString()
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
        MessageManager.msg(MessageManager.MessageType.INFO, player, "You will have a jump boost for the next 5 seconds...");
        player.getInventory().remove(Material.DIAMOND_BOOTS);

        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 2));
    }

    @Override
    public boolean hasCooldown() {
        return false;
    }
}
