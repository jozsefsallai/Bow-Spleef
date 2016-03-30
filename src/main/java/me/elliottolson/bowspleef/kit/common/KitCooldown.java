/*
 * Copyright Elliott Olson (c) 2016. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */

package me.elliottolson.bowspleef.kit.common;

import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KitCooldown extends BukkitRunnable {

    private Player player;
    private int cooldown = 15;

    public KitCooldown(Player player){
        this.player = player;
    }

    public void run() {

        if (cooldown == 0){
            Kit kit = KitManager.getKit(player);
            MessageManager.msg(MessageManager.MessageType.INFO, player, "You are now able to use: "
                    + kit.getColor() + kit.getName());
            cancel();
        }

        if (cooldown > 0){
            cooldown--;
        }

    }
}
