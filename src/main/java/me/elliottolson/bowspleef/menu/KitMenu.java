package me.elliottolson.bowspleef.menu;

import me.elliottolson.bowspleef.kit.common.Kit;
import me.elliottolson.bowspleef.kit.common.KitManager;
import me.elliottolson.bowspleef.manager.StatManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright Elliott Olson (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class KitMenu {

    private static Inventory kitInventory = Bukkit.createInventory(null, 54, "[BS] Kits");
    private static Inventory confirmInventory = Bukkit.createInventory(null, 54, ChatColor.DARK_AQUA + "[BS] " + ChatColor.BLACK + "Sale Confirmation");

    public static void openKitInventory(Player player){

        ItemStack greyGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        ItemMeta greyGlassMeta = greyGlass.getItemMeta();
        greyGlassMeta.setDisplayName(" ");
        greyGlass.setItemMeta(greyGlassMeta);

        ItemStack kitItem = new ItemStack(Material.BOW);
        ItemMeta kitItemMeta = kitItem.getItemMeta();
        kitItemMeta.setDisplayName("Kits");
        kitItem.setItemMeta(kitItemMeta);

        for (int i = 0; i < 18; i++){
            kitInventory.setItem(i, greyGlass);
        }

        kitInventory.setItem(4, kitItem);

        kitInventory.setItem(18, greyGlass);
        kitInventory.setItem(26, greyGlass);
        kitInventory.setItem(27, greyGlass);
        kitInventory.setItem(35, greyGlass);
        kitInventory.setItem(36, greyGlass);
        kitInventory.setItem(44, greyGlass);

        for (int i = 45; i < 54; i++){
            kitInventory.setItem(i, greyGlass);
        }

        for (int i = 0; i < KitManager.getKits.size(); i++){

            List<String> lore = new ArrayList<String>();

            Kit k = KitManager.getKits.get(i);

            ItemStack kit = new ItemStack(k.getIcon().getType());
            ItemMeta kitMeta = kit.getItemMeta();
            kitMeta.setDisplayName(k.getColor().toString() + ChatColor.BOLD + k.getName() +
                    ChatColor.DARK_GRAY + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC + "Kit");

            lore.add("");

            if (k.isBought(player)){
                lore.add(ChatColor.GREEN.toString() + ChatColor.BOLD + "CLICK " + ChatColor.GRAY + "to select kit.");
            } else {
                lore.add(ChatColor.RED.toString() + ChatColor.BOLD + "CLICK " + ChatColor.GRAY + "to buy kit.");
            }

            kitMeta.setLore(lore);
            kit.setItemMeta(kitMeta);

            kitInventory.setItem(i+19, kit);
        }

        player.openInventory(kitInventory);
    }

    public static void openTransactionInventory(Player player, Kit kit){

        ItemStack confirmGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
        ItemMeta confirmGlassMeta = confirmGlass.getItemMeta();
        confirmGlassMeta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "CONFIRM TRANSACTION");
        confirmGlassMeta.setLore(Arrays.asList("", ChatColor.GRAY + "By clicking this, you agree to the ingame", ChatColor.GRAY + "transaction."));
        confirmGlass.setItemMeta(confirmGlassMeta);

        ItemStack denyGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        ItemMeta denyGlassMeta = denyGlass.getItemMeta();
        denyGlassMeta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "DENY TRANSACTION");
        denyGlassMeta.setLore(Arrays.asList("", ChatColor.GRAY + "By clicking this, you deny the ingame", ChatColor.GRAY + "transaction."));
        denyGlass.setItemMeta(denyGlassMeta);

        ItemStack notEnoughGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
        ItemMeta notEnoughGlassMeta = notEnoughGlass.getItemMeta();
        notEnoughGlassMeta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD + "INVALID FUNDS");
        notEnoughGlassMeta.setLore(Arrays.asList("", ChatColor.GRAY + "You do not have the necessary funds to", ChatColor.GRAY + "complete this transaction."));
        notEnoughGlass.setItemMeta(notEnoughGlassMeta);

        ItemStack greyGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        ItemMeta greyGlassMeta = greyGlass.getItemMeta();
        confirmGlassMeta.setDisplayName(" ");
        greyGlass.setItemMeta(greyGlassMeta);

        int balanceAfter = StatManager.getPoints(player.getUniqueId()) - kit.getCost();

        ItemStack transactionDetails = new ItemStack(Material.NAME_TAG);
        ItemMeta transactionDetailsMeta = transactionDetails.getItemMeta();
        transactionDetailsMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Transaction Stats:");
        transactionDetailsMeta.setLore(Arrays.asList("", ChatColor.DARK_AQUA + "Balance Currently: " + ChatColor.GRAY
                + StatManager.getPoints(player.getUniqueId()), ChatColor.DARK_AQUA + "Transaction Cost: " +
                        ChatColor.GRAY + kit.getCost(),
                ChatColor.DARK_AQUA.toString() + "Balance After: " + ChatColor.GRAY.toString() + balanceAfter));

        transactionDetails.setItemMeta(transactionDetailsMeta);

        int confirm = 18;
        for (int i = 0; i < 16; i++){
            if (i == 4) confirm = 27;
            if (i == 8) confirm = 36;
            if (i == 12) confirm = 45;
            if (StatManager.getPoints(player.getUniqueId()) >= kit.getCost()){
                confirmInventory.setItem(confirm++, confirmGlass);
            } else {
                confirmInventory.setItem(confirm++, notEnoughGlass);
            }
        }

        int deny = 23;
        for (int i = 0; i < 16; i++){
            if (i == 4) deny = 32;
            if (i == 8) deny = 41;
            if (i == 12) deny = 50;
            confirmInventory.setItem(deny++, denyGlass);
        }

        int slot = 0;
        for (int i = 0; i < 20; i++){
            if (i == 4) slot = 5;
            if (i == 13) slot = 14;
            if (i == 17) slot = 22;
            if (i == 18) slot = 31;
            if (i == 19) slot = 40;
            if (i == 20) slot = 49;
            confirmInventory.setItem(slot++, greyGlass);
        }

        //Etc
        confirmInventory.setItem(49, greyGlass);

        ItemStack kitIcon = new ItemStack(kit.getIcon().getType());
        ItemMeta kitIconMeta = kitIcon.getItemMeta();
        kitIconMeta.setDisplayName(kit.getColor().toString() + ChatColor.BOLD + kit.getName() + ChatColor.DARK_GRAY
                + " - " + ChatColor.GRAY.toString() + ChatColor.ITALIC + "Kit");
        kitIcon.setItemMeta(kitIconMeta);

        //Details
        confirmInventory.setItem(4, kitIcon);
        confirmInventory.setItem(13, transactionDetails);

        player.openInventory(confirmInventory);
    }

    public static Inventory getKitInventory() {
        return kitInventory;
    }

    public static Inventory getConfirmInventory() {
        return confirmInventory;
    }
}
