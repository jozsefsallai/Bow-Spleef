package me.elliottolson.bowspleef.manager;

import me.elliottolson.bowspleef.BowSpleef;
import org.mcstats.Metrics;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class StatisticCollection {

    private BowSpleef plugin;

    public StatisticCollection(BowSpleef plugin){
        this.plugin = plugin;
    }

    public void createGraph(){

        Metrics.Graph globalStats = plugin.getMetrics().createGraph("Global Statistics");

        globalStats.addPlotter(new Metrics.Plotter("Total games played") {
            @Override
            public int getValue() {
                return ConfigurationManager.getStatisticsConfig().getInt("total.games-played");
            }
        });

        globalStats.addPlotter(new Metrics.Plotter("Total arrows shot") {
            @Override
            public int getValue() {
                return ConfigurationManager.getStatisticsConfig().getInt("total.arrows-shot");
            }
        });

        globalStats.addPlotter(new Metrics.Plotter("Total players joined") {
            @Override
            public int getValue() {
                return ConfigurationManager.getStatisticsConfig().getInt("total.players-joined");
            }
        });

    }

}
