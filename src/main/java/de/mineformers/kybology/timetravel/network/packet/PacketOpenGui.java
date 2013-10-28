package de.mineformers.kybology.timetravel.network.packet;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import de.mineformers.kybology.KybologyTimeTravel;
import de.mineformers.kybology.core.network.packet.BasePacket;

/**
 * Kybology
 * 
 * PacketOpenGui
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PacketOpenGui extends BasePacket {

    public int guiId;
    public int x, y, z;

    public PacketOpenGui() {

    }

    public PacketOpenGui(int id, int x, int y, int z) {
        this.guiId = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void write(ByteArrayDataOutput out) {
        out.writeInt(guiId);
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }

    @Override
    public void read(ByteArrayDataInput in) {
        guiId = in.readInt();
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    @Override
    public void execute(EntityPlayer player, Side side) {
        if (side.isClient())
            FMLCommonHandler.instance().showGuiScreen(
                    KybologyTimeTravel.proxy.getClientGuiElement(guiId, player,
                            player.worldObj, x, y, z));
    }

}
