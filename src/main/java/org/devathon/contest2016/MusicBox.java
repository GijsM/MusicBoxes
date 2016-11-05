package org.devathon.contest2016;

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
    public Set<Player> players = new HashSet();
    public Block block;
    public MusicBoxData data;

    public MusicBox(Block block, MusicBoxData data) {
        this.block = block;
        this.data = data;
    }

    public void reload() {

    }

    public void load() {

    }

    public void unload() {

    }
    private int position = 0;
    private int ticked = 0;

    public void tick() {
        if (++ticked >= data.speed) {
            play((byte) 0b0001_1001, Instrument.PIANO);
            ticked = 0;
        } else {
            return;
        }
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
