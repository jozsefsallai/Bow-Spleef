package me.elliottolson.bowspleef.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
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