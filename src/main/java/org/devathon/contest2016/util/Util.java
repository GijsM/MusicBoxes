package org.devathon.contest2016.util;

import org.bukkit.Location;

/**
 * Created by Gijs on 5-11-2016.
 */
public class Util {
    public static boolean isClose(Location location1, Location location2, int distance) {
        if (location1.getWorld() != location2.getWorld()) return false;
        int rSquared = (int) Math.pow(distance,2);
        int actualdistance = (int) (Math.pow(location1.getBlockX() - location2.getBlockX(), 2) + Math.pow(location1.getBlockZ() - location2.getBlockZ(), 2));
        return actualdistance < rSquared;
    }

}
