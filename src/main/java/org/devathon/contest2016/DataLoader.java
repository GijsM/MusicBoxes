package org.devathon.contest2016;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.musicbox.MusicBox;
import org.devathon.contest2016.musicbox.MusicBoxData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gijs on 5-11-2016.
 */
public class DataLoader {

    static File dataFolder;
    static File locationsFile;
    public static Map<Block, Integer> dataMap = new HashMap<>();
    static Set<MusicBox> musicBoxes = ConcurrentHashMap.newKeySet();
    static LocationList list;

    public static void init(JavaPlugin plugin) {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (MusicBox box : musicBoxes) {
                if (!box.block.getChunk().isLoaded()) Bukkit.getLogger().info("boyy");
                box.tick();
            }
        },0,1);

        dataFolder = plugin.getDataFolder();
        locationsFile = new File(dataFolder.getPath() + "/locations.json");
        dataFolder.mkdir();
        try {
            locationsFile.createNewFile();
            list = new Gson().fromJson(FileUtils.readFileToString(locationsFile), LocationList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list == null) {
            list = new LocationList();
            list.locations = new ArrayList<>();
            list.current = 0;
        }
        for (BlockData data : list.locations) {
            Block block = Bukkit.getWorld(data.world).getBlockAt(data.x, data.y, data.z);
            dataMap.put(block, data.id);
        }
    }

    public static MusicBoxData load(int id) throws IOException {
        return new Gson().fromJson(FileUtils.readFileToString(new File(dataFolder.getPath() + "/" + id + ".json")), MusicBoxData.class);
    }

    public static MusicBox createBox(Block block) {
        if (dataMap.containsKey(block)) return null;
        MusicBox box = new MusicBox(block, new MusicBoxData());
        int id = list.current++;
        dataMap.put(block, id);
        list.locations.add(new BlockData(block.getWorld().getName(), block.getX(), block.getY(), block.getZ(), id));
        try {
            saveBox(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadBox(block);
    }

    public static void saveBox(MusicBox box) throws IOException {

        File saveFile = new File(dataFolder.getPath() + "/" + dataMap.get(box.block) + ".json");
        if (!saveFile.exists()) saveFile.createNewFile();
        FileUtils.writeStringToFile(saveFile, new Gson().toJson(box.data));
    }



    public static void saveLocations() throws IOException {
        FileUtils.writeStringToFile(locationsFile, new Gson().toJson(list));
    }

    public static MusicBox loadBox(Block block) {
        for (MusicBox box : musicBoxes) if (box.block.equals(block)) return  box;
        MusicBox box = null;
        try {
            box = new MusicBox(block, load(dataMap.get(block)));
            musicBoxes.add(box);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return box;
    }

    public static void unLoadBox(MusicBox box) {
        musicBoxes.remove(box);
    }


}
