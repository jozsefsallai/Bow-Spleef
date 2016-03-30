/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef.game;

import me.elliottolson.bowspleef.BowSpleef;
import me.elliottolson.bowspleef.api.*;
import me.elliottolson.bowspleef.kit.common.KitManager;
import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.manager.PlayerManager;
import me.elliottolson.bowspleef.manager.ScoreboardManager;
import me.elliottolson.bowspleef.manager.StatManager;
import me.elliottolson.bowspleef.util.Language;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private ScoreboardManager scoreboardManager;
    private FileConfiguration languageConfig;

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
    private Location pos1;
    private Location pos2;

    public Game(String name) {
        this.name = name;
        scoreboardManager = new ScoreboardManager(this);
        languageConfig = ConfigurationManager.getLanguageConfig();
    }

    public void addPlayer(Player player){
        if (!player.hasPermission("bowspleef.player.game.join")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.noPermission"));
            return;
        }

        if (getState() == GameState.NOTSETUP){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.notSetup"));
            return;
        }

        if (GameManager.getInstance().getPlayerGame(player) != null){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.alreadyInGame"));
            return;
        }

        if (getState() == GameState.LOBBY || getState() == GameState.STARTING){

            if (players.size() == maximumPlayers){
                MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.fullGame"));
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

            //Kits
            KitManager.setKit(player, KitManager.getKit("classic"));

            ItemStack item = new ItemStack(Material.IRON_SWORD);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "KITS" + ChatColor.DARK_GRAY
                    + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC + "(Right Click)");
            item.setItemMeta(itemMeta);
            player.getInventory().setItem(4, item);

            ItemStack leaveItem = new ItemStack(Material.BED);
            ItemMeta meta = leaveItem.getItemMeta();
            meta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "LEAVE GAME" + ChatColor.DARK_GRAY
                    + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC + "(Right Click)");
            leaveItem.setItemMeta(meta);
            player.getInventory().setItem(8, leaveItem);
            player.updateInventory();


            //Economy and Points
            if (!ConfigurationManager.getStatisticsConfig().contains(player.getUniqueId().toString())){
                StatManager.setGames(player.getUniqueId(), 0);
                StatManager.setLosses(player.getUniqueId(), 0);
                StatManager.setWins(player.getUniqueId(), 0);
                StatManager.setPoints(player.getUniqueId(), 100);
            }

            if (!ConfigurationManager.getPlayerConfig().contains(player.getUniqueId().toString())){
                ConfigurationManager.getPlayerConfig().set(player.getUniqueId().toString() + ".kits", Arrays.asList("Classic"));
                ConfigurationManager.saveConfig();
            }

            for (Player p : players){
                MessageManager.msg(MessageManager.MessageType.SUCCESS, p, player.getName() + ChatColor.AQUA +
                        " has joined the game! " + ChatColor.GRAY + "(" + ChatColor.YELLOW + players.size() +
                        ChatColor.GRAY + "/" + ChatColor.YELLOW + maximumPlayers + ChatColor.GRAY + ")");
            }

            for (Player p : spectators){
                MessageManager.msg(MessageManager.MessageType.SUCCESS, p, player.getName() + ChatColor.AQUA +
                        " has joined the game! " + ChatColor.GRAY + "(" + ChatColor.YELLOW + players.size() +
                        ChatColor.GRAY + "/" + ChatColor.YELLOW + maximumPlayers + ChatColor.GRAY + ")");
            }

            if (players.size() == getMinimumPlayers()){
                if (getState() == GameState.LOBBY){
                    msgAll(MessageManager.MessageType.INFO, languageConfig.getString("language.minimumPlayersReached"));
                    start();
                }
            }

            updateSign();
            updateScoreboard();

        } else if (getState() == GameState.INGAME){
            if (spectatorSpawn != null) {
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

                ItemStack leaveItem = new ItemStack(Material.BED);
                ItemMeta meta = leaveItem.getItemMeta();
                meta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "LEAVE GAME" + ChatColor.DARK_GRAY
                        + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC + "(Right Click)");
                leaveItem.setItemMeta(meta);
                player.getInventory().setItem(8, leaveItem);
                player.updateInventory();

                player.teleport(spectatorSpawn);

                GameSpectateEvent event = new GameSpectateEvent(player, this);
                Bukkit.getServer().getPluginManager().callEvent(event);

                spectators.add(player);

                for (Player p : players) {
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, p, player.getName() + ChatColor.AQUA +
                            " is spectating this game!");
                }

                for (Player p : spectators) {
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, p, player.getName() + ChatColor.AQUA +
                            " is spectating this game!");
                }

                updateSign();
                updateScoreboard();
            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.specSpawnNotSetup"));
            }

        } else {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.cantJoinGame"));
        }
    }

    public void removePlayer(Player player) {
        FileConfiguration playerConfig = ConfigurationManager.getPlayerConfig();

        int gamemode = playerConfig.getInt(player.getName() + ".return.gamemode");
        double health = playerConfig.getDouble(player.getName() + ".return.health");
        int foodLevel = playerConfig.getInt(player.getName() + ".return.food");

        player.setGameMode(GameMode.getByValue(gamemode));
        player.setFoodLevel(foodLevel);
        player.setHealth(health);

        player.getActivePotionEffects().clear();
        player.getInventory().clear();

        int x = playerConfig.getInt(player.getName() + ".return.x");
        int y = playerConfig.getInt(player.getName() + ".return.y");
        int z = playerConfig.getInt(player.getName() + ".return.z");
        String world = playerConfig.getString(player.getName() + ".return.world");

        player.teleport(new Location(Bukkit.getWorld(world), x, y, z));

        PlayerManager.retrieveInventory(player);

        if (voters.contains(player)) {
            voters.remove(player);
        }

        if (players.contains(player)) {
            if (players.size() != 1 && getState() == GameState.INGAME) {
                msgAll(MessageManager.MessageType.ERROR, player.getName() + ChatColor.AQUA + " has lost!");
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "You have lost this round of BowSpleef.");

                MessageManager.msg(MessageManager.MessageType.INFO, player, "Summary of game: ");
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Participation: " + ChatColor.GOLD + "+10 points");
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Place: " + ChatColor.GOLD + Language.ordinal(players.size()));

                StatManager.setLosses(player.getUniqueId(), StatManager.getLosses(player.getUniqueId()) + 1);
                StatManager.setPoints(player.getUniqueId(), StatManager.getPoints(player.getUniqueId()) + 10);
            } else if (players.size() == 1 && getState() == GameState.INGAME) {
                MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "You won this round of BowSpleef!");

                MessageManager.msg(MessageManager.MessageType.INFO, player, "Summary of game: ");
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Participation: " + ChatColor.GOLD + "+10 points");
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Win: " + ChatColor.GOLD + "+50 points");

                StatManager.setWins(player.getUniqueId(), StatManager.getWins(player.getUniqueId()) + 1);
                StatManager.setPoints(player.getUniqueId(), StatManager.getPoints(player.getUniqueId()) + 60);
            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.leftGame"));
            }
            players.remove(player);
        }

        if (spectators.contains(player)) {
            spectators.remove(player);
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.leftGame"));
        }

        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        updateScoreboard();
        updateSign();

        playerConfig.set(player.getName(), null);

        GameLeaveEvent event = new GameLeaveEvent(player, this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (players.size() == 1 && getState() == GameState.INGAME) {
            Player winner = players.get(0);

            removePlayer(winner);

            if (spectators.size() > 0){
                for (Player spec : spectators){
                    removePlayer(spec);
                }
            }

            end();
        }
    }

    public void vote(Player player){
        if (!player.hasPermission("bowspleef.player.game.vote")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.voteNoPermission"));
            return;
        }

        if (GameManager.getInstance().getPlayerGame(player) == null){
             return;
        }

        if (getState() != GameState.LOBBY){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.voteError"));
            return;
        }

        if (voters.contains(player)){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.votedAlready"));
            return;
        }

        voters.add(player);
        MessageManager.msg(MessageManager.MessageType.INFO, player, languageConfig.getString("language.voteSuccess"));

        GameVoteEvent event = new GameVoteEvent(player, this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        updateScoreboard();

        if (players.size() >= 2){

            int votesNeeded = Math.round(players.size() * (2/3));
            int remaining = votesNeeded - voters.size();

            if (remaining <= 0){

                start();

                updateScoreboard();

            }

        }

    }

    public void start(){
        state = GameState.STARTING;

        GameCountdownEvent event = new GameCountdownEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);

        new GameCountdown(this).runTaskTimer(BowSpleef.getInstance(), 0L, 20L);

        msgAll(MessageManager.MessageType.INFO, languageConfig.getString("language.gameStarting"));
    }

    public void end(){
        setState(GameState.RESETTING);
        regen();
        setState(GameState.LOBBY);
    }

    public void regen(){
        int minx = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int miny = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int minz = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        int maxx = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int maxy = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int maxz = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        for (int x = minx; x <= maxx; x++){
            for (int y = miny; y <= maxy; y++){
                for (int z = minz; z <= maxz; z++){
                    Block block = spawn.getWorld().getBlockAt(x, y, z);

                    if (block.getType().equals(Material.AIR)){
                        block.setType(Material.TNT);
                    }
                }
            }
        }
    }

    public void updateSign(){
        ConfigurationManager.loadConfig();

        if (!ConfigurationManager.getArenaConfig().contains("arenas." + name + ".sign.x")){
            return;
        }

        int x = ConfigurationManager.getArenaConfig().getInt("arenas." + name + ".sign.x");
        int y = ConfigurationManager.getArenaConfig().getInt("arenas." + name + ".sign.y");
        int z = ConfigurationManager.getArenaConfig().getInt("arenas." + name + ".sign.z");
        String world = ConfigurationManager.getArenaConfig().getString("arenas." + name + ".sign.world");

        Location bl = new Location(Bukkit.getWorld(world), x, y, z);

        if (bl != null && bl.getBlock() != null){
            Block block = bl.getBlock();

            if (block.getState() instanceof Sign){
                Sign sign = (Sign) block.getState();

                sign.setLine(0, ChatColor.AQUA + "[BowSpleef]");
                sign.setLine(1, name);
                sign.setLine(2, state.getColor() + state.getName());
                sign.setLine(3, ChatColor.DARK_GREEN.toString() + players.size() + ChatColor.DARK_GRAY + "/" +
                        ChatColor.DARK_GREEN.toString() + maximumPlayers);
                sign.update();

            }
        }

    }

    public void updateScoreboard(){
        for (Player player : players)
            scoreboardManager.applyScoreboard(player);

        for (Player player : spectators)
            scoreboardManager.applyScoreboard(player);
    }

    public void enable(){
        if (pos1 == null)
            return;

        if (pos2 == null)
            return;

        if (spawn == null)
            return;

        if (lobby == null)
            return;

        setState(GameState.LOBBY);
        regen();

        updateSign();
    }

    public void disable(){
        setState(GameState.DISABLED);

        updateSign();
    }

    public void setup() {
        //Load all arena values
        FileConfiguration arenaConfig = ConfigurationManager.getArenaConfig();

        for (GameState state : GameState.values()) {
            if (state.equals(arenaConfig.getString("arenas." + name + ".game-state"))) {
                setState(state);
            }
        }

        setMinimumPlayers(arenaConfig.getInt("arenas." + name + ".minimum-players"));
        setMaximumPlayers(arenaConfig.getInt("arenas." + name + ".maximum-players"));

        for (String name : arenaConfig.getStringList("arenas." + getName() + ".players")) {
            players.add(Bukkit.getPlayer(name));
        }

        for (String name : arenaConfig.getStringList("arenas." + getName() + ".spectators")) {
            spectators.add(Bukkit.getPlayer(name));
        }

        for (String name : arenaConfig.getStringList("arenas." + getName() + ".voters")) {
            voters.add(Bukkit.getPlayer(name));
        }

        if (arenaConfig.contains("arenas." + name + ".lobby.x")) {
            int x = arenaConfig.getInt("arenas." + name + ".lobby.x");
            int y = arenaConfig.getInt("arenas." + name + ".lobby.y");
            int z = arenaConfig.getInt("arenas." + name + ".lobby.z");
            String world = arenaConfig.getString("arenas." + name + ".lobby.world");
            setLobby(new Location(Bukkit.getWorld(world), x, y, z));
        }


        if (arenaConfig.contains("arenas." + name + ".corner.1.x")) {
            int x = arenaConfig.getInt("arenas." + name + ".corner.1.x");
            int y = arenaConfig.getInt("arenas." + name + ".corner.1.y");
            int z = arenaConfig.getInt("arenas." + name + ".corner.1.z");
            String world = arenaConfig.getString("arenas." + name + ".corner.1.world");
            setPos1(new Location(Bukkit.getWorld(world), x, y, z));
        }


        if (arenaConfig.contains("arenas." + name + ".corner.2.x")) {
            int x = arenaConfig.getInt("arenas." + name + ".corner.2.x");
            int y = arenaConfig.getInt("arenas." + name + ".corner.2.y");
            int z = arenaConfig.getInt("arenas." + name + ".corner.2.z");
            String world = arenaConfig.getString("arenas." + name + ".corner.2.world");
            setPos2(new Location(Bukkit.getWorld(world), x, y, z));
        }

        if (arenaConfig.contains("arenas." + name + ".spawn.x")) {
            int x = arenaConfig.getInt("arenas." + name + ".spawn.x");
            int y = arenaConfig.getInt("arenas." + name + ".spawn.y");
            int z = arenaConfig.getInt("arenas." + name + ".spawn.z");
            String world = arenaConfig.getString("arenas." + name + ".spawn.world");
            setSpawn(new Location(Bukkit.getWorld(world), x, y, z));
        }

        if (arenaConfig.contains("arenas." + name + ".spectator-spawn.x")) {
            int x = arenaConfig.getInt("arenas." + name + ".spectator-spawn.x");
            int y = arenaConfig.getInt("arenas." + name + ".spectator-spawn.y");
            int z = arenaConfig.getInt("arenas." + name + ".spectator-spawn.z");
            String world = arenaConfig.getString("arenas." + name + ".spectator-spawn.world");
            setSpectatorSpawn(new Location(Bukkit.getWorld(world), x, y, z));
        }

        GameManager.getInstance().getGames().add(this);

        enable();
    }

    public void save(){
        //Save all arena values
        FileConfiguration arenaConfig = ConfigurationManager.getArenaConfig();

        arenaConfig.set("arenas." + name + ".game-state", getState().getName());
        arenaConfig.set("arenas." + name + ".minimum-players", getMinimumPlayers());
        arenaConfig.set("arenas." + name + ".maximum-players", getMaximumPlayers());

        List<String> configPlayers = new ArrayList<String>();
        for (Player p : players){
            configPlayers.add(p.getName());
        }

        arenaConfig.set("arenas." + name + ".players", configPlayers);

        List<String> configSpectators = new ArrayList<String>();
        for (Player p : spectators){
            configSpectators.add(p.getName());
        }

        arenaConfig.set("arenas." + name + ".spectators", configSpectators);

        List<String> configVoters = new ArrayList<String>();
        for (Player p : voters){
            configVoters.add(p.getName());
        }

        arenaConfig.set("arenas." + name + ".voters", configVoters);

        //Lobby
        if (getLobby() != null){
            arenaConfig.set("arenas." + name + ".lobby.x", getLobby().getBlockX());
            arenaConfig.set("arenas." + name + ".lobby.y", getLobby().getBlockY());
            arenaConfig.set("arenas." + name + ".lobby.z", getLobby().getBlockZ());
            arenaConfig.set("arenas." + name + ".lobby.world", getLobby().getWorld().getName());
        }

        //Corner 1
        if (getPos1() != null){
            arenaConfig.set("arenas." + name + ".corner.1.x", getPos1().getBlockX());
            arenaConfig.set("arenas." + name + ".corner.1.y", getPos1().getBlockY());
            arenaConfig.set("arenas." + name + ".corner.1.z", getPos1().getBlockZ());
            arenaConfig.set("arenas." + name + ".corner.1.world", getPos1().getWorld().getName());
        }

        //Corner 2
        if (getPos2() != null){
            arenaConfig.set("arenas." + name + ".corner.2.x", getPos2().getBlockX());
            arenaConfig.set("arenas." + name + ".corner.2.y", getPos2().getBlockY());
            arenaConfig.set("arenas." + name + ".corner.2.z", getPos2().getBlockZ());
            arenaConfig.set("arenas." + name + ".corner.2.world", getPos2().getWorld().getName());
        }

        //Game Spawn
        if (getSpawn() != null){
            arenaConfig.set("arenas." + name + ".spawn.x", getSpawn().getBlockX());
            arenaConfig.set("arenas." + name + ".spawn.y", getSpawn().getBlockY());
            arenaConfig.set("arenas." + name + ".spawn.z", getSpawn().getBlockZ());
            arenaConfig.set("arenas." + name + ".spawn.world", getSpawn().getWorld().getName());
        }

        //Spectator Spawn
        if (getSpectatorSpawn() != null){
            arenaConfig.set("arenas." + name + ".spectator-spawn.x", getSpectatorSpawn().getBlockX());
            arenaConfig.set("arenas." + name + ".spectator-spawn.y", getSpectatorSpawn().getBlockY());
            arenaConfig.set("arenas." + name + ".spectator-spawn.z", getSpectatorSpawn().getBlockZ());
            arenaConfig.set("arenas." + name + ".spectator-spawn.world", getSpectatorSpawn().getWorld().getName());
        }
    }

    public void setPos1(Player player){
        if (!player.hasPermission("bowspleef.admin.game.set")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.setLocationNoPermission"));
            return;
        }

        pos1 = player.getLocation();
        MessageManager.msg(MessageManager.MessageType.SUCCESS, player, languageConfig.getString("language.setLocation"));
    }

    public void setPos2(Player player){
        if (!player.hasPermission("bowspleef.admin.game.set")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.setLocationNoPermission"));
            return;
        }

        pos2 = player.getLocation();
        MessageManager.msg(MessageManager.MessageType.SUCCESS, player, languageConfig.getString("language.setLocation"));
    }

    public void setLobby(Player player){
        if (!player.hasPermission("bowspleef.admin.game.set")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.setLocationNoPermission"));
            return;
        }

        lobby = player.getLocation();
        MessageManager.msg(MessageManager.MessageType.SUCCESS, player, languageConfig.getString("language.setLocation"));
    }

    public void setGameSpawn(Player player){
        if (!player.hasPermission("bowspleef.admin.game.set")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.setLocationNoPermission"));
            return;
        }

        spawn = player.getLocation();
        MessageManager.msg(MessageManager.MessageType.SUCCESS, player, languageConfig.getString("language.setLocation"));
    }

    public void setSpectatorSpawn(Player player){
        if (!player.hasPermission("bowspleef.admin.game.set")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, languageConfig.getString("language.setLocationNoPermission"));
            return;
        }

        spectatorSpawn = player.getLocation();
        MessageManager.msg(MessageManager.MessageType.SUCCESS, player, languageConfig.getString("language.setLocation"));
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
            this.time = time;
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
        updateSign();
        updateScoreboard();
    }

    /**
     * Returns a list of the current players within this instance of a game.
     * @return A list of current players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns a list of the current spectators within this instance of a game.
     * @return A list of current spectators.
     */
    public List<Player> getSpectators() {
        return spectators;
    }

    /**
     * Returns a list of the current players who have voted to start this instance of a game.
     * @return A list of the players who have voted.
     */
    public List<Player> getVoters() {
        return voters;
    }

    /**
     * Sets this Game to use the specified list to handle current players.
     * @param players The new List to be used to handle current players.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Sets this Game to use the specified list to handle current spectators.
     * @param spectators The new List to be used to handle current spectators.
     */
    public void setSpectators(List<Player> spectators) {
        this.spectators = spectators;
    }

    /**
     * Sets this Game to use the specified list to handle voters.
     * @param voters The new List to be used to handle votes.
     */
    public void setVoters(List<Player> voters) {
        this.voters = voters;
    }

    /**
     * Returns the maximum amount of players who can be in a game as it starts.
     * @return The maximum amount of players who can join a game.
     */
    public int getMaximumPlayers() {
        return maximumPlayers;
    }


    public void setMaximumPlayers(int maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
    }

    /**
     * Returns the minimum amount of players who can be in a game as it starts.
     * @return The minimum amount of players who can join a game.
     */
    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public void setMinimumPlayers(int minimumPlayers) {
        this.minimumPlayers = minimumPlayers;
    }

    /**
     * Returns the location of the game's lobby.
     * @return The location of the game's lobby.
     */
    public Location getLobby() {
        return lobby;
    }

    public void setLobby(Location lobby) {
        this.lobby = lobby;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public Location getSpectatorSpawn() {
        return spectatorSpawn;
    }

    public void setSpectatorSpawn(Location spectatorSpawn) {
        this.spectatorSpawn = spectatorSpawn;
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }
}
