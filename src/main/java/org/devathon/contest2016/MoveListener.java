package org.devathon.contest2016;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Gijs on 5-11-2016.
 */
public class MoveListener implements Listener {

    @EventHandler
    public void moveChunk(PlayerMoveEvent event) {
        if (event.getTo().getChunk() != event.getTo().getChunk()) {

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

    }
}
