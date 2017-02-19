package org.devathon.contest2016.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.util.CustomCrafting;

/**
 * Created by Gijs on 25-1-2017.
 */
public class CraftItemListener implements Listener {

    @EventHandler
    public void craft(PrepareItemCraftEvent event) {
        if (event.getRecipe().getResult().equals(CustomCrafting.musicBox) && !event.getView().getPlayer().hasPermission("MusicBoxes.craft")) event.getInventory().setResult(new ItemStack(Material.AIR));
    }

    @EventHandler
    public void rename(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.ANVIL && event.getClickedInventory().getItem(0) != null && event.getClickedInventory().getItem(0).isSimilar(CustomCrafting.musicBox) && event.getSlot() == 2) event.setCancelled(true);
    }

}