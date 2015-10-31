package me.elliottolson.bowspleef.kit.common;


import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Copyright Elliott Olson (c) 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public abstract class Kit {

    public abstract String getName();
    public abstract ChatColor getColor();
    public abstract ItemStack getIcon();

    public abstract int getCost();

    public abstract ArmourSet getArmour();

    public abstract ItemStack getBow();
    public abstract ItemStack getSpecialItem();

    public abstract List<ItemStack> getItems();

    //Methods
    public abstract void execute(Player player);
    public abstract boolean hasCooldown();

    //Info
    public void give(Player player){
        if (isBought(player)){
            //TGive armour
            player.getInventory().setHelmet(getArmour().getHelmet());;
            player.getInventory().setChestplate(getArmour().getChestplate());;
            player.getInventory().setLeggings(getArmour().getLeggings());;
            player.getInventory().setBoots(getArmour().getBoots());;

            if (getBow() != null) player.getInventory().setItem(0, getBow());
            if (getSpecialItem() != null) player.getInventory().setItem(2, getSpecialItem());

            player.getInventory().setItem(8, new ItemStack(Material.ARROW));

            if (getItems().size() > 0){
                for (int i = 0; i < getItems().size(); i++){
                    int slot = 9;
                    player.getInventory().setItem(slot, getItems().get(i));
                    slot++;
                }
            }

            MessageManager.msg(MessageManager.MessageType.INFO, player, "You have received the kit: " + getColor() + getName());
        }
    }

    public void buy(Player player){
        if (!isBought(player)){
            if (ConfigurationManager.getPlayerConfig().getInt(player.getUniqueId().toString() + ".points") >= getCost()){

                ConfigurationManager.getPlayerConfig().set(player.getUniqueId().toString() + ".kit." +
                        getName() + ".owned", "yes");

                int remainingFunds = ConfigurationManager.getPlayerConfig().getInt(player.getUniqueId().toString() + ".points") - getCost();
                ConfigurationManager.getPlayerConfig().set(player.getUniqueId().toString() + ".points", remainingFunds);

                MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "You have successfully bought: " +
                        ChatColor.DARK_AQUA + getName() + ChatColor.GRAY + " for " + ChatColor.GOLD + getCost()
                        + ChatColor.GRAY + " points.");

            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "You do not have the sufficient funds to buy: "
                        + getColor() + getName());
            }
        } else {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You already own this kit.");
        }
    }

    public boolean isBought(Player player){
        List<String> kits = ConfigurationManager.getPlayerConfig().getStringList(player.getUniqueId() + ".kits");
        if (kits.contains(getName())){
            return true;
        }
        return false;
    }

}
