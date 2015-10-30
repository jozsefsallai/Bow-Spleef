package me.elliottolson.bowspleef.game;

import org.bukkit.ChatColor;
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
public class Game {

    private String name;
    private GameState state = GameState.NOTSETUP;
    private List<Player> players = new ArrayList<Player>();
    private List<Player> spectators = new ArrayList<Player>();
    private List<Player> voters = new ArrayList<Player>();

    public Game(String name) {
        this.name = name;
    }

    public enum GameState {
        DISABLED("Disabled", 0, ChatColor.DARK_RED),
        LOADING("Loading", 1, ChatColor.GOLD),
        LOBBY("Lobby", 2, ChatColor.GREEN),
        STARTING("Starting", 3, ChatColor.DARK_GREEN),
        SPREAD("Spreading Out", 4, ChatColor.RED),
        INGAME("InGame", 5, ChatColor.DARK_RED),
        RESETTING("Resetting", 6, ChatColor.DARK_RED),
        INACTIVE("Inactive", 7, ChatColor.DARK_RED),
        NOTSETUP("Not Setup", 8, ChatColor.DARK_RED),
        ERROR("Error", 9, ChatColor.DARK_RED);

        private String name;
        private int id;
        private ChatColor color;

        GameState(String name, int id, ChatColor color){
            this.name = name;
            this.id = id;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public ChatColor getColor() {
            return color;
        }
    }

    public String getName() {
        return name;
    }

    public GameState getState() {
        return state;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Player> getSpectators() {
        return spectators;
    }

    public List<Player> getVoters() {
        return voters;
    }
}
