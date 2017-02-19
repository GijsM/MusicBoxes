package org.devathon.contest2016.util;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

/**
 * Created by Gijs on 25-1-2017.
 */
public class LanguageUtils {

    public static String displayname;
    public static String lore;
    public static String bassdrum;
    public static String bassguitar;
    public static String snaredrum;
    public static String sticks;
    public static String piano;
    public static String clickeditinstrument;
    public static String clicktoggle;
    public static String musicboxon;
    public static String musicboxoff;
    public static String speed;
    public static String leftrighttochange;
    public static String clickaddnote;
    public static String clickremovenote;
    public static String clickgoback;
    public static String backtomain;
    public static String backonepage;
    public static String createnextpage;
    public static String clicktonextpage;
    public static String gotonextpage;
    public static String clickgotonextpage;
    public static String volume;

    public static void load(Plugin plugin) {
        plugin.reloadConfig();
        Configuration configuration = plugin.getConfig();
        configuration.addDefault("ItemDisplayname", "Music Box");
        configuration.addDefault("ItemLore", "Place down at a nice spot!");
        configuration.addDefault("BassDrum", "Bass Drum");
        configuration.addDefault("BassGuitar", "Bass Guitar");
        configuration.addDefault("SnareDrum", "Snare Drum");
        configuration.addDefault("Sticks", "Sticks");
        configuration.addDefault("Piano", "Piano");
        configuration.addDefault("EditInstrument", "Click to edit this instrument");
        configuration.addDefault("ClickToToggle", "Click to toggle it");
        configuration.addDefault("LeftRightChange", "Left and Right click to change");
        configuration.addDefault("MusicBoxOn", "MusicBox: On");
        configuration.addDefault("MusicBoxOff", "MusicBox: Off");
        configuration.addDefault("Speed", "Speed: ");
        configuration.addDefault("AddNote", "Click to add a note");
        configuration.addDefault("RemoveNote", "Click to remove this note");
        configuration.addDefault("BackToMenu", "Back to the Main Menu");
        configuration.addDefault("ClickToBack", "Click to go back");
        configuration.addDefault("CreateNextPage", "Create next page");
        configuration.addDefault("ClickCreateNextPage", "Click to create the next page");
        configuration.addDefault("ClickGoNextPage", "Click to go to the next page");
        configuration.addDefault("GoNextPage", "Go to the next page");
        configuration.addDefault("GoBackPage", "Go one page back");
        configuration.addDefault("Volume", "Volume: ");
        configuration.options().copyDefaults(true);
        plugin.saveConfig();
        displayname = configuration.getString("ItemDisplayname");
        lore = configuration.getString("ItemLore");
        bassdrum = configuration.getString("BassDrum");
        bassguitar = configuration.getString("BassGuitar");
        snaredrum = configuration.getString("SnareDrum");
        sticks = configuration.getString("Sticks");
        piano = configuration.getString("Piano");
        clickeditinstrument = configuration.getString("EditInstrument");
        clicktoggle = configuration.getString("ClickToToggle");
        musicboxon = configuration.getString("MusicBoxOn");
        musicboxoff = configuration.getString("MusicBoxOff");
        speed = configuration.getString("Speed");
        volume = configuration.getString("Volume");
        leftrighttochange = configuration.getString("LeftRightChange");
        clickaddnote = configuration.getString("AddNote");
        clickremovenote = configuration.getString("RemoveNote");
        clickgoback = configuration.getString("ClickToBack");
        backtomain = configuration.getString("BackToMenu");
        backonepage = configuration.getString("GoBackPage");
        createnextpage = configuration.getString("CreateNextPage");
        clickgotonextpage = configuration.getString("ClickGoNextPage");
        gotonextpage = configuration.getString("GoNextPage");
        clicktonextpage = configuration.getString("ClickGoNextPage");
    }

}
