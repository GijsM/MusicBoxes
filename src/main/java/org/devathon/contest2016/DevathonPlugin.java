package org.devathon.contest2016;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public class DevathonPlugin extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
        DataLoader.init(this);
    }

    @Override
    public void onDisable() {
        try {
            DataLoader.saveLocations();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void click(PlayerInteractEvent event) {
        if (event.getClickedBlock().getType() != Material.ENDER_PORTAL_FRAME) return;
        DataLoader.createBox(event.getClickedBlock());

    }
}

