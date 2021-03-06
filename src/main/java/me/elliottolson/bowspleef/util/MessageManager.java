package me.elliottolson.bowspleef.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageManager {

    public static void msg(MessageType messageType, Player player, String message){
        String prefix = messageType.getPrefix();
        player.sendMessage(prefix + ChatColor.GRAY + message);
    }

    public enum MessageType {

        INFO(ChatColor.GOLD.toString() + ChatColor.BOLD + ">> "),
        ERROR(ChatColor.RED.toString() + ChatColor.BOLD + ">> "),
        SUCCESS(ChatColor.GREEN.toString() + ChatColor.BOLD + ">> "),
        SUB_INFO(ChatColor.GOLD.toString() + ChatColor.BOLD + "> ");

        private String prefix;

        MessageType(String prefix){
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

}