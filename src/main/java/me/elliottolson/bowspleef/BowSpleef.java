package me.elliottolson.bowspleef;

import me.elliottolson.bowspleef.commands.CommandProcessor;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.listeners.GameListener;
import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.manager.StatisticCollection;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

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
        ConfigurationManager.loadConfig();

        /////////////////////////////////////////
        //               Commands              //
        /////////////////////////////////////////
        getCommand("bs").setExecutor(new CommandProcessor());


        /////////////////////////////////////////
        //              Listeners              //
        /////////////////////////////////////////
        getServer().getPluginManager().registerEvents(new GameListener(), this);

        try {
            metrics = new Metrics(this);
            new StatisticCollection(this).createGraph();
            metrics.start();
        } catch (Exception e){
            e.printStackTrace();
        }

        getLogger().info("BowSpleef Version: 1.3.0 is enabled!'");
    }

    @Override
    public void onDisable() {

    }

    public static BowSpleef getInstance() {
        return instance;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}
