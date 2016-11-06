package org.devathon.contest2016.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.devathon.contest2016.DataLoader;
import org.devathon.contest2016.musicbox.MusicBox;

/**
 * Created by Gijs on 6-11-2016.
 */
public class BlockRedstoneListener implements Listener{

    @EventHandler
    public void onRedstone(BlockPhysicsEvent event) {
        if (DataLoader.dataMap.containsKey(event.getBlock())) {
            Bukkit.getLogger().info("bo");
            MusicBox box = DataLoader.loadBox(event.getBlock());
            box.data.on = event.getBlock().getBlockPower() > 0;
        }
    }
}
