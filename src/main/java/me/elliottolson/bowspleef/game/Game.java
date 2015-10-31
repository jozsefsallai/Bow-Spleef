package me.elliottolson.bowspleef.game;

import me.elliottolson.bowspleef.BowSpleef;
import me.elliottolson.bowspleef.api.GameCountdownEvent;
import me.elliottolson.bowspleef.api.GameJoinEvent;
import me.elliottolson.bowspleef.api.GameSpectateEvent;
import me.elliottolson.bowspleef.api.GameVoteEvent;
import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.manager.PlayerManager;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.Bukkit;
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
    private Location spawn;
    private Location spectatorSpawn;

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

            PlayerManager.saveInventory(player);

            player.getInventory().clear();
            player.updateInventory();

            player.teleport(lobby);

            GameJoinEvent event = new GameJoinEvent(player, this);
            Bukkit.getServer().getPluginManager().callEvent(event);

            players.add(player);

            //TODO: Stats, Achievements, Kits, Economy

            for (Player p : players){
                MessageManager.msg(MessageManager.MessageType.SUCCESS, p, player.getName() + ChatColor.AQUA +
                        " has joined the game! " + ChatColor.GRAY + "(" + ChatColor.YELLOW + players.size() +
                        ChatColor.GRAY + "/" + ChatColor.YELLOW + maximumPlayers + ChatColor.GRAY + ")");
                //TODO: Update scoreboards
            }

            for (Player p : spectators){
                MessageManager.msg(MessageManager.MessageType.SUCCESS, p, player.getName() + ChatColor.AQUA +
                        " has joined the game! " + ChatColor.GRAY + "(" + ChatColor.YELLOW + players.size() +
                        ChatColor.GRAY + "/" + ChatColor.YELLOW + maximumPlayers + ChatColor.GRAY + ")");
                //TODO: Update scoreboards
            }

            //TODO: Update signs

        } else if (getState() == GameState.INGAME){

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

            PlayerManager.saveInventory(player);

            player.getInventory().clear();
            player.updateInventory();

            player.teleport(spectatorSpawn);

            GameSpectateEvent event = new GameSpectateEvent(player, this);
            Bukkit.getServer().getPluginManager().callEvent(event);

            spectators.add(player);

            //TODO: Stats, Achievements, Kits, Economy

            for (Player p : players){
                MessageManager.msg(MessageManager.MessageType.SUCCESS, p, player.getName() + ChatColor.AQUA +
                        " is spectating this game!");
                //TODO: Update scoreboards
            }

            for (Player p : spectators){
                MessageManager.msg(MessageManager.MessageType.SUCCESS, p, player.getName() + ChatColor.AQUA +
                        " is spectating this game!");
                //TODO: Update scoreboards
            }

            //TODO: Update signs

        } else {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You cannot join the game at this time.");
        }
    }

    public void removePlayer(Player player){

    }

    public void vote(Player player){
        if (!player.hasPermission("bowspleef.player.vote")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You do not have permission to vote.");
            return;
        }

        if (GameManager.getInstance().getPlayerGame(player) == null){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You must be in a game to vote.");
            return;
        }

        if (getState() != GameState.LOBBY){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You are unable to vote at this time.");
            return;
        }

        if (voters.contains(player)){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You have already voted once.");
            return;
        }

        voters.add(player);
        MessageManager.msg(MessageManager.MessageType.INFO, player, "You have voted to start the game.");

        {
            GameVoteEvent event = new GameVoteEvent(player, this);
            Bukkit.getServer().getPluginManager().callEvent(event);
        }

        if (players.size() >= 2){

            int votesNeeded = Math.round(players.size() * (2/3));
            int remaining = votesNeeded - voters.size();

            if (remaining == 0){

                state = GameState.STARTING;

                {
                    GameCountdownEvent event = new GameCountdownEvent(this);
                    Bukkit.getServer().getPluginManager().callEvent(event);
                }

                new GameCountdown(this).runTaskTimer(BowSpleef.getInstance(), 0L, 20L);

                msgAll(MessageManager.MessageType.INFO, "This game will begin soon...");
            }

        }

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

    public void msgAll(MessageManager.MessageType type, String message){
        for (Player player : getPlayers()){
            player.sendMessage(type.getPrefix() + ChatColor.GRAY + message);
        }
        for (Player player : getSpectators()){
            player.sendMessage(type.getPrefix() + ChatColor.GRAY + message);
        }
    }

    public enum GameState {
        DISABLED("Disabled", 0, ChatColor.DARK_RED, 0),
        LOADING("Loading", 1, ChatColor.GOLD, 0),
        LOBBY("Lobby", 2, ChatColor.GREEN, 0),
        STARTING("Starting", 3, ChatColor.DARK_GREEN, 15),
        SPREAD("Spreading Out", 4, ChatColor.RED, 5),
        INGAME("InGame", 5, ChatColor.DARK_RED, 0),
        RESETTING("Resetting", 6, ChatColor.DARK_RED, 0),
        INACTIVE("Inactive", 7, ChatColor.DARK_RED, 0),
        NOTSETUP("Not Setup", 8, ChatColor.DARK_RED, 0),
        ERROR("Error", 9, ChatColor.DARK_RED, 0);

        private String name;
        private int id;
        private ChatColor color;
        private int time;

        GameState(String name, int id, ChatColor color, int time) {
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

        public int getTime() {
            return time;
        }
    }

    public String getName() {
        return name;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
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
