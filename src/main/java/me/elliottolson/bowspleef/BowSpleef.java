/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef;

import me.elliottolson.bowspleef.commands.*;
import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.kit.BoltKit;
import me.elliottolson.bowspleef.kit.ClassicKit;
import me.elliottolson.bowspleef.kit.GhostKit;
import me.elliottolson.bowspleef.kit.JumperKit;
import me.elliottolson.bowspleef.kit.common.KitListener;
import me.elliottolson.bowspleef.kit.common.KitManager;
import me.elliottolson.bowspleef.listeners.GameListener;
import me.elliottolson.bowspleef.listeners.SignListener;
import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.util.Language;
import me.elliottolson.bowspleef.util.Metrics;
import me.elliottolson.bowspleef.util.Updater;
import org.bukkit.plugin.java.JavaPlugin;

public class BowSpleef extends JavaPlugin {

    private static BowSpleef instance;
    private Metrics metrics;
    private Updater updater;

    private final String PLUGIN_VERSION = "1.4.0";

    @Override
    public void onEnable() {
        getLogger().info("BowSpleef Version: " + PLUGIN_VERSION + " is enabling...'");
        instance = this;

        /////////////////////////////////////////
        //               Config                //
        /////////////////////////////////////////
        Language.setupLanguage();
        ConfigurationManager.loadConfig();

        /////////////////////////////////////////
        //               Commands              //
        /////////////////////////////////////////
        getCommand("bs").setExecutor(new CommandProcessor());
        Commands.getCommandList().add(new HelpCommand());
        Commands.getCommandList().add(new CreateCommand());
        Commands.getCommandList().add(new DeleteCommand());
        Commands.getCommandList().add(new JoinCommand());
        Commands.getCommandList().add(new LeaveCommand());
        Commands.getCommandList().add(new VoteCommand());
        Commands.getCommandList().add(new SetCommand());
        Commands.getCommandList().add(new RegenCommand());
        Commands.getCommandList().add(new ListCommand());

        /////////////////////////////////////////
        //              Listeners              //
        /////////////////////////////////////////
        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getServer().getPluginManager().registerEvents(new KitListener(), this);
        getServer().getPluginManager().registerEvents(new SignListener(), this);
        getServer().getPluginManager().registerEvents(new KitListener(), this);

        /////////////////////////////////////////
        //               Games                 //
        /////////////////////////////////////////
        GameManager.getInstance().loadGames();

        /////////////////////////////////////////
        //                Kits                 //
        /////////////////////////////////////////
        KitManager.getKits.add(new ClassicKit());
        KitManager.getKits.add(new JumperKit());
        KitManager.getKits.add(new BoltKit());
        KitManager.getKits.add(new GhostKit());

        try {
            metrics = new Metrics(this);
            metrics.start();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (ConfigurationManager.getConfigConfig().getBoolean("update.status")){
            updater = new Updater(this, 56977, getFile(), Updater.UpdateType.NO_VERSION_CHECK, false);
        } else {
            updater = new Updater(this, 56977, getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
            if (updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE){
                getLogger().info("[BowSpleef] New update available! - " + updater.getLatestName());
            }
        }

        getLogger().info("BowSpleef Version: " + PLUGIN_VERSION + " is enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BowSpleef Version: " + PLUGIN_VERSION + " is disabling...");

        for (Game game : GameManager.getInstance().getGames()){
            game.save();
        }

        GameManager.getInstance().saveGames();

        ConfigurationManager.saveConfig();

        getLogger().info("BowSpleef Version: " + PLUGIN_VERSION + " is disabled!");
    }

    public static BowSpleef getInstance() {
        return instance;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}
