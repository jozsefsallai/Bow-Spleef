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
public class SetCommand extends Command {

    public SetCommand(){
        setName("set");
        setAlias("s");
        setDescription("Set values for games.");
        setBePlayer(true);
        setPermission("bowspleef.admin.game.set");
        setUsage("<Game> <Option>");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 3 || getArgs().size() == 4){

            String name = getArgs().get(1);
            Game game = GameManager.getInstance().getGame(name);

            if (game == null){
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "This game doesn't exist.");
                return CommandResult.FAIL;
            }

            if (getArgs().size() == 3){

                if (getArgs().get(2).equalsIgnoreCase("spawn")){
                    game.setGameSpawn(player);
                    return CommandResult.SUCCESS;
                } else if (getArgs().get(2).equalsIgnoreCase("pos1")){
                    game.setPos1(player);
                    return CommandResult.SUCCESS;
                } else if (getArgs().get(2).equalsIgnoreCase("pos2")){
                    game.setPos2(player);
                    return CommandResult.SUCCESS;
                } else if (getArgs().get(2).equalsIgnoreCase("specspawn")){
                    game.setSpectatorSpawn(player);
                    return CommandResult.SUCCESS;
                } else if (getArgs().get(2).equalsIgnoreCase("lobby")){
                    game.setLobby(player);
                    return CommandResult.SUCCESS;
                } else if (getArgs().get(2).equalsIgnoreCase("enabled")){
                    game.enable();
                    return CommandResult.SUCCESS;
                }

            }

            else {

                if (getArgs().get(2).equalsIgnoreCase("minplayers")){
                    game.setMinimumPlayers(Integer.valueOf(getArgs().get(3)));
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "Minimum amount of players changed.");
                    return CommandResult.SUCCESS;
                } else if (getArgs().get(2).equalsIgnoreCase("maxplayers")){
                    game.setMaximumPlayers(Integer.valueOf(getArgs().get(3)));
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "Maximum amount of players changed.");
                    return CommandResult.SUCCESS;
                }

            }

        }


        return CommandResult.INVALID_USAGE;
    }
}
