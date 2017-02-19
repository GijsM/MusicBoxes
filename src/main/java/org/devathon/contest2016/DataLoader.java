package org.devathon.contest2016;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.musicbox.MusicBox;
import org.devathon.contest2016.musicbox.MusicBoxData;
import org.devathon.contest2016.util.BlockData;
import org.devathon.contest2016.util.Compression;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gijs on 5-11-2016.
 */
public class DataLoader {

    static File locationsFile;
    public static Map<Block, MusicBoxData> dataMap = new ConcurrentHashMap<>();
    static Set<MusicBox> musicBoxes = ConcurrentHashMap.newKeySet();
    static LocationList list;

    public static void init(JavaPlugin plugin) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (MusicBox box : musicBoxes) {
                box.tick();
            }
        },0,1);

        File dataFolder = plugin.getDataFolder();
        dataFolder.mkdir();
        locationsFile = new File(dataFolder.getPath() + "/locations.json");
        if (locationsFile.exists()) {
            try {
                list = new Gson().fromJson(FileUtils.readFileToString(locationsFile), LocationList.class);
                locationsFile.delete();
                locationsFile = new File(dataFolder.getPath() + "/locations.data");
                locationsFile.createNewFile();
                saveLocations();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            locationsFile = new File(dataFolder.getPath() + "/locations.data");
            try {
                locationsFile.createNewFile();
                list = new Gson().fromJson(new String(Compression.decompress(FileUtils.readFileToByteArray(locationsFile))), LocationList.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (list == null) {
            list = new LocationList();
            list.locations = new ArrayList<>();
            list.current = 0;
        }
        for (BlockData data : list.locations) {
            Block block = Bukkit.getWorld(data.world).getBlockAt(data.x, data.y, data.z);
            dataMap.put(block, data.getData());
        }
    }


    public static void removeBox(MusicBox box) {
        list.locations.remove(box.data.blockData);
        unLoadBox(box);
        dataMap.remove(box.block);
    }

    public static MusicBox createBox(Block block) {
        if (dataMap.containsKey(block)) return null;
        BlockData data = new BlockData(block.getWorld().getName(), block.getX(), block.getY(), block.getZ(), list.current++, 10);
        list.locations.add(data);
        MusicBox box = new MusicBox(block, new MusicBoxData(false, 2, new byte[5][8], data, 10));
        dataMap.put(block, box.data);
        box.data.save();
        return loadBox(block);
    }




    public static void saveLocations() throws IOException {
        FileUtils.writeByteArrayToFile(locationsFile, Compression.compress(new Gson().toJson(list).getBytes()));
    }

    public static MusicBox loadBox(Block block) {
        for (MusicBox box : musicBoxes) if (box.block.equals(block)) return box;
        MusicBox box = new MusicBox(block, dataMap.get(block));
        musicBoxes.add(box);
        return box;
    }

    public static void unLoadBox(MusicBox box) {
        musicBoxes.remove(box);
    }


}
