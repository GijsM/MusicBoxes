package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

/**
 * Created by Gijs on 5-11-2016.
 */
public class MusicBox {
    public Block block;
    public MusicBoxData data;

    public MusicBox(Block block, MusicBoxData data) {
        this.block = block;
        this.data = data;
    }

    public void reload() {

    }

    public void load() {

    }

    public void unload() {

    }

    public void tick() {

        Bukkit.getLogger().info("hai");


    }
}
