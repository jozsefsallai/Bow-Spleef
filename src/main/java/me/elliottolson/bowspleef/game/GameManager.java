package me.elliottolson.bowspleef.game;

import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.util.MessageManager;
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

    public void createGame(String name, Player player){
        if (!player.hasPermission("bowspleef.admin.game.create")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You do not have permission to create a game.");
            return;
        }

        if (getGame(name) != null){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "This game already exists.");
            return;
        }


        Game game = new Game(name);
        game.setMaximumPlayers(10);
        game.setMinimumPlayers(2);
        game.setState(Game.GameState.NOTSETUP);
        games.add(game);

        MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "This game was created.");
    }

    public void deleteGame(String name, Player player){
        if (!player.hasPermission("bowspleef.admin.game.delete")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You do not have permission to delete a game.");
            return;
        }

        if (getGame(name) == null){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "This game doesn't exist.");
            return;
        }

        games.remove(getGame(name));

        if (ConfigurationManager.getArenaConfig().contains("arenas." + name))
            ConfigurationManager.getArenaConfig().set("arenas." + name, null);

        MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "This game was deleted.");
    }

    public Game getPlayerGame(Player player){
        for (Game game : games){
            if (game.getPlayers().contains(player) || game.getSpectators().contains(player)){
                return game;
            }
        }
        return null;
    }

    public Game getGame(String name){
        for (Game game : games){
            if (game.getName().equalsIgnoreCase(name)){
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
