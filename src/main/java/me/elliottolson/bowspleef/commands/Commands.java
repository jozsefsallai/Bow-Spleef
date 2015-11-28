package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class Commands {

    private static List<Command> commandList = new ArrayList();

    public static List<Command> getCommandList() {
        return commandList;
    }

    public static void setCommandList(List<Command> commandList) {
        commandList = commandList;
    }

    public static void displayCommands(Player player){
        for (Command command : commandList){
            if (player.hasPermission(command.getPermission())){
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, command.getDisplayUsage());
            }
        }
    }

}
