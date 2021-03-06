package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.util.MessageManager;

public class VoteCommand extends Command {

    public VoteCommand(){
        setName("vote");
        setAlias("v");
        setDescription("Vote to start game.");
        setPermission("bowspleef.player.game.vote");
        setBePlayer(true);
        setUsage("");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 1){

            Game game = GameManager.getInstance().getPlayerGame(player);

            if (game != null){
                game.vote(player);
            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "You must be in a game to vote.");
                return CommandResult.FAIL;
            }

            return CommandResult.SUCCESS;

        }

        return CommandResult.INVALID_USAGE;
    }
}
