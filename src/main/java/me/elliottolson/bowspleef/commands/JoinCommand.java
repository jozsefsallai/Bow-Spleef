package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.util.MessageManager;

public class JoinCommand extends Command {

    public JoinCommand(){
        setName("join");
        setAlias("j");
        setBePlayer(true);
        setPermission("bowspleef.player.game.join");
        setUsage("<Game>");
        setDescription("Join a game.");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 2){

            String name = getArgs().get(1);

            if (GameManager.getInstance().getGame(name) != null) {
                GameManager.getInstance().getGame(name).addPlayer(player);
                return CommandResult.SUCCESS;
            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player,
                        ConfigurationManager.getLanguageConfig().getString("language.gameDoesntExist"));
                return CommandResult.FAIL;
            }

        }

        return CommandResult.INVALID_USAGE;
    }
}
