package me.elliottolson.bowspleef;

import me.elliottolson.bowspleef.commands.*;
import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.kit.*;
import me.elliottolson.bowspleef.kit.common.KitListener;
import me.elliottolson.bowspleef.kit.common.KitManager;
import me.elliottolson.bowspleef.listeners.GameListener;
import me.elliottolson.bowspleef.listeners.SignListener;
import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.manager.StatisticCollection;
import me.elliottolson.bowspleef.util.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class BowSpleef extends JavaPlugin {

    private static BowSpleef instance;
    private Metrics metrics;

    @Override
    public void onEnable() {
        getLogger().info("BowSpleef Version: 1.3.0 is enabling...'");
        instance = this;

        /////////////////////////////////////////
        //               Config                //
        /////////////////////////////////////////
        if (ConfigurationManager.getConfigConfig() == null){
            ConfigurationManager.saveConfig();
        }
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
            new StatisticCollection(this).createGraph();
            metrics.start();
        } catch (Exception e){
            e.printStackTrace();
        }

        getLogger().info("BowSpleef Version: 1.3.0 is enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BowSpleef Version: 1.3.0 is disabling...");

        for (Game game : GameManager.getInstance().getGames()){
            game.save();
        }

        GameManager.getInstance().saveGames();

        ConfigurationManager.saveConfig();

        getLogger().info("BowSpleef Version: 1.3.0 is disabled!");
    }

    public static BowSpleef getInstance() {
        return instance;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}
