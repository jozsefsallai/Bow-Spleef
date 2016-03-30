/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationManager {

    private static File arenas = new File("plugins/BowSpleef", "arenas.yml");
    private static FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(arenas);

    private static File players = new File("plugins/BowSpleef", "players.yml");
    private static FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(players);

    private static File statistics = new File("plugins/BowSpleef", "statistics.yml");
    private static FileConfiguration statisticsConfig = YamlConfiguration.loadConfiguration(statistics);

    private static File config = new File("plugins/BowSpleef", "config.yml");
    private static FileConfiguration configConfig = YamlConfiguration.loadConfiguration(config);

    private static File language = new File("plugins/BowSpleef", "language.yml");
    private static FileConfiguration languageConfig = YamlConfiguration.loadConfiguration(language);

    public static void loadConfig(){
        try {

            arenaConfig.load(arenas);
            playerConfig.load(players);
            statisticsConfig.load(statistics);
            configConfig.load(config);
            languageConfig.load(language);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveConfig(){
        try {

            arenaConfig.save(arenas);
            playerConfig.save(players);
            statisticsConfig.save(statistics);
            configConfig.save(config);
            languageConfig.save(language);

            if (!configConfig.contains("update.status")){
                configConfig.set("update.status", true);
                configConfig.save(config);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void loadStatConfig(){
        try {
            statisticsConfig.load(statistics);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveStatConfig(){
        try {
            statisticsConfig.save(statistics);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static FileConfiguration getArenaConfig() {
        return arenaConfig;
    }

    public static FileConfiguration getPlayerConfig() {
        return playerConfig;
    }

    public static FileConfiguration getStatisticsConfig() {
        return statisticsConfig;
    }

    public static FileConfiguration getConfigConfig() {
        return configConfig;
    }

    public static FileConfiguration getLanguageConfig() { return languageConfig; }

}
