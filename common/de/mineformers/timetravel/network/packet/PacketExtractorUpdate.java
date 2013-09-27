package de.mineformers.timetravel.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.tileentity.TileEnergyExtractor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeDirection;

/**
 * TimeTravel
 * 
 * PacketExtractorUpdate
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PacketExtractorUpdate extends PacketTileUpdate {

	public int energy;

	public PacketExtractorUpdate() {
	}

	public PacketExtractorUpdate(int x, int y, int z,
			ForgeDirection orientation, byte state, String customName,
			int energy) {
		super(x, y, z, orientation, state, customName);
		this.energy = energy;
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		super.write(out);

		out.writeInt(energy);
	}

	@Override
	public void read(ByteArrayDataInput in) {
		super.read(in);

		energy = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side) {
		super.execute(player, side);

		if (side.isClient()) {
			if (player.worldObj.getBlockTileEntity(x, y, z) != null)
				((TileEnergyExtractor) player.worldObj.getBlockTileEntity(x, y,
						z)).setEnergy(energy);
		}
	}

}
