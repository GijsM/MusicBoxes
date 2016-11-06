package org.devathon.contest2016.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

/**
 * Created by Gijs on 5-11-2016.
 */
public class CustomCrafting {
    public static ItemStack musicBox;

    public static void addRecipe(Server server) {
        musicBox = new ItemStack(Material.ENDER_PORTAL_FRAME);
        musicBox.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        ItemMeta meta = musicBox.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Music Box");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Place down at a nice spot!"));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        musicBox.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(musicBox);
        recipe.shape("xxx","xox","xxx");
        recipe.setIngredient('x', Material.NOTE_BLOCK);
        recipe.setIngredient('o', Material.DIAMOND);
        server.addRecipe(recipe);
    }

}
