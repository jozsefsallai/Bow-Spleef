package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
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
            game.vote(player);

            return CommandResult.SUCCESS;

        }

        return CommandResult.INVALID_USAGE;
    }
}
