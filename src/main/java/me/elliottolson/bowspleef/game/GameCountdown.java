package me.elliottolson.bowspleef.game;

import me.elliottolson.bowspleef.kit.common.KitManager;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class GameCountdown extends BukkitRunnable {

    private Game game;
    private int time;

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
                    player.updateInventory();
                }
            }

            if (time == 5 ||( time <= 3 && time >= 1)){
                game.msgAll(MessageManager.MessageType.SUB_INFO, "The game will start in " + ChatColor.DARK_AQUA +
                        time + ChatColor.GRAY + " seconds...");
            }

            if (time == 0){
                for (Player player : game.getPlayers()){
                    KitManager.getKit(player).give(player);
                    player.updateInventory();
                    MessageManager.msg(MessageManager.MessageType.INFO, player, "The game has started!");
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
