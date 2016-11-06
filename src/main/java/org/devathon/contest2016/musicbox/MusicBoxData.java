package org.devathon.contest2016.musicbox;

import com.google.gson.Gson;

import java.util.Arrays;

/**
 * Created by Gijs on 5-11-2016.
 */
public class MusicBoxData {
    boolean on;
    int speed;
    byte[][] data;

    public static MusicBoxData load(String json) {
        return new Gson().fromJson(json, MusicBoxData.class);
    }

    public MusicBoxData() {
        on = false;
        speed = 2;
        data = new byte[5][7];
    }

    public void setSize(int newsize) {
        byte[][] newdata = new byte[5][newsize];
        int i = 0;
        for (byte[] bytes : data) {
            newdata[i] = Arrays.copyOf(data[i++], newsize);
        }
    }

    public String save() {
        return new Gson().toJson(this);
    }
}
