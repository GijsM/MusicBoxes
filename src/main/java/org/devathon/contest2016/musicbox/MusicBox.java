package org.devathon.contest2016.musicbox;

import org.bukkit.Bukkit;
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
    float[] octaves = new float[]{0.6F, 2/3F, 0.7F, 0.8F, 0.9F, 0.95F, 1.0F};

    public void play(byte b, Instrument instrument) {
        for (int i = 0;i<7;i++) {
            if (((0b0000_0001<<i)&b) != 0) block.getWorld().playSound(block.getLocation(), getSound(instrument), data.volume/10F, octaves[i]);
        }
    }

    Sound getSound(Instrument instrument) {
        switch (instrument) {
            case BASS_DRUM: return Basedrum;
            case BASS_GUITAR: return Bass;
            case PIANO: return Harp;
            case SNARE_DRUM: return Snare;
            case STICKS: return Hat;
        }
        return null;
    }

    private static boolean old = Bukkit.getVersion().contains("1.8");
    private static Sound Basedrum = (Sound) (old ? getSoundEnum("NOTE_BASS_DRUM") : getSoundEnum("BLOCK_NOTE_BASEDRUM"));
    private static Sound Bass = (Sound) (old ? getSoundEnum("NOTE_BASS") : getSoundEnum("BLOCK_NOTE_BASS"));
    private static Sound Harp = (Sound) (old ? getSoundEnum("NOTE_PIANO") : getSoundEnum("BLOCK_NOTE_HARP"));
    private static Sound Snare = (Sound) (old ? getSoundEnum("NOTE_SNARE_DRUM") : getSoundEnum("BLOCK_NOTE_SNARE"));
    private static Sound Hat = (Sound) (old ? getSoundEnum("NOTE_STICKS") : getSoundEnum("BLOCK_NOTE_HAT"));

    public static Enum getSoundEnum(String s) {
        return Enum.valueOf(Sound.class, s);
    }
}
