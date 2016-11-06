package org.devathon.contest2016;


import java.io.File;
import java.util.List;

/**
 * Created by Gijs on 5-11-2016.
 */
public class LocationList {
    public static File folder;
    public int current;
    public List<BlockData> locations;


} class BlockData {

    int x;
    int y;
    int z;
    int id;
    String world;

    public BlockData(String world, int x, int y, int z, int id) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = id;
    }

}
