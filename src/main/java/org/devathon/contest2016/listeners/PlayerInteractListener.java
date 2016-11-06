package org.devathon.contest2016.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.devathon.contest2016.musicbox.MusicBox;

/**
 * Created by Gijs on 6-11-2016.
 */
public class PlayerInteractListener implements Listener {

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
        for (MusicBox box : MoveListener.near.get(event.getPlayer())) {
            if (box.block.getX() == event.getClickedBlock().getX() && box.block.getZ() == event.getClickedBlock().getZ() && box.block.getY() == event.getClickedBlock().getY()) {
                box.openInventory(event.getPlayer());
                event.setCancelled(true);
            }
        }
    }

}
