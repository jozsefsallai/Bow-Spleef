package me.elliottolson.bowspleef.manager;

import java.util.UUID;

public class StatManager {

    public static int getPoints(UUID uuid){
        ConfigurationManager.loadStatConfig();
        return ConfigurationManager.getStatisticsConfig().getInt(uuid.toString() + ".points");
    }

    public static void setPoints(UUID uuid, int points){
        ConfigurationManager.getStatisticsConfig().set(uuid.toString() + ".points", points);
        ConfigurationManager.saveStatConfig();
    }

    public static int getWins(UUID uuid){
        ConfigurationManager.loadStatConfig();
        return ConfigurationManager.getStatisticsConfig().getInt(uuid.toString() + ".wins");
    }

    public static void setWins(UUID uuid, int wins){
        ConfigurationManager.getStatisticsConfig().set(uuid.toString() + ".wins", wins);
        ConfigurationManager.saveStatConfig();
    }

    public static int getLosses(UUID uuid){
        ConfigurationManager.loadStatConfig();
        return ConfigurationManager.getStatisticsConfig().getInt(uuid.toString() + ".losses");
    }

    public static void setLosses(UUID uuid, int losses){
        ConfigurationManager.getStatisticsConfig().set(uuid.toString() + ".losses", losses);
        ConfigurationManager.saveStatConfig();
    }

    public static int getGames(UUID uuid){
        ConfigurationManager.loadStatConfig();
        return ConfigurationManager.getStatisticsConfig().getInt(uuid.toString() + ".games");
    }

    public static void setGames(UUID uuid, int games){

        ConfigurationManager.getStatisticsConfig().set(uuid.toString() + ".games", games);
        ConfigurationManager.saveStatConfig();
    }

}
