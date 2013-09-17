package de.mineformers.timetravel.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.tileentity.TileTT;
import de.mineformers.timetravel.tileentity.TileTimeMachine;
import de.mineformers.timetravel.travelling.timemachine.TMPartPanel;

/**
 * TimeTravel
 * 
 * PacketTMPanelUpdate
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PacketTMPanelUpdate extends PacketTimeMachineUpdate {

	public int baseX, baseY, baseZ;
	public int countdown;
	public boolean doCountdown;

	public PacketTMPanelUpdate() {

	}

	public PacketTMPanelUpdate(int x, int y, int z, ForgeDirection orientation,
	        byte state, String customName, boolean valid, int type, int baseX,
	        int baseY, int baseZ, int countdown, boolean doCountdown) {
		super(x, y, z, orientation, state, customName, valid, type);
		this.baseX = baseX;
		this.baseY = baseY;
		this.baseZ = baseZ;
		this.countdown = countdown;
		this.doCountdown = doCountdown;
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		super.write(out);
		
		out.writeInt(baseX);
		out.writeInt(baseY);
		out.writeInt(baseZ);
		out.writeInt(countdown);
		out.writeBoolean(doCountdown);
	}

	@Override
	public void read(ByteArrayDataInput in) {
		super.read(in);
		
		this.baseX = in.readInt();
		this.baseY = in.readInt();
		this.baseZ = in.readInt();
		this.countdown = in.readInt();
		this.doCountdown = in.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player, Side side) {
		super.execute(player, side);
		if (side.isClient()) {
			TileEntity te = player.worldObj.getBlockTileEntity(x, y, z);
			if (te instanceof TileTT) {
				TMPartPanel panel = (TMPartPanel) ((TileTimeMachine) te)
				        .getPart();
				
				panel.setBasePosition(baseX, baseY, baseZ);
				panel.setCountdown(countdown);
				panel.setCountingDown(doCountdown);
			}
		}
	}
}
