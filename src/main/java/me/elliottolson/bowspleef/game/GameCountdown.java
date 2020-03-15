/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef.game;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.util.MessageManager;

public class GameCountdown extends BukkitRunnable {

    private Game game;
    private int time;

    private void giveBow(Player player) {
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta meta = bow.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "BOW" + ChatColor.GRAY.toString()
                + ChatColor.ITALIC + " - Classic");
        bow.setItemMeta(meta);
        bow.addEnchantment(Enchantment.ARROW_FIRE, 1);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);

        player.getInventory().setItem(0, bow);

        ItemStack arrow = new ItemStack(Material.ARROW);
        arrow.setAmount(64);
        ItemMeta arrowMeta = arrow.getItemMeta();
        arrowMeta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "ARROW" + ChatColor.GRAY.toString()
                + ChatColor.ITALIC + " - Classic");
        arrow.setItemMeta(arrowMeta);

        player.getInventory().setItem(8, arrow);
    }

    public GameCountdown(Game game){
        this.game = game;
        time = game.getState().getTime();
    }

    public void run() {

        int id = game.getState().getId();

        if (id == game.getState().STARTING.getId()){

            if (time == 15){
                time = 15;
            }

            if (time == 15 || time == 10 || time == 5 || (time <= 3 && time > 0)){
                game.msgAll(MessageManager.MessageType.SUB_INFO, "You will be teleported in " + ChatColor.DARK_AQUA +
                        time + ChatColor.GRAY + " seconds...");
            }

            if (time == 0){
                for (Player player : game.getPlayers()){
                    player.teleport(game.getSpawn());
                }
                game.setState(Game.GameState.SPREAD);
                time = 11;
            }

            if (time > 0){
                time--;
            }

        }

        else if (id == game.getState().SPREAD.getId()){

            if (time == 10){
                game.msgAll(MessageManager.MessageType.INFO, "The game will start momentarily...");
                game.msgAll(MessageManager.MessageType.SUB_INFO, "You will receive your kits in " + ChatColor.DARK_AQUA +
                        "10" + ChatColor.GRAY + " seconds...");

                for (Player player : game.getPlayers()){
                    player.getInventory().clear();
                }
            }

            if (time == 5 ||( time <= 3 && time >= 1)){
                game.msgAll(MessageManager.MessageType.SUB_INFO, "The game will start in " + ChatColor.DARK_AQUA +
                        time + ChatColor.GRAY + " seconds...");
            }

            if (time == 0){
                for (Player player : game.getPlayers()){
                    giveBow(player);
                    MessageManager.msg(MessageManager.MessageType.INFO, player, ConfigurationManager.getLanguageConfig()
                            .getString("language.gameStarted"));
                }
                game.setState(Game.GameState.INGAME);
            }

            if (time > 0){
                time--;
            }

        }

        else if (id == game.getState().INGAME.getId()) {
            cancel();
        }

    }
}
