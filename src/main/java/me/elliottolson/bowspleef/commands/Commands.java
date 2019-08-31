package me.elliottolson.bowspleef.commands;

import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
