package de.mineformers.kybology.timetravel.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.kybology.core.tileentity.TileKybology;
import de.mineformers.kybology.timetravel.network.packet.PacketTimeMachineUpdate;
import de.mineformers.kybology.timetravel.travelling.timemachine.TimeMachinePart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;

/**
 * Kybology
 * 
 * TileTimeMachine
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TileTimeMachine extends TileKybology {

	private int meta;
	private TimeMachinePart part;

	public TileTimeMachine() {
		super();
		this.meta = TimeMachinePart.TYPE_BASE;
	}

	public TileTimeMachine(int type) {
		super();
		this.meta = type;
	}

	@Override
	public void validate() {
		super.validate();
		if (part == null)
			this.part = TimeMachinePart.getFromMeta(meta, this);
		else {
			this.part.initFromTile(this);
		}
	}

	public void setTypeMeta(int meta) {
		this.meta = meta;
	}

	public int getTypeMeta() {
		return meta;
	}

	public TimeMachinePart getPart() {
		return part;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!worldObj.isRemote) {
			if (part != null)
				part.updateTick();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		nbtTagCompound.setInteger("MachinePart", meta);
		if (part != null)
			part.writeToNBT(nbtTagCompound);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		meta = nbtTagCompound.getInteger("MachinePart");
		if (part == null)
			part = TimeMachinePart.getFromMeta(meta, this);
		part.readFromNBT(nbtTagCompound);
	}

	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord,
		        xCoord + 1, yCoord + 2, zCoord + 1);
	}

	@Override
	public Packet getDescriptionPacket() {
		PacketTimeMachineUpdate packet = part.getPacket();

		if (packet != null) {
			packet.customName = customName;
			packet.orientation = (byte) this.orientation.ordinal();
			packet.state = state;
			return packet.makePacket();
		}

		return new PacketTimeMachineUpdate(xCoord, yCoord, zCoord, orientation,
		        state, customName, part.isValidMultiblock(), meta).makePacket();
	}

}
