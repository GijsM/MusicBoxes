package org.devathon.contest2016.musicbox;

import org.devathon.contest2016.util.BlockData;

import java.util.Arrays;

/**
 * Created by Gijs on 5-11-2016.
 */
public class MusicBoxData {
    public boolean on;
    public int speed;
    public byte[][] data;
    public BlockData blockData;


    public void setSize(int newsize) {
        byte[][] newdata = new byte[5][newsize];
        int i = 0;
        for (byte[] bytes : data) {
            newdata[i] = Arrays.copyOf(data[i++], newsize);
        }
        data = newdata;
    }

    public void save() {
        blockData.save(this);
    }

    public MusicBoxData(boolean on, int speed, byte[][] data, BlockData blockData) {
        this.blockData = blockData;
        this.on = on;
        this.speed = speed;
        this.data = data;
    }
}
