/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef.listeners;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class GameListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (GameManager.getInstance().getPlayerGame(player) != null) {
            Game game = GameManager.getInstance().getPlayerGame(player);

            if (game.getState() == Game.GameState.INGAME || game.getState() == Game.GameState.LOBBY ||
                    game.getState() == Game.GameState.STARTING || game.getState() == Game.GameState.SPREAD) {

                if (player.getItemInHand() != null) {
                    ItemStack item = player.getItemInHand();

                    if (item.getType() == Material.BED) {
                        game.removePlayer(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();

        if (GameManager.getInstance().getPlayerGame(player) != null){
            Game game = GameManager.getInstance().getPlayerGame(player);
            game.removePlayer(player);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();

            if (GameManager.getInstance().getPlayerGame(player) != null){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();

        if (GameManager.getInstance().getPlayerGame(player) != null){
            e.setCancelled(true);
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You cannot build while playing BowSpleef.");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();

        if (GameManager.getInstance().getPlayerGame(player) != null){
            e.setCancelled(true);
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You cannot break blocks while playing BowSpleef.");
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e){
        Player player = e.getPlayer();

        if (GameManager.getInstance().getPlayerGame(player) != null){
            e.setCancelled(true);
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You cannot drop items while playing BowSpleef.");
        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent e){
        Player player = e.getPlayer();

        if (GameManager.getInstance().getPlayerGame(player) != null){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e){
        if (e.getEntity() instanceof Player){

            Player player = (Player) e.getEntity();

            if (GameManager.getInstance().getPlayerGame(player) != null){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player player = e.getPlayer();

        if (GameManager.getInstance().getPlayerGame(player) != null){
            Game game = GameManager.getInstance().getPlayerGame(player);

            if (game.getState() == Game.GameState.INGAME){
                if (player.getLocation().getBlockY() < game.getPos1().getBlockY()){
                    game.removePlayer(player);
                }
            }
        }
    }

}
