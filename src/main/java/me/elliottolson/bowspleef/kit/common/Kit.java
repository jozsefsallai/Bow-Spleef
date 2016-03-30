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
import me.elliottolson.bowspleef.manager.StatManager;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Kit {

    public abstract String getName();
    public abstract ChatColor getColor();
    public abstract ItemStack getIcon();

    public abstract int getCost();

    public abstract ItemStack getBow();
    public abstract ItemStack getSpecialItem();

    public abstract List<ItemStack> getItems();

    //Methods
    public abstract void execute(Player player);
    public abstract boolean hasCooldown();

    //Info
    public void give(Player player){
        if (isBought(player)){
            if (getBow() != null) player.getInventory().setItem(0, getBow());
            if (getSpecialItem() != null) player.getInventory().setItem(2, getSpecialItem());

            player.getInventory().setItem(8, new ItemStack(Material.ARROW));

            if (getItems() != null){
                if (getItems().size() > 0){
                    for (int i = 0; i < getItems().size(); i++){
                        int slot = 9;
                        player.getInventory().setItem(slot, getItems().get(i));
                        slot++;
                    }
                }
            }

            MessageManager.msg(MessageManager.MessageType.INFO, player, "You have received the kit: " + getColor() + getName());
        }
    }

    public void buy(Player player){
        if (!isBought(player)){
            if (StatManager.getPoints(player.getUniqueId()) >= getCost()){

                List<String> kits = ConfigurationManager.getPlayerConfig().getStringList(player.getUniqueId() + ".kits");
                kits.add(getName());
                ConfigurationManager.getPlayerConfig().set(player.getUniqueId() + ".kits", kits);

                int remainingFunds = StatManager.getPoints(player.getUniqueId()) - getCost();
                StatManager.setPoints(player.getUniqueId(), remainingFunds);

                MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "You have successfully bought: " +
                        ChatColor.DARK_AQUA + getName() + ChatColor.GRAY + " for " + ChatColor.GOLD + getCost()
                        + ChatColor.GRAY + " points.");

                ConfigurationManager.saveConfig();
                GameManager.getInstance().getPlayerGame(player).updateScoreboard();

            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "You do not have the sufficient funds to buy: "
                        + getColor() + getName());
            }
        } else {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You already own this kit.");
        }
    }

    public boolean isBought(Player player){
        ConfigurationManager.loadConfig();
        List<String> kits = ConfigurationManager.getPlayerConfig().getStringList(player.getUniqueId() + ".kits");

        if (kits.contains(getName())){
            return true;
        }

        return false;
    }

}
