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
import org.devathon.contest2016.MusicBoxPlugin;

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
    int maxPages;
    boolean mainMenu;
    int instrument;
    int pagenumber;

    public MusicBoxMenu(Player player, MusicBox box) {
        inventory = player.getInventory().getContents();
        player.getInventory().setContents(new ItemStack[9 * 4]);
        Bukkit.getPluginManager().registerEvents(this, MusicBoxPlugin.plugin);
        this.player = player;
        this.box = box;
        maxPages = box.data.data[0].length/8;
        mainMenu = true;
        box.editing = true;
        view = player.openInventory(Bukkit.createInventory(player, 9 * 6, ChatColor.BOLD + "Music Box"));
        renderMain(view);
    }

    public void renderMain(InventoryView view) {
        mainMenu = true;
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
        view.setItem(12, !box.data.on ? getItemStack(Material.MAGMA_CREAM, ChatColor.RED + "Music Box: Off!", ChatColor.GRAY + "Click to toggle it") :
                getItemStack(Material.SLIME_BALL, ChatColor.GREEN + "Music Box: On!", ChatColor.GRAY + "Click to toggle it"));
        view.setItem(14, getItemStack(Material.FEATHER, ChatColor.AQUA + "Speed: " + box.data.speed, ChatColor.GRAY + "Left and Right click to change"));
    }

    public void surroundView(ItemStack itemStack, int height) {
        for (int i = 0; i < 9; i++) {
            view.setItem(i, itemStack);
        }
        for (int i = 9 * (height - 1); i < height * 9; i++) {
            view.setItem(i, itemStack);
        }
        for (int i = 9; i < 9 * (height - 1); i += 9) {
            view.setItem(i, itemStack);
        }
        for (int i = 17; i < (height) * 9; i += 9) {
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
            } else if (event.getRawSlot() >= 28 && event.getRawSlot() < 34) {
                openInstrument(4 - (33 - event.getRawSlot()), 0);
            }
        } else {
            int slot = event.getRawSlot();
            int colum = (slot % 9) - 1;
            int row = (1 - slot / 9) * -1;
            if (colum >= 0 && colum < 7 && row <= 7 && row >= 0) {
                boolean checked = isChecked(pagenumber, row, colum, instrument);
                check(pagenumber, row, colum, instrument, !checked);
                if (checked) {
                    ItemStack empty = getItemStack(Material.STAINED_GLASS_PANE, ChatColor.GRAY + "Click to add a note", null);
                    empty.setDurability((short) 7);
                    view.setItem(slot, empty);
                } else {
                    ItemStack filled = getItemStack(Material.STAINED_GLASS_PANE, ChatColor.GRAY + "Click to remove this note", null);
                    filled.setDurability((short) 4);
                    view.setItem(slot, filled);
                }
            }
            if (slot == 4) {
                if (pagenumber == 0) {
                    for (int i = 0; i < 90; i++) view.setItem(i, null);
                    renderMain(view);
                } else {
                    openInstrument(instrument, --pagenumber);
                }
            } else if (slot == 85) {
                if (pagenumber+1 >= maxPages) {
                    maxPages++;
                    box.data.setSize(maxPages*8);
                    Bukkit.getLogger().info(box.data.data[1].length + "");
                }
                openInstrument(instrument, ++pagenumber);
            }

        }
    }

    public void openInstrument(int instrument, int pageNumber) {
        this.instrument = instrument;
        this.pagenumber = pageNumber;
        mainMenu = false;
        ItemStack around = new ItemStack(Material.STAINED_GLASS_PANE);
        around.setDurability((short) 9);
        ItemMeta meta = around.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Music Box");
        around.setItemMeta(meta);
        surroundView(around, 10);
        if (pageNumber == 0) {
            view.setItem(4, getItemStack(Material.ARROW, ChatColor.GOLD + "Back to the Main Menu", ChatColor.GRAY + "Click to go back"));
        } else {
            view.setItem(4, getItemStack(Material.ARROW, ChatColor.GOLD + "Go Back One Page", ChatColor.GRAY + "Click to go back"));
        }
        if (pageNumber == maxPages-1) {
            view.setItem(85, getItemStack(Material.NETHER_STAR, ChatColor.GOLD + "Create next page", ChatColor.GRAY + "Click to create the next page"));
        } else {
            view.setItem(85, getItemStack(Material.ARROW, ChatColor.GOLD + "Go to the Next Page", ChatColor.GRAY + "Click to go to the next page"));
        }
        ItemStack empty = getItemStack(Material.STAINED_GLASS_PANE, ChatColor.GRAY + "Click to add a  note", null);
        empty.setDurability((short) 7);
        ItemStack filled = getItemStack(Material.STAINED_GLASS_PANE, ChatColor.GRAY + "Click to remove this note", null);
        filled.setDurability((short) 4);
        Bukkit.getLogger().info(box.data.data[instrument].length + "ss");
        for (int i = 1; i < 9; i++) {
            byte b = box.data.data[instrument][pageNumber * 8 + i - 1];
            for (int x = 1; x < 8; x++) {
                boolean checked = ((b & ((int) Math.pow(2, x - 1))) != 0);
                view.setItem(i * 9 + x, checked ? filled : empty);
            }
        }
    }


    public boolean isChecked(int page, int row, int colum, int instrument) {
        byte b = box.data.data[instrument][page * 8 + row];
        byte result = (byte) (b & (int) Math.pow(2, colum));
        return result != 0;
    }

    public void check(int page, int row, int colum, int instrument, boolean check) {
        int place = page * 8 + row;
        byte bit = (byte) Math.pow(2, colum);
        if (check) box.data.data[instrument][place] = (byte) (box.data.data[instrument][place] | bit);
        else box.data.data[instrument][place] = (byte) (box.data.data[instrument][place] & 0b11111111 - bit);
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
