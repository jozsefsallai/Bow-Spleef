/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef.kit.common;

import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.manager.ConfigurationManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KitManager {

    public static List<Kit> getKits = new ArrayList<Kit>();

    public static Kit getKit(Player player){
        if (GameManager.getInstance().getPlayerGame(player) != null){
            ConfigurationManager.loadConfig();
            String kitName = ConfigurationManager.getPlayerConfig().getString(player.getName() + ".kit");
            return getKit(kitName);
        }

        return null;
    }

    public static void setKit(Player player, Kit kit){
        if (player == null)
            return;

        if (kit == null)
            return;

        ConfigurationManager.getPlayerConfig().set(player.getName() + ".kit", kit.getName());
        ConfigurationManager.saveConfig();
    }

    public static Kit getKit(String name){
        for (Kit kit : getKits){
            if (kit.getName().equalsIgnoreCase(name)){
                return kit;
            }
        }
        return null;
    }

}
