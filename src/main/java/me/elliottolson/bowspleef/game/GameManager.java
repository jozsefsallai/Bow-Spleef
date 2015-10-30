package me.elliottolson.bowspleef.game;

import me.elliottolson.bowspleef.manager.ConfigurationManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class GameManager {

    private static GameManager instance;
    private List<Game> games = new ArrayList<Game>();

    //TODO: Load Games, Load Kits, and Player checks

    public void setup(){

    }

    public void loadGames(){
        games.clear();

        for (String name : ConfigurationManager.getArenaConfig().getStringList("list.arenas")){
            games.add(new Game(name));
        }
    }

    public Game getPlayerGame(Player player){
        for (Game game : games){
            if (game.getPlayers().contains(player) || game.getSpectators().contains(player)){
                return game;
            }
        }
        return null;
    }


    /**
     * Returns a new instance of {@link GameManager}, creating one if null.
     *
     * @return {@link GameManager}
     */
    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

}
