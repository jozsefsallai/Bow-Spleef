package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.util.MessageManager;

public class HelpCommand extends Command {

    public HelpCommand(){
        setName("help");
        setAlias("?");
        setBePlayer(true);
        setPermission("bowspleef.player.help");
        setUsage("");
        setDescription("View BowSpleef commands.");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 1){
            MessageManager.msg(MessageManager.MessageType.INFO, player, "Displaying BowSpleef commands...");
            Commands.displayCommands(player);

            return CommandResult.SUCCESS;
        }

        return CommandResult.INVALID_USAGE;
    }
}
