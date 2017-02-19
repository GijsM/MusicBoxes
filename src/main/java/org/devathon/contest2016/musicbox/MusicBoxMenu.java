package org.devathon.contest2016.musicbox;

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
import org.devathon.contest2016.MusicBoxPlugin;
import org.devathon.contest2016.util.LanguageUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gijs on 5-11-2016.
 */
public class MusicBoxMenu implements Listener {

    public static Set<MusicBoxMenu> openMenus = new HashSet<>();

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
        view = player.openInventory(Bukkit.createInventory(player, 9 * 6, ChatColor.BOLD + LanguageUtils.displayname));
        renderMain(view);
        openMenus.add(this);
        player.updateInventory();
    }

    public void renderMain(InventoryView view) {
        mainMenu = true;
        ItemStack around = new ItemStack(Material.STAINED_GLASS_PANE);
        around.setDurability((short) 9);
        ItemMeta meta = around.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + LanguageUtils.displayname);
        around.setItemMeta(meta);
        surroundView(around, 6);
        int i = 0;
        for (Instrument instrument : Instrument.values()) {
            view.setItem(i++ + 29, getItemStack(Material.NOTE_BLOCK, ChatColor.AQUA + getInstrumentName(instrument), ChatColor.GRAY + LanguageUtils.clickeditinstrument));
        }
        view.setItem(12, !box.data.on ? getItemStack(Material.MAGMA_CREAM, ChatColor.RED + LanguageUtils.musicboxoff, ChatColor.GRAY + LanguageUtils.clicktoggle) :
                getItemStack(Material.SLIME_BALL, ChatColor.GREEN + LanguageUtils.musicboxoff, ChatColor.GRAY + LanguageUtils.clicktoggle));
        view.setItem(14, getItemStack(Material.FEATHER, ChatColor.AQUA + LanguageUtils.speed + box.data.speed, ChatColor.GRAY + LanguageUtils.leftrighttochange));
        view.setItem(13, getItemStack(Material.DOUBLE_PLANT, ChatColor.AQUA + LanguageUtils.volume + box.data.volume, ChatColor.GRAY + LanguageUtils.leftrighttochange));
    }

    public String getInstrumentName(Instrument instrument) {
        switch (instrument) {
            case BASS_DRUM:
                return LanguageUtils.bassdrum;
            case BASS_GUITAR:
                return LanguageUtils.bassguitar;
            case PIANO:
                return LanguageUtils.piano;
            case SNARE_DRUM:
                return LanguageUtils.snaredrum;
            case STICKS:
                return LanguageUtils.sticks;
        }
        return null;
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
                    view.setItem(12, getItemStack(Material.MAGMA_CREAM, ChatColor.RED + LanguageUtils.musicboxoff, ChatColor.GRAY + LanguageUtils.clicktoggle));
                } else {
                    box.data.on = true;
                    view.setItem(12, getItemStack(Material.SLIME_BALL, ChatColor.GREEN + LanguageUtils.musicboxon, ChatColor.GRAY + LanguageUtils.clicktoggle));
                }
            } else if (event.getRawSlot() == 14) {
                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    if (box.data.speed == 1) return;
                    view.setItem(14, getItemStack(Material.FEATHER, ChatColor.AQUA + LanguageUtils.speed + --box.data.speed, ChatColor.GRAY + LanguageUtils.leftrighttochange));

                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (box.data.speed == 10) return;
                    view.setItem(14, getItemStack(Material.FEATHER, ChatColor.AQUA + LanguageUtils.speed + ++box.data.speed, ChatColor.GRAY + LanguageUtils.leftrighttochange));
                }
            } else if (event.getRawSlot() == 13) {
                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    if (box.data.volume == 1) return;
                    view.setItem(13, getItemStack(Material.DOUBLE_PLANT, ChatColor.AQUA + LanguageUtils.volume + --box.data.volume, ChatColor.GRAY + LanguageUtils.leftrighttochange));

                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (box.data.volume == 10) return;
                    view.setItem(13, getItemStack(Material.DOUBLE_PLANT, ChatColor.AQUA + LanguageUtils.volume + ++box.data.volume, ChatColor.GRAY + LanguageUtils.leftrighttochange));
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
                    ItemStack empty = getItemStack(Material.STAINED_GLASS_PANE, ChatColor.GRAY + LanguageUtils.clickaddnote, null);
                    empty.setDurability((short) 7);
                    view.setItem(slot, empty);
                } else {
                    ItemStack filled = getItemStack(Material.STAINED_GLASS_PANE, ChatColor.GRAY + LanguageUtils.clickaddnote, null);
                    filled.setDurability((short) 4);
                    view.setItem(slot, filled);
                }
            }
            if (slot == 4) {
                if (pagenumber == 0) {
                    for (int i = 0; i < 90; i++) view.setItem(i, null);
                    renderMain(view);
                } else {
                    if (pagenumber+1 == maxPages && pagenumber>0) {
                        boolean anythinghere = false;
                        for (byte[] bytes : box.data.data) {
                            for (int i = (maxPages-1)*8;i<(maxPages)*8;i++) {
                                if (bytes[i] != 0) anythinghere = true;
                            }
                        }
                        if (!anythinghere) {
                            maxPages--;
                            box.data.setSize(maxPages*8);
                        }
                    }
                    openInstrument(instrument, --pagenumber);
                }
            } else if (slot == 85) {
                if (pagenumber+1 >= maxPages) {
                    maxPages++;
                    box.data.setSize(maxPages*8);
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
        meta.setDisplayName(ChatColor.GRAY + LanguageUtils.displayname);
        around.setItemMeta(meta);
        surroundView(around, 10);
        if (pageNumber == 0) {
            view.setItem(4, getItemStack(Material.NOTE_BLOCK, ChatColor.GOLD + LanguageUtils.backtomain, ChatColor.GRAY + LanguageUtils.clickgoback));
        } else {
            view.setItem(4, getItemStack(Material.ARROW, ChatColor.GOLD + LanguageUtils.backonepage, ChatColor.GRAY + LanguageUtils.clickgoback));
        }
        if (pageNumber == maxPages-1) {
            view.setItem(85, getItemStack(Material.NETHER_STAR, ChatColor.GOLD + LanguageUtils.createnextpage, ChatColor.GRAY + LanguageUtils.clicktonextpage));
        } else {
            view.setItem(85, getItemStack(Material.ARROW, ChatColor.GOLD + LanguageUtils.gotonextpage, ChatColor.GRAY + LanguageUtils.clickgotonextpage));
        }
        ItemStack empty = getItemStack(Material.STAINED_GLASS_PANE, ChatColor.GRAY + LanguageUtils.clickaddnote, null);
        empty.setDurability((short) 7);
        ItemStack filled = getItemStack(Material.STAINED_GLASS_PANE, ChatColor.GRAY + LanguageUtils.clickremovenote, null);
        filled.setDurability((short) 4);
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
        close(true);
    }

    public void close(boolean removeFromList) {
        box.editing = false;
        player.getInventory().setContents(inventory);
        box.data.save();
        HandlerList.unregisterAll(this);
        player.closeInventory();
        player.updateInventory();
        if (removeFromList) openMenus.remove(this);
    }
}
