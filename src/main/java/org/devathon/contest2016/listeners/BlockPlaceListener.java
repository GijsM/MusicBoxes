package org.devathon.contest2016.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.devathon.contest2016.CustomCrafting;
import org.devathon.contest2016.DataLoader;

import java.util.List;

/**
 * Created by Gijs on 5-11-2016.
 */
public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        List<String> lore = event.getItemInHand().getItemMeta().getLore();
        if (lore != null && lore.equals(CustomCrafting.musicBox.getItemMeta().getLore())) {
            DataLoader.createBox(event.getBlock());
        }
    }
}
