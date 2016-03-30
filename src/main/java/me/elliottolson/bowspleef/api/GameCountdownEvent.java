/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef.api;

import me.elliottolson.bowspleef.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameCountdownEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Game game;

    public GameCountdownEvent(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
