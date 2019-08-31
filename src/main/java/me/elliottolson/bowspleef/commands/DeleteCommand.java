package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.GameManager;

public class DeleteCommand extends Command {

    public DeleteCommand(){
        setName("delete");
        setAlias("d");
        setUsage("<Game>");
        setBePlayer(true);
        setDescription("Delete a game.");
        setPermission("bowspleef.admin.game.delete");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 2){

            String name = getArgs().get(1);
            GameManager.getInstance().deleteGame(name, player);

            return CommandResult.SUCCESS;
        }

        return CommandResult.INVALID_USAGE;
    }
}
