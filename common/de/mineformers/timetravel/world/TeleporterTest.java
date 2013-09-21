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
 * TeleporterTest
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TeleporterTest extends Teleporter {

	private WorldServer world;

	public TeleporterTest(WorldServer worldServer) {
		super(worldServer);
		this.world = worldServer;
	}

	public void placeInPortal(Entity par1Entity, double par2, double par4,
	        double par6, float par8) {
		double x = MathHelper.floor_double(par1Entity.posX);
		double y = MathHelper.floor_double(par1Entity.posY) - 1;
		double z = MathHelper.floor_double(par1Entity.posZ);

		EntityPlayer player = (EntityPlayer) par1Entity;
		/*
		 * if (par1Entity instanceof EntityPlayer) {
		 * 
		 * if (TravellingRegistry
		 * .isValidTimeDestination(player.worldObj.provider.dimensionId)) if
		 * (player.getHeldItem() != null && player.getHeldItem().itemID == 5230)
		 * { ItemStack stopWatch = player.getHeldItem(); NBTTagCompound nbt =
		 * stopWatch.getTagCompound(); if (nbt == null) { nbt = new
		 * NBTTagCompound(); stopWatch.setTagCompound(nbt); } else { int[]
		 * coords = nbt.getIntArray("Coordinates"); boolean inDim = coords[3] ==
		 * 1; if (inDim) { x = coords[0] + 0.5D; y = coords[1] + 1; z =
		 * coords[2] + 0.5D;
		 * 
		 * coords[3] = 0; nbt.setIntArray("Coordinates", coords);
		 * 
		 * for (ItemStack item : player.inventory.mainInventory) { if (item !=
		 * null) if (item.itemID == 5230) { item.setTagCompound(nbt); } } } } }
		 * }
		 */

		PlayerPropertiesTT props = PlayerPropertiesTT.getByEntity(player);

		if (props.getTmDimension() == world.provider.dimensionId) {
			x = props.getTmCoordinates().xCoord + 0.5F;
			y = props.getTmCoordinates().yCoord + 0.5F;
			z = props.getTmCoordinates().zCoord + 0.5F;
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
