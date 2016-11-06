package org.devathon.contest2016.musicbox;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.devathon.contest2016.DataLoader;
import org.devathon.contest2016.DevathonPlugin;

import java.io.IOException;

/**
 * Created by Gijs on 5-11-2016.
 */
public class MusicBoxMenu implements Listener {

    Player player;
    MusicBox box;
    InventoryView view;
    boolean mainMenu;
   public MusicBoxMenu(Player player, MusicBox box) {
       Bukkit.getPluginManager().registerEvents(this, DevathonPlugin.plugin);
        this.player = player;
        this.box = box;
        mainMenu = true;
        box.editing = true;
       view = player.openInventory(Bukkit.createInventory(player, 9*6, ChatColor.BOLD + "Music Box"));
       renderMain(view);
   }

    public void renderMain(InventoryView view) {
        ItemStack around = new ItemStack(Material.STAINED_GLASS_PANE);
        around.setData(new MaterialData(Material.STAINED_GLASS_PANE, (byte) 9));
        for (int i = 0; i<9;i++) {
            view.setItem(i, around);
        }
        for (int i = 45; i<54;i++) {
            view.setItem(i, around);
        }
        for (int i = 9; i<45;i+=9) {
            view.setItem(i, around);
        }
        for (int i = 17; i<54;i+=9) {
            view.setItem(i, around);
        }
    }

    public void updatePosition(int i) {

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView() != view) return;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) throws IOException {
        if (event.getView() != view) return;
        box.editing = false;
        DataLoader.saveBox(box);
        HandlerList.unregisterAll(this);
    }
}
