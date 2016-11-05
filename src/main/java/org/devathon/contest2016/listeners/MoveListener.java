package org.devathon.contest2016.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.devathon.contest2016.DataLoader;
import org.devathon.contest2016.MusicBox;
import org.devathon.contest2016.Util;

import java.util.*;

/**
 * Created by Gijs on 5-11-2016.
 */
public class MoveListener implements Listener {

    Map<MusicBox, Set<Player>> activated = new HashMap<>();
    Map<Player, Set<MusicBox>> near = new HashMap<>();

    @EventHandler
    public void moveChunk(PlayerMoveEvent event) {
        if (event.getTo().getChunk() != event.getFrom().getChunk()) {
            checkPlayer(event.getPlayer());
        }
    }

    public void checkPlayer(Player player) {
        //Boxes to remove
        Set<MusicBox> boxes = near.get(player);
        if (boxes == null) {
            boxes = new HashSet<>();
            near.put(player, boxes);
        }
        Iterator<MusicBox> iterator = boxes.iterator();
        while (iterator.hasNext()) {
            MusicBox box = iterator.next();
            if (!Util.isClose(box.block.getLocation(), player.getLocation(), 16*3)) {
                boxes.remove(box);
                box.players.remove(player);
                if (box.players.isEmpty()) {
                    DataLoader.unLoadBox(box);
                    activated.remove(box);
                }
            }
        }

        //Boxes to add
        for (Block block : DataLoader.dataMap.keySet()) {
            if (Util.isClose(block.getLocation(), player.getLocation(), 16*3)) {
                MusicBox box = DataLoader.loadBox(block);
                near.get(player).add(box);
                box.players.add(player);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        near.put(event.getPlayer(), new HashSet<>());
        checkPlayer(event.getPlayer());

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Set<MusicBox> boxes = near.get(event.getPlayer());
        Iterator<MusicBox> iterator = boxes.iterator();
        while (iterator.hasNext()) {
            MusicBox box = iterator.next();
            boxes.remove(box);
            box.players.remove(event.getPlayer());
            if (box.players.isEmpty()) {
                DataLoader.unLoadBox(box);
                activated.remove(box);
            }
        }
        near.remove(event.getPlayer());
    }
}
