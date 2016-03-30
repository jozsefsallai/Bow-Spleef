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
import org.bukkit.plugin.Plugin;

public class GameEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Plugin plugin;
    private Game game;

    public GameEndEvent(Game game, Plugin plugin){
        this.plugin = plugin;
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
