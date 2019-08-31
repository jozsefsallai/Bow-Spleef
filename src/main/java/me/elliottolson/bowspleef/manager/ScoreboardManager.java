package me.elliottolson.bowspleef.manager;

import me.elliottolson.bowspleef.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    private Game game;

    private org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();

    public ScoreboardManager(Game game){
        this.game = game;
    }

    public void applyScoreboard(Player player){
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("dummy", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "BOWSPLEEF");

        if (game.getState() == Game.GameState.LOBBY){

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME:", 16);
            addLine(objective, ChatColor.WHITE + "Waiting: " + ChatColor.GREEN + game.getPlayers().size(), 15);
            addLine(objective, ChatColor.WHITE + "Needed: " + ChatColor.RED + game.getMinimumPlayers(), 14);
            addLine(objective, ChatColor.WHITE + "Votes: " + ChatColor.YELLOW + game.getVoters().size(), 13);

            addLine(objective, "", 12);

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "STATS:", 11);
            addLine(objective, ChatColor.WHITE + "Points: " + ChatColor.YELLOW + StatManager.getPoints(player.getUniqueId()), 10);
            addLine(objective, ChatColor.WHITE + "Wins: " + ChatColor.YELLOW + StatManager.getWins(player.getUniqueId()), 9);
            addLine(objective, ChatColor.WHITE + "Losses: " + ChatColor.YELLOW + StatManager.getLosses(player.getUniqueId()), 8);

        } else if (game.getState() == Game.GameState.STARTING){

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME:", 16);
            addLine(objective, ChatColor.WHITE + "Waiting: " + ChatColor.GREEN + game.getPlayers().size(), 15);
            addLine(objective, ChatColor.WHITE + "Needed: " + ChatColor.RED + game.getMinimumPlayers(), 14);

            addLine(objective, "", 13);

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "STATS:", 12);
            addLine(objective, ChatColor.WHITE + "Points: " + ChatColor.YELLOW + StatManager.getPoints(player.getUniqueId()), 11);
            addLine(objective, ChatColor.WHITE + "Wins: " + ChatColor.YELLOW + StatManager.getWins(player.getUniqueId()), 10);
            addLine(objective, ChatColor.WHITE + "Losses: " + ChatColor.YELLOW + StatManager.getLosses(player.getUniqueId()), 9);

        } else if (game.getState() == Game.GameState.SPREAD){

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME:", 16);
            addLine(objective, ChatColor.WHITE + "Players: " + ChatColor.GREEN + game.getPlayers().size(), 15);
            addLine(objective, ChatColor.WHITE + "Spectators: " + ChatColor.RED + game.getSpectators().size(), 14);

            addLine(objective, "", 13);

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "STATS:", 12);
            addLine(objective, ChatColor.WHITE + "Points: " + ChatColor.YELLOW + StatManager.getPoints(player.getUniqueId()), 11);
            addLine(objective, ChatColor.WHITE + "Wins: " + ChatColor.YELLOW + StatManager.getWins(player.getUniqueId()), 10);
            addLine(objective, ChatColor.WHITE + "Losses: " + ChatColor.YELLOW + StatManager.getLosses(player.getUniqueId()), 9);

        } else if (game.getState() == Game.GameState.INGAME){

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME:", 16);
            addLine(objective, ChatColor.WHITE + "Players: " + ChatColor.GREEN + game.getPlayers().size(), 15);
            addLine(objective, ChatColor.WHITE + "Spectators: " + ChatColor.RED + game.getSpectators().size(), 14);

            addLine(objective, "", 13);

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "STATS:", 12);
            addLine(objective, ChatColor.WHITE + "Points: " + ChatColor.YELLOW + StatManager.getPoints(player.getUniqueId()), 11);
            addLine(objective, ChatColor.WHITE + "Wins: " + ChatColor.YELLOW + StatManager.getWins(player.getUniqueId()), 10);
            addLine(objective, ChatColor.WHITE + "Losses: " + ChatColor.YELLOW + StatManager.getLosses(player.getUniqueId()), 9);

        } else {
            addLine(objective, ChatColor.DARK_RED.toString() + ChatColor.BOLD + "ERROR", 16);
            addLine(objective, ChatColor.DARK_RED.toString() + ChatColor.BOLD + "DISPLAYING...", 15);

        }

        player.setScoreboard(scoreboard);

    }

    private void addLine(Objective objective, String text, int value){
        Score score = objective.getScore(text);
        score.setScore(value);
    }


}
