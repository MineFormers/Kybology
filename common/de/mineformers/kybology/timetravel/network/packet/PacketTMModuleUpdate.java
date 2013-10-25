package de.mineformers.kybology.timetravel.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import de.mineformers.kybology.core.tileentity.TileKybology;
import de.mineformers.kybology.timetravel.tileentity.TileTimeMachine;
import de.mineformers.kybology.timetravel.travelling.timemachine.TMPartModule;
import de.mineformers.kybology.timetravel.travelling.timemachine.TMPartModule.ModuleType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * Kybology
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
			if (te instanceof TileKybology) {
				TMPartModule module = (TMPartModule) ((TileTimeMachine) te)
				        .getPart();
				
				module.setType(ModuleType.values()[moduleType]);
			}
		}
	}

}
