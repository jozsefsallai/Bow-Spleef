/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef.kit;

import me.elliottolson.bowspleef.BowSpleef;
import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.kit.common.Kit;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GhostKit extends Kit {

    @Override
    public String getName() {
        return "Ghost";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GRAY;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.WEB);
    }

    @Override
    public int getCost() {
        return 625;
    }

    @Override
    public ItemStack getBow() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "BOW"
                + ChatColor.GRAY.toString() + ChatColor.ITALIC + " - Ghost");
        item.setItemMeta(meta);
        item.addEnchantment(Enchantment.ARROW_FIRE, 1);
        item.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        return item;
    }

    @Override
    public ItemStack getSpecialItem() {
        ItemStack item = new ItemStack(351, 1, (short) 8);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "GO INVISIBLE " + ChatColor.DARK_GRAY + "-" + ChatColor.GRAY.toString()
                + ChatColor.ITALIC + " (RIGHT CLICK)");
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public List<ItemStack> getItems() {
        return null;
    }

    @Override
    public void execute(final Player player) {
        MessageManager.msg(MessageManager.MessageType.INFO, player, "You will be invisible for the next 5 seconds...");
        player.getInventory().remove(getSpecialItem());

        final Game game = GameManager.getInstance().getPlayerGame(player);
        for (Player p : game.getPlayers()){
            p.hidePlayer(player);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(BowSpleef.getInstance(), new Runnable() {
            public void run() {
                for (Player p : game.getPlayers()){
                    p.showPlayer(player);
                }
                MessageManager.msg(MessageManager.MessageType.INFO, player, "You are once again visible!");
            }
        }, 20 * 5);

    }

    @Override
    public boolean hasCooldown() {
        return false;
    }
}
