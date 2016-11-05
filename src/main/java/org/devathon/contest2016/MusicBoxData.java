package org.devathon.contest2016;

import com.google.gson.Gson;
import org.bukkit.Instrument;

/**
 * Created by Gijs on 5-11-2016.
 */
public class MusicBoxData {
    int speed;
    byte[][] data;

    public static MusicBoxData load(String json) {
        return new Gson().fromJson(json, MusicBoxData.class);
    }

    public MusicBoxData() {
        speed = 2;
        data = new byte[5][8];
    }

    public String save() {
        return new Gson().toJson(this);
    }
}
