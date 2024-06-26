package org.devathon.contest2016.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.devathon.contest2016.DataLoader;
import org.devathon.contest2016.musicbox.MusicBox;
import org.devathon.contest2016.util.Util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gijs on 5-11-2016.
 */
public class DataLoaderListener implements Listener {

    public static Map<Player, Set<MusicBox>> near = new ConcurrentHashMap<>();

    @EventHandler
    public void move(PlayerMoveEvent event) {
        if (event.getTo().getChunk() != event.getFrom().getChunk()) {
            checkPlayer(event.getPlayer());
        }
    }

    public static void checkPlayer(Player player) {
        Set<MusicBox> boxes = near.get(player);
        if (boxes == null) {
            boxes = new HashSet<>();
            near.put(player, boxes);
        }
        Set<MusicBox> toRemove = new HashSet<>();
        for (MusicBox box : boxes) {
                if (!Util.isClose(box.block.getLocation(), player.getLocation(), 16 * 3)) {
                    toRemove.add(box);
                    box.players.remove(player);
                    if (box.players.isEmpty()) {
                        DataLoader.unLoadBox(box);
                    }
                }
        }
        boxes.removeAll(toRemove);

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
        for (Object object : boxes.toArray()) {
            MusicBox box = (MusicBox) object;
            box.players.remove(event.getPlayer());
            if (box.players.isEmpty()) {
                DataLoader.unLoadBox(box);
            }
        }
        near.remove(event.getPlayer());
    }
}
