package me.elliottolson.bowspleef.game;

import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static GameManager instance;
    private List<Game> games = new ArrayList<Game>();

    public void loadGames(){
        games.clear();

        for (String name : ConfigurationManager.getArenaConfig().getStringList("list.games")){
            Game game = new Game(name);
            game.setup();
        }
    }

    public void saveGames(){
        List<String> gameNames = new ArrayList<String>();
        for (Game game : games){
            gameNames.add(game.getName());
        }
        ConfigurationManager.getArenaConfig().set("list.games", gameNames);
    }

    public void createGame(String name, Player player){
        FileConfiguration file = ConfigurationManager.getLanguageConfig();

        if (!player.hasPermission("bowspleef.admin.game.create")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, file.getString("language.gameCreateNoPermission"));
            return;
        }

        if (getGame(name) != null){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, file.getString("language.gameExists"));
            return;
        }


        Game game = new Game(name);
        game.setMaximumPlayers(10);
        game.setMinimumPlayers(2);
        game.setState(Game.GameState.NOTSETUP);
        games.add(game);

        MessageManager.msg(MessageManager.MessageType.SUCCESS, player, file.getString("language.gameCreated"));
    }

    public void deleteGame(String name, Player player){
        FileConfiguration file = ConfigurationManager.getLanguageConfig();

        if (!player.hasPermission("bowspleef.admin.game.delete")){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, file.getString("language.gameDeleteNoPermission"));
            return;
        }

        if (getGame(name) == null){
            MessageManager.msg(MessageManager.MessageType.ERROR, player, file.getString("language.gameDoesntExist"));
            return;
        }

        games.remove(getGame(name));

        if (ConfigurationManager.getArenaConfig().contains("arenas." + name))
            ConfigurationManager.getArenaConfig().set("arenas." + name, null);

        MessageManager.msg(MessageManager.MessageType.SUCCESS, player, file.getString("language.gameDeleted"));
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

    public List<Game> getGames() {
        return games;
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
