package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.listeners.*;
import org.devathon.contest2016.musicbox.MusicBoxMenu;
import org.devathon.contest2016.util.CustomCrafting;
import org.devathon.contest2016.util.LanguageUtils;

import java.io.IOException;


public class MusicBoxPlugin extends JavaPlugin implements Listener {

    public static Plugin plugin;
    public static String pluginPrefix = ChatColor.AQUA + "[" + ChatColor.YELLOW + "MusicBoxes" + ChatColor.AQUA + "] ";


    @Override
    public void onEnable() {
        register(new BlockRedstoneListener(), new DataLoaderListener(), new PlayerInteractListener(), new BlockRemoveListener(), new BlockPlaceListener(), new CraftItemListener());
        LanguageUtils.load(this);
        CustomCrafting.addRecipe(Bukkit.getServer());
        DataLoader.init(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            DataLoaderListener.checkPlayer(player);
        }
        plugin = this;
    }

    public void register(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("MusicBoxes")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("give")) {
                    if (!sender.hasPermission("MusicBoxes.give")) return true;
                    if (args.length == 2) {
                        Player player = Bukkit.getPlayer(args[1]);
                        if (player == null) {
                            sender.sendMessage(pluginPrefix + ChatColor.RED + "Player not online");
                            return false;
                        } else {
                            player.getInventory().addItem(CustomCrafting.musicBox);
                            sender.sendMessage(pluginPrefix + ChatColor.GREEN + "Gave " + player.getName() + " a MusicBox!");
                        }
                    } else {
                        if (sender instanceof Player) {
                            ((Player) sender).getInventory().addItem(CustomCrafting.musicBox);
                            sender.sendMessage(pluginPrefix + ChatColor.GREEN + "Here ya go");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (!sender.hasPermission("MusicBoxes.reload")) return true;
                    LanguageUtils.load(this);
                    sender.sendMessage(pluginPrefix + ChatColor.GREEN + "Reloaded");
                }
            } else {
                sender.sendMessage(pluginPrefix + ChatColor.GREEN + "Try /MusicBoxes give <player>");
            }
        }
        return true;
    }

    @Override
    public void onDisable() {
        try {
            DataLoader.saveLocations();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (MusicBoxMenu menu : MusicBoxMenu.openMenus) menu.close(false);
    }

}

