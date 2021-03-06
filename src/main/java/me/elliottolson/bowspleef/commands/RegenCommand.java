package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.util.MessageManager;

public class RegenCommand extends Command {

    public RegenCommand(){
        setName("reset");
        setAlias("r");
        setDescription("Reset a game's arena.");
        setUsage("<Game>");
        setBePlayer(true);
        setPermission("bowspleef.admin.game.reset");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 2){

            String gameName = getArgs().get(1);
            Game game = GameManager.getInstance().getGame(gameName);

            game.regen();
            MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "Reset the game.");

            return CommandResult.SUCCESS;
        }

        return CommandResult.INVALID_USAGE;
    }
}
