package de.mineformers.timetravel.travelling.timemachine;

import de.mineformers.timetravel.network.packet.PacketTimeMachineUpdate;
import de.mineformers.timetravel.tileentity.TileTimeMachine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * TimeTravel
 * 
 * TimeMachinePart
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public abstract class TimeMachinePart {

	public static final int TYPE_BASE = 0;
	public static final int TYPE_PANEL = 1;
	public static final int TYPE_STAIRS = 2;
	public static final int TYPE_PILLAR = 3;
	public static final int TYPE_MODULE = 4;

	public static TimeMachinePart getFromMeta(int meta, TileEntity parent) {
		switch (meta) {
			case TYPE_BASE:
				return new TMPartBase(parent);
			case TYPE_PANEL:
				return new TMPartPanel(parent);
			case TYPE_STAIRS:
				return new TimeMachinePart(meta, parent) {

					@Override
					public PacketTimeMachineUpdate getPacket() {
						return null;
					}

				};
			case TYPE_PILLAR:
				return new TimeMachinePart(meta, parent) {

					@Override
					public PacketTimeMachineUpdate getPacket() {
						return null;
					}

				};
			case TYPE_MODULE:
				return new TMPartModule(parent);
			default:
				return new TMPartBase(parent);
		}
	}

	private World world;
	private Vec3 position;
	private int meta;
	private int tick;
	private boolean valid;

	public TimeMachinePart(int type, TileEntity parent) {
		this.meta = type;
		this.world = parent.worldObj;
		this.position = Vec3.createVectorHelper(parent.xCoord, parent.yCoord,
		        parent.zCoord);
		this.valid = false;
	}
	
	public void initFromTile(TileEntity parent) {
		this.world = parent.worldObj;
		this.position = Vec3.createVectorHelper(parent.xCoord, parent.yCoord,
		        parent.zCoord);
	}

	public World getWorld() {
		return world;
	}

	public Vec3 getPos() {
		return position;
	}

	public void setValidMultiblock(boolean valid) {
		this.valid = valid;
	}

	public boolean isValidMultiblock() {
		return valid;
	}

	public void invalidateMultiblock() {
		if (!world.isRemote) {
			int xCoord = (int) position.xCoord;
			int yCoord = (int) position.yCoord;
			int zCoord = (int) position.zCoord;
			for (int xOff = -1; xOff <= 1; xOff++) {
				for (int zOff = -1; zOff <= 1; zOff++) {
					if (world.getBlockTileEntity(xCoord + xOff, yCoord, zCoord
					        + zOff) != null) {
						if (world.getBlockTileEntity(xCoord + xOff, yCoord,
						        zCoord + zOff) instanceof TileTimeMachine) {
							TileTimeMachine tile = (TileTimeMachine) world
							        .getBlockTileEntity(xCoord + xOff, yCoord,
							                zCoord + zOff);
							if (tile.getTypeMeta() == TYPE_BASE) {
								TMPartBase base = (TMPartBase) tile.getPart();
								base.invalidateMultiblock();
								return;
							}
						}
					}
				}
			}
		}
	}

	public void updateTick() {
		tick++;
		if (tick == 100)
			resetTick();
	}

	public int getTypeMeta() {
		return meta;
	}

	protected int getTick() {
		return tick;
	}

	protected void resetTick() {
		tick = 0;
	}

	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		nbtTagCompound.setBoolean("IsValidMultiblock", valid);
	}

	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		valid = nbtTagCompound.getBoolean("IsValidMultiblock");
	}

	public abstract PacketTimeMachineUpdate getPacket();

}
