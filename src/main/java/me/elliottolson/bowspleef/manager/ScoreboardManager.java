package me.elliottolson.bowspleef.manager;

import me.elliottolson.bowspleef.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class ScoreboardManager {

    private Game game;

    private org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

    private Objective objective = scoreboard.registerNewObjective("dummy", "dummy");

    public ScoreboardManager(Game game){
        this.game = game;
    }

    public void applyScoreboard(Player player){

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "BOWSPLEEF");

        if (game.getState() == game.getState().LOBBY){

            addLine(ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME:", 16);
            addLine(ChatColor.WHITE + "Waiting: " + ChatColor.GREEN + game.getPlayers().size(), 15);
            addLine(ChatColor.WHITE + "Needed: " + ChatColor.RED + game.getMinimumPlayers(), 14);

            addLine("", 13);

//            addLine(ChatColor.GOLD.toString() + ChatColor.BOLD + "STATS:", 12);
//            addLine(ChatColor.WHITE + "Points: " + ChatColor.YELLOW + StatManager.getPoints(player.getUniqueId()), 15);
//            addLine(ChatColor.WHITE + "Wins: " + ChatColor.YELLOW + StatManager.getWins(player.getUniqueId()), 14);
//            addLine(ChatColor.WHITE + "Losses: " + ChatColor.YELLOW + StatManager.getLosses(player.getUniqueId()), 15);

        } else if (game.getState() == game.getState().STARTING){

            addLine(ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME:", 16);
            addLine(ChatColor.WHITE + "Waiting: " + ChatColor.GREEN + game.getPlayers().size(), 15);
            addLine(ChatColor.WHITE + "Needed: " + ChatColor.RED + game.getMinimumPlayers(), 14);

            addLine("", 13);

//            addLine(ChatColor.GOLD.toString() + ChatColor.BOLD + "STATS:", 12);
//            addLine(ChatColor.WHITE + "Points: " + ChatColor.YELLOW + StatManager.getPoints(player.getUniqueId()), 15);
//            addLine(ChatColor.WHITE + "Wins: " + ChatColor.YELLOW + StatManager.getWins(player.getUniqueId()), 14);
//            addLine(ChatColor.WHITE + "Losses: " + ChatColor.YELLOW + StatManager.getLosses(player.getUniqueId()), 15);

        } else if (game.getState() == game.getState().SPREAD){

            addLine(ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME:", 16);
            addLine(ChatColor.WHITE + "Players: " + ChatColor.GREEN + game.getPlayers().size(), 15);
            addLine(ChatColor.WHITE + "Spectators: " + ChatColor.RED + game.getSpectators().size(), 14);

            addLine("", 13);

//            addLine(ChatColor.GOLD.toString() + ChatColor.BOLD + "STATS:", 12);
//            addLine(ChatColor.WHITE + "Points: " + ChatColor.YELLOW + StatManager.getPoints(player.getUniqueId()), 15);
//            addLine(ChatColor.WHITE + "Wins: " + ChatColor.YELLOW + StatManager.getWins(player.getUniqueId()), 14);
//            addLine(ChatColor.WHITE + "Losses: " + ChatColor.YELLOW + StatManager.getLosses(player.getUniqueId()), 15);

        } else if (game.getState() == game.getState().INGAME){

            addLine(ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME:", 16);
            addLine(ChatColor.WHITE + "Players: " + ChatColor.GREEN + game.getPlayers().size(), 15);
            addLine(ChatColor.WHITE + "Spectators: " + ChatColor.RED + game.getSpectators().size(), 14);

            addLine("", 13);

//            addLine(ChatColor.GOLD.toString() + ChatColor.BOLD + "STATS:", 12);
//            addLine(ChatColor.WHITE + "Points: " + ChatColor.YELLOW + StatManager.getPoints(player.getUniqueId()), 15);
//            addLine(ChatColor.WHITE + "Wins: " + ChatColor.YELLOW + StatManager.getWins(player.getUniqueId()), 14);
//            addLine(ChatColor.WHITE + "Losses: " + ChatColor.YELLOW + StatManager.getLosses(player.getUniqueId()), 15);

        } else {
            addLine(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "ERROR", 16);
            addLine(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "DISPLAYING...", 15);

        }

        player.setScoreboard(scoreboard);

    }

    private void addLine(String text, int value){
        Score score = objective.getScore(text);
        score.setScore(value);
    }


}
