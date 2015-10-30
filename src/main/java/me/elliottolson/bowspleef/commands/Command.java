package me.elliottolson.bowspleef.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
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
public abstract class Command {

    protected String name;
    protected String usage;
    protected List<String> args = new ArrayList();
    protected String alias;
    protected CommandSender sender;
    protected boolean bePlayer;
    protected Player player;
    protected String permission;

    public CommandResult run(CommandSender sender, String command, String[] preArgs) {
        setSender(sender);
        setName(command);

        getArgs().clear();
        for (String string : preArgs) {
            getArgs().add(string);
        }

        if (isBePlayer()) {
            if (!(sender instanceof Player)) {
                return CommandResult.NOT_PLAYER;
            }

            setPlayer((Player)sender);
        }

        if (!sender.hasPermission(getPermission())) {
            return CommandResult.NO_PERMISSION;
        }

        return execute();
    }

    public abstract CommandResult execute();

    public boolean hasAlias() {
        return getAlias() != null;
    }

    public String getDisplayUsage() {
        return new StringBuilder().append(ChatColor.DARK_AQUA + "/bs " + ChatColor.GREEN + getName()).append(hasAlias() ?                new StringBuilder().append(ChatColor.GRAY).append("/").append(ChatColor.GREEN).append(getAlias()).toString() : "")
                .append(" ").append(ChatColor.RED).append(getUsage()).toString();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public List<String> getArgs() {
        return this.args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public CommandSender getSender() {
        return this.sender;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public boolean isBePlayer() {
        return this.bePlayer;
    }

    public void setBePlayer(boolean bePlayer) {
        this.bePlayer = bePlayer;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}
