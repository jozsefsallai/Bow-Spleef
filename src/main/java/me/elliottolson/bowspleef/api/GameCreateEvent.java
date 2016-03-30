/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef.api;

import me.elliottolson.bowspleef.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameCreateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Game game;

    public GameCreateEvent(Player player, Game game){
        this.player = player;
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
