package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.ChatColor;

public class ListCommand extends Command {

    public ListCommand() {
        setName("list");
        setUsage("");
        setBePlayer(true);
        setPermission("bowspleef.player.game.list");
        setDescription("List all available games.");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 1) {

            String result = " ";

            for (Game game : GameManager.getInstance().getGames()) {
                result += ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + game.getName() + ChatColor.DARK_GRAY + "(" +
                        game.getState().getColor().toString() + ChatColor.ITALIC + game.getState().getName() +
                        ChatColor.DARK_GRAY + ")" + ChatColor.GRAY + ", ";
            }

            MessageManager.msg(MessageManager.MessageType.INFO, player, "All available games...");

            if (result.length() > 2) {
                result = result.substring(0, result.length() - 2);
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, result);
            } else {
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, " Zero games found.");
            }


            return CommandResult.SUCCESS;
        }

        return CommandResult.INVALID_USAGE;
    }
}
