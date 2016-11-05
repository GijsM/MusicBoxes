package org.devathon.contest2016;

import net.minecraft.server.v1_10_R1.NBTBase;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.TileEntity;
import org.bukkit.Instrument;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gijs on 5-11-2016.
 */
public class TileEntityMusicBox extends TileEntity {
    int speed;
    int length;
    Map<Instrument, byte[]> musicData = new HashMap<>();

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        speed = nbttagcompound.getInt("Speed");
        length = nbttagcompound.getInt("length");
        NBTTagCompound compound = nbttagcompound.getCompound("Data");
        for (String s : compound.c()) {
            Instrument instrument = Instrument.valueOf(s);
            musicData.put(instrument, compound.getByteArray(s));
        }

    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        nbttagcompound.setInt("Speed", speed);
        nbttagcompound.setInt("Length", speed);
        NBTTagCompound base = new NBTTagCompound();
        for (Map.Entry<Instrument, byte[]> entry : musicData.entrySet()) {
            base.setByteArray(entry.getKey().name(), entry.getValue());
        }
        nbttagcompound.set("Data", base);
        return nbttagcompound;
    }
}
