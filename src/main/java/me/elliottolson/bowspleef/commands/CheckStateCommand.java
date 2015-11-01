package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.util.MessageManager;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class CheckStateCommand extends Command {

    public CheckStateCommand(){
        setName("CheckState");
        setDescription("Check the state of a game.");
        setAlias("cs");
        setUsage("<Game>");
        setPermission("bowspleef.admin.game.check");
        setBePlayer(true);
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 2){
            String name = getArgs().get(1);

            if (GameManager.getInstance().getGame(name) != null){
                MessageManager.msg(MessageManager.MessageType.INFO, player, "Game state is: " + GameManager.getInstance().getGame(name).getState().getName());
            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "Game doesn't exist.");
            }

            return CommandResult.SUCCESS;
        }

        return CommandResult.INVALID_USAGE;
    }
}
