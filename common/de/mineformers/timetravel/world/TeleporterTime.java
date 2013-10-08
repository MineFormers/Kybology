package de.mineformers.timetravel.world;

import de.mineformers.timetravel.entity.PlayerPropertiesTT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * TimeTravel
 * 
 * TeleporterTime
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TeleporterTime extends Teleporter {

	private WorldServer world;

	public TeleporterTime(WorldServer worldServer) {
		super(worldServer);
		this.world = worldServer;
	}

	public void placeInPortal(Entity par1Entity, double par2, double par4,
	        double par6, float par8) {
		double x = MathHelper.floor_double(par1Entity.posX);
		double y = MathHelper.floor_double(par1Entity.posY) - 1;
		double z = MathHelper.floor_double(par1Entity.posZ);

		EntityPlayer player = (EntityPlayer) par1Entity;

		PlayerPropertiesTT props = PlayerPropertiesTT.getByEntity(player);

		if (props.getTmDimension() == world.provider.dimensionId) {
			x = props.getTmCoordinates().x + 0.5F;
			y = props.getTmCoordinates().y + 0.5F;
			z = props.getTmCoordinates().z + 0.5F;
		}

		while (world.getBlockId((int) x, (int) y, (int) z) != 0
		        || world.getBlockId((int) x, (int) y + 1, (int) z) != 0) {
			y += 1;
		}

		par1Entity.setLocationAndAngles(x, y + 1, z, par1Entity.rotationYaw,
		        0.0F);
		par1Entity.motionX = par1Entity.motionY = par1Entity.motionZ = 0.0D;
	}
}
