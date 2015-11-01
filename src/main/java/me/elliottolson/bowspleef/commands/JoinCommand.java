package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.GameManager;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
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
            GameManager.getInstance().getGame(name).addPlayer(player);
            return CommandResult.SUCCESS;

        }

        return CommandResult.INVALID_USAGE;
    }
}
