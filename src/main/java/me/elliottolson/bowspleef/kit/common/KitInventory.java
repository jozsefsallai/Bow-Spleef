package me.elliottolson.bowspleef.kit.common;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright Elliott Olson (c) 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class KitInventory {

    private Player player;

    private List<String> confirmGlassLore = new ArrayList<String>();
    private List<String> denyGlassLore = new ArrayList<String>();
    private List<String> notEnoughMoneyLore = new ArrayList<String>();
    private List<String> transactionLore = new ArrayList<String>();

    //Inventories
    private static Inventory confirmInventory;

    public KitInventory(Player player){
        this.player = player;
    }

    //TODO: Confirmation
//    public void openConfirmation(){
//        confirmInventory = Bukkit.createInventory(player, 54, ChatColor.DARK_AQUA + "[BS] " + ChatColor.BLACK + "Sale Confirmation");
//        confirmInventory.setMaxStackSize(1);
//
//        ItemStack confirmGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
//        ItemMeta confirmGlassMeta = confirmGlass.getItemMeta();
//        confirmGlassMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "CONFIRM TRANSACTION");
//        confirmGlassLore.add("");
//        confirmGlassLore.add(ChatColor.GRAY + "By clicking this, you agree to the ingame");
//        confirmGlassLore.add(ChatColor.GRAY + "transaction.");
//        confirmGlassMeta.setLore(confirmGlassLore);
//        confirmGlass.setItemMeta(confirmGlassMeta);
//        confirmGlassLore.clear();
//
//        ItemStack denyGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
//        ItemMeta denyGlassMeta = denyGlass.getItemMeta();
//        denyGlassMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "DENY TRANSACTION");
//        denyGlassLore.add("");
//        denyGlassLore.add(ChatColor.GRAY + "By clicking this, you deny the ingame");
//        denyGlassLore.add(ChatColor.GRAY + "transaction.");
//        denyGlassMeta.setLore(denyGlassLore);
//        denyGlass.setItemMeta(denyGlassMeta);
//        denyGlassLore.clear();
//
//        ItemStack notEnoughGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
//        ItemMeta notEnoughGlassMeta = notEnoughGlass.getItemMeta();
//        notEnoughGlassMeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD + "INVALID FUNDS");
//        notEnoughMoneyLore.add("");
//        notEnoughMoneyLore.add(ChatColor.GRAY + "You do not have the necessary funds to");
//        notEnoughMoneyLore.add(ChatColor.GRAY + "complete this transaction.");
//        notEnoughGlassMeta.setLore(notEnoughMoneyLore);
//        notEnoughGlass.setItemMeta(notEnoughGlassMeta);
//        notEnoughMoneyLore.clear();
//
//        ItemStack greyGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
//        ItemMeta greyGlassMeta = greyGlass.getItemMeta();
//        confirmGlassMeta.setDisplayName(null);
//        greyGlass.setItemMeta(greyGlassMeta);
//
//        // TODO: Actually setup the PlayerManager
//        int balanceAfter = playerManager.getBalance()-item.getCost();
//
//        ItemStack transactionDetails = new ItemStack(Material.NAME_TAG);
//        ItemMeta transactionDetailsMeta = transactionDetails.getItemMeta();
//        transactionDetailsMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Transaction Stats:");
//        transactionLore.add("");
//        transactionLore.add(ChatColor.DARK_AQUA + "Balance Currently: "+ ChatColor.GRAY + playerManager.getBalance());
//        transactionLore.add(ChatColor.DARK_AQUA + "Transaction Cost: " + ChatColor.GRAY + item.getCost());
//        transactionLore.add(ChatColor.DARK_AQUA.toString() + "Balance After: " + ChatColor.GRAY.toString() + balanceAfter);
//        transactionDetailsMeta.setLore(transactionLore);
//        transactionDetails.setItemMeta(transactionDetailsMeta);
//        transactionLore.clear();
//
//        int confirm = 18;
//        for (int i = 0; i < 16; i++){
//            if (i == 4) confirm = 27;
//            if (i == 8) confirm = 36;
//            if (i == 12) confirm = 45;
//            if (playerManager.getBalance() >= item.getCost()){
//                confirmInventory.setItem(confirm++, confirmGlass);
//            } else {
//                confirmInventory.setItem(confirm++, notEnoughGlass);
//            }
//        }
//
//        int deny = 23;
//        for (int i = 0; i < 16; i++){
//            if (i == 4) deny = 32;
//            if (i == 8) deny = 41;
//            if (i == 12) deny = 50;
//            confirmInventory.setItem(deny++, denyGlass);
//        }
//
//        int slot = 0;
//        for (int i = 0; i < 20; i++){
//            if (i == 4) slot = 5;
//            if (i == 13) slot = 14;
//            if (i == 17) slot = 22;
//            if (i == 18) slot = 31;
//            if (i == 19) slot = 40;
//            if (i == 20) slot = 49;
//            confirmInventory.setItem(slot++, greyGlass);
//        }
//
//        //Etc
//        confirmInventory.setItem(49, greyGlass);
//
//        //Details
//        confirmInventory.setItem(4, item.getItem());
//        confirmInventory.setItem(13, transactionDetails);
//
//        player.openInventory(confirmInventory);
//    }

    public static Inventory getConfirmInventory(){
        return confirmInventory;
    }

}
