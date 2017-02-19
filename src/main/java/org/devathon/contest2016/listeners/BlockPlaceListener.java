package org.devathon.contest2016.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.devathon.contest2016.DataLoader;
import org.devathon.contest2016.util.CustomCrafting;

/**
 * Created by Gijs on 5-11-2016.
 */
public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getItemInHand() != null && event.getItemInHand().isSimilar(CustomCrafting.musicBox)) {
            DataLoader.createBox(event.getBlock());
            for (Player player : Bukkit.getOnlinePlayers()) DataLoaderListener.checkPlayer(player);
        }
    }
}
