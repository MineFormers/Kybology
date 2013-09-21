package de.mineformers.timetravel.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.tileentity.TileTT;
import de.mineformers.timetravel.tileentity.TileTimeMachine;
import de.mineformers.timetravel.travelling.timemachine.TMPartModule;
import de.mineformers.timetravel.travelling.timemachine.TMPartModule.ModuleType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * TimeTravel
 * 
 * PacketTMModuleUpdate
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PacketTMModuleUpdate extends PacketTimeMachineUpdate {

	public int moduleType;

	public PacketTMModuleUpdate() {

	}

	public PacketTMModuleUpdate(int x, int y, int z,
	        ForgeDirection orientation, byte state, String customName,
	        boolean valid, int type, int moduleType) {
		super(x, y, z, orientation, state, customName, valid, type);
		this.moduleType = moduleType;
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		super.write(out);

		out.writeInt(moduleType);
	}

	@Override
	public void read(ByteArrayDataInput in) {
		super.read(in);

		moduleType = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side) {
		super.execute(player, side);

		if (side.isClient()) {
			TileEntity te = player.worldObj.getBlockTileEntity(x, y, z);
			if (te instanceof TileTT) {
				TMPartModule module = (TMPartModule) ((TileTimeMachine) te)
				        .getPart();
				
				module.setType(ModuleType.values()[moduleType]);
			}
		}
	}

}
