package me.elliottolson.bowspleef.kit.common;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.manager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Copyright Elliott Olson (c) 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class KitListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if (GameManager.getInstance().getPlayerGame(player) != null){
            Game game = GameManager.getInstance().getPlayerGame(player);

            if (game.getState() == Game.GameState.INGAME){
                if (e.getItem() == KitManager.getKit(player).getSpecialItem()){
                    KitManager.getKit(player).execute(player);
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (GameManager.getInstance().getPlayerGame(player) != null){
            Game game = GameManager.getInstance().getPlayerGame(player);

            if (game.getState() == Game.GameState.LOBBY || game.getState() == Game.GameState.STARTING){

                if (e.getInventory() == KitInventory.getKitsInventory()){

                    for (Kit kit : KitManager.getKits){
                        if (e.getCurrentItem() == kit.getIcon()){
                            KitManager.setKit(player, kit);
                        }
                    }

                    e.setCancelled(true);

                } else if (e.getInventory() == KitInventory.getConfirmInventory()){



                }

            }
        }
    }

}
