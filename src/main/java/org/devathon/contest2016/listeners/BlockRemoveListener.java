package org.devathon.contest2016.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

    @EventHandler
    public void onDamage(BlockDamageEvent event) {
        checkRemove(event.getBlock(), event.getPlayer());
    }

    void checkRemove(Block block, Player player) {
        for (MusicBox box : MoveListener.near.get(player)) {
            if (box.block.getX() == block.getX() && box.block.getZ() == block.getZ() && box.block.getY() == block.getY()) {
                block.getWorld().dropItemNaturally(block.getLocation().add(0.5,0.5,0.5), CustomCrafting.musicBox);
                DataLoader.removeBox(box);
                box.block.setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        checkRemove(event.getBlock(), event.getPlayer());
    }
}
