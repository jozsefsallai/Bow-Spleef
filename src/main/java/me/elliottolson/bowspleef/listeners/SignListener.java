package me.elliottolson.bowspleef.listeners;

import me.elliottolson.bowspleef.game.Game;
import me.elliottolson.bowspleef.game.GameManager;
import me.elliottolson.bowspleef.manager.ConfigurationManager;
import me.elliottolson.bowspleef.util.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (e.getClickedBlock().getState() instanceof Sign){
                Sign sign = (Sign) e.getClickedBlock().getState();

                if (sign.getLine(0).equalsIgnoreCase(ChatColor.AQUA + "[BowSpleef]")){
                    String gameName = sign.getLine(1);

                    if (gameName != null){
                        Game game = GameManager.getInstance().getGame(gameName);
                        game.addPlayer(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCreate(SignChangeEvent e){
        Player player = e.getPlayer();

        if (e.getLine(0).equalsIgnoreCase("[BowSpleef]") && e.getLine(1).equalsIgnoreCase("Join")) {
            if (player.hasPermission("bowspleef.admin.sign.create")) {
                if (e.getLine(2) != null) {

                    String gameName = e.getLine(2);
                    Game game = GameManager.getInstance().getGame(gameName);

                    if (game != null) {
                        MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "You have setup the game sign for: "
                                + ChatColor.AQUA + gameName);

                        Location l = e.getBlock().getLocation();

                        ConfigurationManager.getArenaConfig().set("arenas." + gameName + ".sign.x", l.getBlockX());
                        ConfigurationManager.getArenaConfig().set("arenas." + gameName + ".sign.y", l.getBlockY());
                        ConfigurationManager.getArenaConfig().set("arenas." + gameName + ".sign.z", l.getBlockZ());
                        ConfigurationManager.getArenaConfig().set("arenas." + gameName + ".sign.world", l.getWorld().getName());

                        ConfigurationManager.saveConfig();
                        ConfigurationManager.loadConfig();

                        e.setLine(0, ChatColor.AQUA + "[BowSpleef]");
                        e.setLine(1, gameName);
                        e.setLine(2, game.getState().getColor() + game.getState().getName());
                        e.setLine(3, ChatColor.DARK_GREEN.toString() + game.getPlayers().size() + ChatColor.DARK_GRAY + "/" +
                                ChatColor.DARK_GREEN.toString() + game.getMaximumPlayers());



                    } else {
                        MessageManager.msg(MessageManager.MessageType.ERROR, player, "This game doesn't exist.");
                    }
                } else {
                    MessageManager.msg(MessageManager.MessageType.ERROR, player, "You didn't specify a game.");
                }
            }
        }

    }

}
