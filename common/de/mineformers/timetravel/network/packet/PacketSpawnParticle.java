package de.mineformers.timetravel.network.packet;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.client.entity.ModParticles;

/**
 * TimeTravel
 * 
 * PacketSpawnParticle
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PacketSpawnParticle extends BasePacket {

    public int id;
    public double x, y, z;
    public double motionX, motionY, motionZ;

    public PacketSpawnParticle() {
    }

    public PacketSpawnParticle(int id, double x, double y, double z,
            double motionX, double motionY, double motionZ) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    @Override
    public void write(ByteArrayDataOutput out) {
        out.writeInt(id);
        out.writeDouble(x);
        out.writeDouble(y);
        out.writeDouble(z);
        out.writeDouble(motionX);
        out.writeDouble(motionY);
        out.writeDouble(motionZ);
    }

    @Override
    public void read(ByteArrayDataInput in) {
        id = in.readInt();
        x = in.readDouble();
        y = in.readDouble();
        z = in.readDouble();
        motionX = in.readDouble();
        motionY = in.readDouble();
        motionZ = in.readDouble();
    }

    @Override
    public void execute(EntityPlayer player, Side side) {
        if (side.isClient()) {
            ModParticles.values()[id].spawnParticles(FMLClientHandler
                    .instance().getClient().theWorld, x, y, z, motionX,
                    motionY, motionZ);
        }
    }

}
