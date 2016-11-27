package org.devathon.contest2016.musicbox;

import org.bukkit.Effect;
import org.bukkit.Instrument;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gijs on 5-11-2016.
 */
public class MusicBox  {
    boolean editing = false;
    public Set<Player> players = new HashSet();
    public Block block;
    public MusicBoxData data;
    public MusicBoxMenu menu;

    public MusicBox(Block block, MusicBoxData data) {
        this.block = block;
        this.data = data;
    }
    private int position = 0;
    private int ticked = 0;

    public void tick() {
        if (!data.on) return;
        if (++ticked >= data.speed) {
            ticked = 0;
            boolean note = false;
            if (position >= data.data[0].length) position = 0;
            for (int i = 0;i<5;i++) {
                play(data.data[i][position], Instrument.values()[i]);
                if (data.data[i][position] != 0) note = true;
            }
            if (note) block.getWorld().playEffect(block.getLocation().add(0.5,1,0.5), Effect.NOTE, 1, 16);
            updatePosition();
            if (++position >= data.data[0].length) position = 0;
        } else {
            return;
        }
    }

    public void openInventory(Player player) {
        if (!editing) menu = new MusicBoxMenu(player, this);
    }

    public void updatePosition() {
        if (menu != null)
            menu.updatePosition(position);

    }

    public void play(byte b, Instrument instrument) {
        if ((0b0000_0001&b) != 0) block.getWorld().playSound(block.getLocation(), getSound(instrument), 1F, 0.6F);
        if ((0b0000_0010&b) != 0) block.getWorld().playSound(block.getLocation(), getSound(instrument), 1F, 2/3F);
        if ((0b0000_0100&b) != 0) block.getWorld().playSound(block.getLocation(), getSound(instrument), 1F, 0.7F);
        if ((0b0000_1000&b) != 0) block.getWorld().playSound(block.getLocation(), getSound(instrument), 1F, 0.8F);
        if ((0b0001_0000&b) != 0) block.getWorld().playSound(block.getLocation(), getSound(instrument), 1F, 0.9F);
        if ((0b0010_0000&b) != 0) block.getWorld().playSound(block.getLocation(), getSound(instrument), 1F, 0.95F);
        if ((0b0100_0000&b) != 0) block.getWorld().playSound(block.getLocation(), getSound(instrument), 1F, 1.0F);
    }

    Sound getSound(Instrument instrument) {
        switch (instrument) {
            case BASS_DRUM: return Sound.BLOCK_NOTE_BASEDRUM;
            case BASS_GUITAR: return Sound.BLOCK_NOTE_BASS;
            case PIANO: return Sound.BLOCK_NOTE_HARP;
            case SNARE_DRUM: return Sound.BLOCK_NOTE_SNARE;
            case STICKS: return Sound.BLOCK_NOTE_HAT;
        }
        return null;
    }
}
