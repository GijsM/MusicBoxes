package org.devathon.contest2016.musicbox;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.devathon.contest2016.DataLoader;
import org.devathon.contest2016.DevathonPlugin;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by Gijs on 5-11-2016.
 */
public class MusicBoxMenu implements Listener {

    Player player;
    ItemStack[] inventory;
    MusicBox box;
    InventoryView view;
    boolean mainMenu;
   public MusicBoxMenu(Player player, MusicBox box) {
       inventory = player.getInventory().getContents();
       player.getInventory().setContents(new ItemStack[9*4]);
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
        around.setDurability((short) 9);
        ItemMeta meta = around.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Music Box");
        around.setItemMeta(meta);
        surroundView(around, 6);
        int i = 0;
        for (Instrument instrument : Instrument.values()) {
            view.setItem(i++ + 29, getItemStack(Material.NOTE_BLOCK, ChatColor.AQUA + WordUtils.capitalize((instrument.name().replace("_", " ").toLowerCase())), ChatColor.GRAY + "Click to edit this instrument"));
        }
        view.setItem(12, !box.data.on ?  getItemStack(Material.MAGMA_CREAM, ChatColor.RED + "Music Box: Off!", ChatColor.GRAY + "Click to toggle it") :
                                        getItemStack(Material.SLIME_BALL, ChatColor.GREEN + "Music Box: On!", ChatColor.GRAY + "Click to toggle it"));
        view.setItem(14, getItemStack(Material.FEATHER, ChatColor.AQUA + "Speed: " + box.data.speed, ChatColor.GRAY + "Left and Right click to change"));
    }

    public void surroundView(ItemStack itemStack, int height) {
        for (int i = 0; i<9;i++) {
            view.setItem(i, itemStack);
        }
        for (int i = 9*(height-1); i<height*9;i++) {
            view.setItem(i, itemStack);
        }
        for (int i = 9; i<9*(height-1);i+=9) {
            view.setItem(i, itemStack);
        }
        for (int i = 17; i<(height)*9;i+=9) {
            view.setItem(i, itemStack);
        }
    }

    public ItemStack getItemStack(Material material, String name, String lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta1 = itemStack.getItemMeta();
        meta1.setDisplayName(name);
        meta1.setLore(Collections.singletonList(lore));
        itemStack.setItemMeta(meta1);
        return itemStack;
    }

    public void updatePosition(int i) {

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView() != view) return;
        event.setCancelled(true);
        if (mainMenu) {
            if (event.getRawSlot() == 12) {
                if (box.data.on) {
                    box.data.on = false;
                    view.setItem(12, getItemStack(Material.MAGMA_CREAM, ChatColor.RED + "Music Box: Off!", ChatColor.GRAY + "Click to toggle it"));
                } else {
                    box.data.on = true;
                    view.setItem(12, getItemStack(Material.SLIME_BALL, ChatColor.GREEN + "Music Box: On!", ChatColor.GRAY + "Click to toggle it"));
                }
            } else if (event.getRawSlot() == 14) {
                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    if (box.data.speed == 1) return;
                    view.setItem(14, getItemStack(Material.FEATHER, ChatColor.AQUA + "Speed: " + --box.data.speed, ChatColor.GRAY + "Left and Right click to change"));

                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (box.data.speed == 10) return;
                    view.setItem(14, getItemStack(Material.FEATHER, ChatColor.AQUA + "Speed: " + ++box.data.speed, ChatColor.GRAY + "Left and Right click to change"));
                }

            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) throws IOException {
        if (event.getView() != view) return;
        box.editing = false;
        player.getInventory().setContents(inventory);
        DataLoader.saveBox(box);
        HandlerList.unregisterAll(this);
    }
}
