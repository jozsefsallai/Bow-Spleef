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
