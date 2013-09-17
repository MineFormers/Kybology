package de.mineformers.timetravel.travelling.timemachine;

import java.util.List;

import cpw.mods.fml.common.network.PacketDispatcher;
import de.mineformers.timetravel.lib.DimIds;
import de.mineformers.timetravel.lib.Sounds;
import de.mineformers.timetravel.network.packet.PacketTMPanelUpdate;
import de.mineformers.timetravel.world.TeleporterTest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

/**
 * TimeTravel
 * 
 * TMPartPanel
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TMPartPanel extends TimeMachinePart {

	private Vec3 basePosition;
	private boolean doCountdown;
	private int countdown;

	public TMPartPanel(TileEntity parent) {
		super(TimeMachinePart.TYPE_PANEL, parent);
		this.doCountdown = false;
		this.countdown = 10;
	}

	public Vec3 getBasePosition() {
		return basePosition;
	}

	public void setBasePosition(int x, int y, int z) {
		this.basePosition = Vec3.createVectorHelper(x, y, z);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateTick() {
		super.updateTick();

		if (this.isValidMultiblock()) {
			if (doCountdown) {
				if (countdown == 0) {
					PacketDispatcher.sendPacketToAllAround(basePosition.xCoord,
					        basePosition.yCoord, basePosition.zCoord, 32,
					        this.getWorld().provider.dimensionId,
					        this.getPacket());
				}
				if (this.getTick() == 20) {
					int minX = (int) (basePosition.xCoord - 4);
					int maxX = (int) (basePosition.xCoord + 4);
					int minY = (int) basePosition.yCoord;
					int maxY = (int) (basePosition.yCoord + 2);
					int minZ = (int) (basePosition.zCoord - 4);
					int maxZ = (int) (basePosition.zCoord + 4);
					List<EntityPlayer> players = this.getWorld()
					        .getEntitiesWithinAABB(
					                EntityPlayer.class,
					                AxisAlignedBB.getBoundingBox(minX, minY,
					                        minZ, maxX, maxY, maxZ));
					if (countdown > 0) {
						this.getWorld().playSoundEffect(basePosition.xCoord,
						        basePosition.yCoord, basePosition.zCoord,
						        Sounds.countdown[countdown - 1], 1, 1);
					}
					for (EntityPlayer player : players
					        .toArray(new EntityPlayer[players.size()])) {
						if (countdown == 0)
							player.addChatMessage("Go!");
						else if (countdown > 0) {
							player.addChatMessage(countdown + "...");
						}
					}
				}

				if (countdown < -1) {
					doCountdown = false;
					countdown = 10;
					double minX = basePosition.xCoord - 0.5D;
					double maxX = basePosition.xCoord + 1.5D;
					double minY = basePosition.yCoord;
					double maxY = basePosition.yCoord + 2;
					double minZ = basePosition.zCoord - 0.5D;
					double maxZ = basePosition.zCoord + 1.5D;
					List<EntityPlayer> players = this.getWorld()
					        .getEntitiesWithinAABB(
					                EntityPlayer.class,
					                AxisAlignedBB.getBoundingBox(minX, minY,
					                        minZ, maxX, maxY, maxZ));
					for (EntityPlayer player : players
					        .toArray(new EntityPlayer[players.size()])) {
						teleport(player);
					}
					PacketDispatcher.sendPacketToAllAround(basePosition.xCoord,
					        basePosition.yCoord, basePosition.zCoord, 32,
					        this.getWorld().provider.dimensionId,
					        this.getPacket());
					return;
				}

				if (this.getTick() == 20)
					countdown--;
			}
		}

		if (this.getTick() >= 20)
			this.resetTick();
	}

	private void teleport(EntityPlayer player) {

		for (ItemStack item : player.inventory.mainInventory) {
			if (item != null)
				if (item.itemID == 5230) {
					NBTTagCompound nbt = item.getTagCompound();
					if (nbt == null) {
						nbt = new NBTTagCompound();
						item.setTagCompound(nbt);
					}

					nbt.setIntArray("Coordinates", new int[] {
					        (int) basePosition.xCoord,
					        (int) basePosition.yCoord,
					        (int) basePosition.zCoord, 1 });
				}
		}

		if ((player.ridingEntity == null) && (player.riddenByEntity == null)
		        && ((player instanceof EntityPlayerMP))) {
			EntityPlayerMP thePlayer = (EntityPlayerMP) player;
			if (thePlayer.timeUntilPortal > 0) {
				thePlayer.timeUntilPortal = 10;
			} else {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer
				        .getConfigurationManager()
				        .transferPlayerToDimension(
				                thePlayer,
				                DimIds.REDWOOD,
				                new TeleporterTest(
				                        thePlayer.mcServer
				                                .worldServerForDimension(DimIds.REDWOOD)));
			}
		}
	}

	public void startCountdown() {
		this.doCountdown = true;
	}

	public int getCountdown() {
		return countdown;
	}

	public void setCountdown(int countdown) {
		this.countdown = countdown;
	}

	public void setCountingDown(boolean doCountdown) {
		this.doCountdown = doCountdown;
	}

	public boolean isCountingDown() {
		return doCountdown;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setIntArray("BasePosition", new int[] { (int) basePosition.xCoord,
		        (int) basePosition.yCoord, (int) basePosition.zCoord });
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		int[] basePos = nbt.getIntArray("BasePosition");
		this.setBasePosition(basePos[0], basePos[1], basePos[2]);
	}

	@Override
	public Packet getPacket() {
		int baseX = (basePosition != null) ? (int) basePosition.xCoord : 0;
		int baseY = (basePosition != null) ? (int) basePosition.yCoord : 0;
		int baseZ = (basePosition != null) ? (int) basePosition.zCoord : 0;

		return new PacketTMPanelUpdate((int) this.getPos().xCoord,
		        (int) this.getPos().yCoord, (int) this.getPos().zCoord,
		        this.getOrientation(), this.getState(), this.getCustomName(),
		        this.isValidMultiblock(), this.getTypeMeta(), baseX, baseY,
		        baseZ, countdown, doCountdown).makePacket();
	}

}
