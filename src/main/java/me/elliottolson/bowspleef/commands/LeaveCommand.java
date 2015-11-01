package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.util.MessageManager;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class LeaveCommand extends Command {

    public LeaveCommand(){
        setName("leave");
        setAlias("l");
        setUsage("");
        setBePlayer(true);
        setPermission("bowspleef.player.game.leave");
        setDescription("Leave a game");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 1){

            Game game = GameManager.getInstance().getPlayerGame(player);

            if (game != null){

                game.removePlayer(player);

            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "You are currently not in a game.");
            }

        }

        return CommandResult.INVALID_USAGE;
    }
}
