package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.listeners.*;
import org.devathon.contest2016.util.CustomCrafting;

import java.io.IOException;


public class MusicBoxPlugin extends JavaPlugin implements Listener {

    public static Plugin plugin;


    @Override
    public void onEnable() {
        register(new BlockRedstoneListener(), new MoveListener(), new PlayerInteractListener(), new BlockRemoveListener(), new BlockPlaceListener());
        CustomCrafting.addRecipe(Bukkit.getServer());
        DataLoader.init(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            MoveListener.checkPlayer(player);
        }
        plugin = this;
    }

    public void register(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
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

