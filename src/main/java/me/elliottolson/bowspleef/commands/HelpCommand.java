/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

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
