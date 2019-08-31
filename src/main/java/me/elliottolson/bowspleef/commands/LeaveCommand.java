package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.util.MessageManager;

public class LeaveCommand extends Command {

    public LeaveCommand(){
        setName("leave");
        setAlias("l");
        setUsage("");
        setBePlayer(true);
        setPermission("bowspleef.player.game.leave");
        setDescription("Leave a game.");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 1){

            Game game = GameManager.getInstance().getPlayerGame(player);

            if (game != null){

                game.removePlayer(player);
                return CommandResult.SUCCESS;

            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "You are currently not in a game.");
                return CommandResult.FAIL;
            }

        }

        return CommandResult.INVALID_USAGE;
    }
}
