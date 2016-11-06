package org.devathon.contest2016.util;

import org.bukkit.Location;

/**
 * Created by Gijs on 5-11-2016.
 */
public class Util {
    public static boolean isClose(Location location1, Location location2, int distance) {
        if (location1.getWorld() != location2.getWorld()) return false;
        int r = distance^2;
        return r > Math.abs(location1.getBlockX()-location2.getBlockX())*Math.abs(location1.getBlockZ()-location2.getBlockZ());
    }

}
