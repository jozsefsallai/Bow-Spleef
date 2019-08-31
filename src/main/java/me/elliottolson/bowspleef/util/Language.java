package me.elliottolson.bowspleef.util;

import me.elliottolson.bowspleef.manager.ConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;

public class Language {

    public static String ordinal(int i) {
        String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + sufixes[i % 10];

        }
    }

    public static void setupLanguage() {
        ConfigurationManager.saveConfig();
        FileConfiguration file = ConfigurationManager.getLanguageConfig();

        file.addDefault("language.fullGame", "This game is already full.");
        file.addDefault("language.noPermission", "You do not have permission to join a game.");
        file.addDefault("language.notSetup", "This game is not setup.");
        file.addDefault("language.alreadyInGame", "You cannot be in multiple games.");
        file.addDefault("language.cantJoinGame", "You cannot join the game at this time.");

        file.addDefault("language.minimumPlayersReached", "Minimum player count reached!");

        file.addDefault("language.specSpawnNotSetup", "The spectating spawn hasn't been set up.");

        file.addDefault("language.leftGame", "You have left the arena.");

        file.addDefault("language.voteNoPermission", "You do not have permission to vote.");
        file.addDefault("language.voteError", "You are unable to vote at this time.");
        file.addDefault("language.votedAlready", "You have already voted once.");
        file.addDefault("language.voteSuccess", "You have voted to start the game.");

        file.addDefault("language.gameStarting", "This game will begin soon...");
        file.addDefault("language.gameStarted", "The game has started!");

        file.addDefault("language.setLocationNoPermission", "You do not have permission to set this location.");
        file.addDefault("language.setLocation", "Location set.");

        file.addDefault("language.gameExists", "This game already exists.");
        file.addDefault("language.gameDoesntExist", "This game doesn't exist.");
        file.addDefault("language.gameCreateNoPermission", "You do not have permission to create a game.");
        file.addDefault("language.gameDeleteNoPermission", "You do not have permission to delete a game.");
        file.addDefault("language.gameCreated", "This game was created.");
        file.addDefault("language.gameDeleted", "This game was deleted.");

        file.options().copyDefaults(true);
        ConfigurationManager.saveConfig();
    }

}
