package me.elliottolson.bowspleef.kit.common;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.manager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
                if (e.getItem() == PlayerManager.getKit(player).getSpecialItem()){
                    PlayerManager.getKit(player).execute(player);
                }
            }
        }
    }

}
