package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.GameManager;

public class CreateCommand extends Command {

    public CreateCommand(){
        setName("create");
        setAlias("c");
        setDescription("Create a game.");
        setUsage("<Name>");
        setPermission("bowspleef.admin.game.create");
        setBePlayer(true);
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 2){

            String name = getArgs().get(1);
            GameManager.getInstance().createGame(name, player);

            return CommandResult.SUCCESS;

        }

        return CommandResult.INVALID_USAGE;
    }
}
