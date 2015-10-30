package me.elliottolson.bowspleef.game;

import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
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

    private int maximumPlayers;
    private int minimumPlayers;

    private Location lobby;
    private List<Location> spawns;

    public Game(String name) {
        this.name = name;
    }

    public void addPlayer(Player player){
        if (!player.hasPermission("bowspleef.player.join")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You do not have permission to join a game.");
            return;
        }

        if (getState() == GameState.NOTSETUP){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "This game is not setup.");
            return;
        }

        if (GameManager.getInstance().getPlayerGame(player) != null){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You cannot be in multiple games.");
            return;
        }

        if (getState() == GameState.LOBBY || getState() == GameState.STARTING){

            if (players.size() == maximumPlayers){
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "This game is already full.");
                return;
            }

            int gm = player.getGameMode().getValue();
            double health = player.getHealth();
            int food = player.getFoodLevel();

            ConfigurationManager.getPlayerConfig().set(player.getName() + ".return.gamemode", gm);
            ConfigurationManager.getPlayerConfig().set(player.getName() + ".return.health", health);
            ConfigurationManager.getPlayerConfig().set(player.getName() + ".return.food", food);

            player.setGameMode(GameMode.ADVENTURE);
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);

            Location returnLocation = player.getLocation();
            ConfigurationManager.getPlayerConfig().set(player.getName() + ".return.x", returnLocation.getBlockX());
            ConfigurationManager.getPlayerConfig().set(player.getName() + ".return.y", returnLocation.getBlockY());
            ConfigurationManager.getPlayerConfig().set(player.getName() + ".return.z", returnLocation.getBlockZ());
            ConfigurationManager.getPlayerConfig().set(player.getName() + ".return.world", returnLocation.getWorld().getName());

            //TODO: Save Inventory

            player.getInventory().clear();
            player.updateInventory();

            player.teleport(lobby);



        } else if (getState() == GameState.INGAME){



        } else {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You cannot join the game at this time.");
        }
    }

    public void removePlayer(Player player){

    }

    public void vote(Player player){

    }

    public void start(){

    }

    public void end(){

    }

    public void reset(){

    }

    public void enable(){

    }

    public void disable(){

    }

    public void setup() {

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

        GameState(String name, int id, ChatColor color) {
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
