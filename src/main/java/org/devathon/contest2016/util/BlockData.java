package org.devathon.contest2016.util;

import org.devathon.contest2016.musicbox.MusicBoxData;

import java.util.Arrays;
import java.util.Base64;

/**
 * Created by Gijs on 24-11-2016.
 */
public class BlockData {

    public int x;
    public int y;
    public int z;
    public int id;
    public String world;
    public String data;
    public int speed;
    public boolean on;

    public BlockData(String world, int x, int y, int z, int id) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }

    public void save(MusicBoxData data) {
        int length = data.data[0].length * 5;
        byte[] byteData = new byte[length];
        int pointer = 0;
        for (byte[] bytes : data.data) {
            for (byte b : bytes) {
                byteData[pointer++] = b;
            }
        }
        this.data = Base64.getEncoder().encodeToString(Compression.compress(byteData));
        this.on = data.on;
        this.speed = data.speed;
    }

    public MusicBoxData getData() {
        byte[] dataArray = Compression.decompress(Base64.getDecoder().decode(data));
        int size = dataArray.length/5;
        byte[][] alldata = new byte[5][size];
        for (int i = 0; i<5;i++) {
            alldata[i] = Arrays.copyOfRange(dataArray, i*size, (i+1)*size);
        }
        return new MusicBoxData(on, speed, alldata, this);
    }


}
