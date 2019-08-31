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
