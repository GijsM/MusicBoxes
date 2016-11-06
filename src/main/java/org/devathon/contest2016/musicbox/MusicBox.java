package org.devathon.contest2016.musicbox;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
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
        size = data.data[0].length;
    }
    private int size;
    private int position = 0;
    private int ticked = 0;

    public void tick() {
        if (!data.on) return;
        if (++ticked >= data.speed) {
            ticked = 0;
            for (int i = 0;i<5;i++) {
                play(data.data[i][position], Instrument.values()[i]);
            }
            updatePosition();
            if (++position >= size) position = 0;
        } else {
            return;
        }
    }

    public void openInventory(Player player) {
        if (!editing) menu = new MusicBoxMenu(player, this);

    }

    public void updatePosition() {
        menu.updatePosition(position);

    }

    public void play(byte b, Instrument instrument) {
        for (Player player : players) {
            if ((0b0000_0001&b) != 0) player.playNote(block.getLocation(), instrument, new Note(0, Note.Tone.A, false));
            if ((0b0000_0010&b) != 0) player.playNote(block.getLocation(), instrument, new Note(0, Note.Tone.B, false));
            if ((0b0000_0100&b) != 0) player.playNote(block.getLocation(), instrument, new Note(0, Note.Tone.C, false));
            if ((0b0000_1000&b) != 0) player.playNote(block.getLocation(), instrument, new Note(0, Note.Tone.D, false));
            if ((0b0001_0000&b) != 0) player.playNote(block.getLocation(), instrument, new Note(0, Note.Tone.E, false));
            if ((0b0010_0000&b) != 0) player.playNote(block.getLocation(), instrument, new Note(0, Note.Tone.F, false));
            if ((0b0100_0000&b) != 0) player.playNote(block.getLocation(), instrument, new Note(0, Note.Tone.G, false));
        }

    }
}
