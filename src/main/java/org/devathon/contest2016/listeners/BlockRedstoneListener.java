package org.devathon.contest2016.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.devathon.contest2016.DataLoader;
import org.devathon.contest2016.musicbox.MusicBox;

import java.io.IOException;

/**
 * Created by Gijs on 6-11-2016.
 */
public class BlockRedstoneListener implements Listener{

    @EventHandler
    public void onRedstone(BlockPhysicsEvent event) throws IOException {
        if (event.getBlock().getType() != Material.ENDER_PORTAL_FRAME) return;
        if (DataLoader.dataMap.containsKey(event.getBlock())) {
            MusicBox box = DataLoader.loadBox(event.getBlock());
            boolean on = event.getBlock().getBlockPower() > 0;
            boolean toggle =  box.data.on != on;
            box.data.on = on;
            if (toggle) box.data.save();
        }
    }
}
