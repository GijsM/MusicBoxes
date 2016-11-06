package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.listeners.*;
import org.devathon.contest2016.util.CustomCrafting;

import java.io.IOException;


public class DevathonPlugin extends JavaPlugin implements Listener {

    public static Plugin plugin;


    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockRemoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockRedstoneListener(), this);
        CustomCrafting.addRecipe(Bukkit.getServer());
        DataLoader.init(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            MoveListener.checkPlayer(player);
        }
        plugin = this;
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

