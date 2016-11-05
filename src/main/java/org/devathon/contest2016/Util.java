package org.devathon.contest2016;

import org.bukkit.Location;

/**
 * Created by Gijs on 5-11-2016.
 */
public class Util {
    public static boolean isClose(Location location1, Location location2, int distance) {
        int r = distance^2;
        return r > Math.abs(location1.getBlockX()-location2.getBlockX())*Math.abs(location1.getBlockZ()-location2.getBlockZ());
    }

}
