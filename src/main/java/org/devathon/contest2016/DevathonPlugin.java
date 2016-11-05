package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.listeners.BlockPlaceListener;
import org.devathon.contest2016.listeners.MoveListener;

import java.io.IOException;


public class DevathonPlugin extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        CustomCrafting.addRecipe(Bukkit.getServer());
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

}

