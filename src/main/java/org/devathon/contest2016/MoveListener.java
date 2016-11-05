package org.devathon.contest2016;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        Iterator<MusicBox> iterator = boxes.iterator();
        while (iterator.hasNext()) {
            MusicBox box = iterator.next();
            if (!Util.isClose(box.block.getLocation(), player.getLocation(), 16*3)) {
                boxes.remove(box);
                Set<Player> nearplayers= activated.get(box);
                nearplayers.remove(player);
                if (nearplayers.isEmpty()) {
                    activated.remove(box);
                    DataLoader.unLoadBox(box);
                }
            }
        }

        //Boxes to add
        for (Block block : DataLoader.dataMap.keySet()) {
            if (Util.isClose(block.getLocation(), player.getLocation(), 16*3)) {
                MusicBox box = DataLoader.loadBox(block);
                near.get(player).add(box);
                Set<Player> set = activated.get(box);
                if (set == null) {
                    Set<Player> newSet = new HashSet<>();
                    newSet.add(player);
                    activated.put(box, newSet);
                } else {
                    set.add(player);
                }
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
                Set<Player> nearplayers= activated.get(box);
                nearplayers.remove(event.getPlayer());
                if (nearplayers.isEmpty()) {
                    activated.remove(box);
                    DataLoader.unLoadBox(box);
                }
        }
        near.remove(event.getPlayer());
    }
}
