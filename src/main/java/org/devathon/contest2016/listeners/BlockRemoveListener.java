package org.devathon.contest2016.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.devathon.contest2016.DataLoader;
import org.devathon.contest2016.musicbox.MusicBox;
import org.devathon.contest2016.util.CustomCrafting;

/**
 * Created by Gijs on 6-11-2016.
 */
public class BlockRemoveListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(BlockDamageEvent event) {
        checkRemove(event.getBlock(), event.getPlayer(), event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent event) {
        checkRemove(event.getBlock(), event.getPlayer(), event);
    }

    void checkRemove(Block block, Player player, Cancellable cancellable) {
        MusicBox box1 =  null;
        for (MusicBox box : DataLoaderListener.near.get(player)) {
            if (box.block.getX() == block.getX() && box.block.getZ() == block.getZ() && box.block.getY() == block.getY()) {
                if (!player.hasPermission("MusicBoxes.break") || cancellable.isCancelled()) {
                    cancellable.setCancelled(true);
                    return;
                }
                if (player.getGameMode() != GameMode.CREATIVE) block.getWorld().dropItemNaturally(block.getLocation().add(0.5,0.5,0.5), CustomCrafting.musicBox);
                DataLoader.removeBox(box);
                box.block.setType(Material.AIR);
                box1 = box;
                break;
            }
        }
        if (box1 != null) DataLoaderListener.near.get(player).remove(box1);
    }

}
