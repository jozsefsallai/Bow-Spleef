package me.elliottolson.bowspleef.kit.common;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.manager.PlayerManager;
import me.elliottolson.bowspleef.menu.KitMenu;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Copyright Elliott Olson (c) 2014. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar brandings
 * are the sole property of Elliott Olson. Distribution, reproduction, taking snippits, or
 * claiming any contents as your own will break the terms of the license, and void any
 * agreements with you, the third party.
 */
public class KitListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if (GameManager.getInstance().getPlayerGame(player) != null){
            Game game = GameManager.getInstance().getPlayerGame(player);

            if (game.getState() == Game.GameState.INGAME){
                if (KitManager.getKit(player).getSpecialItem() != null){
                    if (e.getItem().equals(KitManager.getKit(player).getSpecialItem())){
                        KitManager.getKit(player).execute(player);
                    }
                }
            } else if (game.getState() == Game.GameState.LOBBY || game.getState() == Game.GameState.STARTING){
                if (e.getItem().getType() == Material.IRON_SWORD){
                    if (!e.getPlayer().getInventory().getName().equalsIgnoreCase("[BS] Kits")){
                        MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Opening kits...");
                        KitMenu.openKitInventory(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (GameManager.getInstance().getPlayerGame(player) != null){
            Game game = GameManager.getInstance().getPlayerGame(player);

            if (game.getState() == Game.GameState.LOBBY || game.getState() == Game.GameState.STARTING){

                if (e.getInventory().getName().equalsIgnoreCase("[BS] Kits")){

                    for (Kit kit : KitManager.getKits){
                        if (e.getCurrentItem().getType() == kit.getIcon().getType()){
                            if (kit.isBought(player)){
                                if (KitManager.getKit(player) != kit){
                                    player.closeInventory();
                                    KitManager.setKit(player, kit);
                                    MessageManager.msg(MessageManager.MessageType.INFO, player, "You have selected the kit: "
                                            + kit.getColor() + kit.getName());
                                }
                            } else {
                                player.closeInventory();
                                KitMenu.openTransactionInventory(player, kit);
                            }
                        }
                    }

                    e.setCancelled(true);

                } else if (e.getInventory().getName().equalsIgnoreCase(ChatColor.DARK_AQUA + "[BS] " + ChatColor.BLACK + "Sale Confirmation")){

                    if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN.toString() + ChatColor.BOLD + "CONFIRM TRANSACTION")){

                        Kit kit = null;

                        for (Kit kits : KitManager.getKits){
                            if (e.getInventory().getItem(4).getType() == kits.getIcon().getType()){
                                kit = kits;
                            }
                        }

                        player.closeInventory();
                        kit.buy(player);
                        return;


                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED.toString() + ChatColor.BOLD + "DENY TRANSACTION")){

                        player.closeInventory();
                        MessageManager.msg(MessageManager.MessageType.ERROR, player, "This transaction has been cancelled.");
                        return;

                    } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY.toString() + ChatColor.BOLD + "INVALID FUNDS")){

                        player.closeInventory();
                        MessageManager.msg(MessageManager.MessageType.ERROR, player, "This transaction has been cancelled.");
                        return;

                    }

                    e.setCancelled(true);
                }
            }
        }
    }

}
