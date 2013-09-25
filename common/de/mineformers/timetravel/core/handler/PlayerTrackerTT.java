package de.mineformers.timetravel.core.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import cpw.mods.fml.common.IPlayerTracker;
import de.mineformers.timetravel.api.TravellingRegistry;
import de.mineformers.timetravel.core.util.LangHelper;
import de.mineformers.timetravel.entity.PlayerPropertiesTT;
import de.mineformers.timetravel.lib.ItemIds;
import de.mineformers.timetravel.world.TeleporterTime;

/**
 * TimeTravel
 * 
 * PlayerTrackerTT
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PlayerTrackerTT implements IPlayerTracker {

	@ForgeSubscribe
	public void onPlayerJoinWorld(EntityConstructing event) {
		if (event.entity != null)
			if (event.entity.worldObj != null)
				if (!event.entity.worldObj.isRemote)
					if (event.entity instanceof EntityPlayer) {
						EntityPlayer player = (EntityPlayer) event.entity;
						if (player
								.getExtendedProperties(PlayerPropertiesTT.IDENTIFIER) == null)
							player.registerExtendedProperties(
									PlayerPropertiesTT.IDENTIFIER,
									new PlayerPropertiesTT());
					}
	}

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		PlayerPropertiesTT props = PlayerPropertiesTT.getByEntity(player);
		if (props.getTimeStarted() > 0) {
			if ((System.currentTimeMillis() - props.getTimeStarted()) >= props
					.getSecondsAvail() * 1000) {
				props.setSecondsLeft(0);
				props.setSecondsAvail(0);
				props.setTimeStarted(0);
				player.addChatMessage(LangHelper.translate("message",
						"unknownForce"));
				if (player instanceof EntityPlayerMP) {
					EntityPlayerMP thePlayer = (EntityPlayerMP) player;
					if (thePlayer.timeUntilPortal > 0) {
						thePlayer.timeUntilPortal = 20;
					} else {
						thePlayer.timeUntilPortal = 20;
						thePlayer.mcServer.getConfigurationManager()
								.transferPlayerToDimension(
										thePlayer,
										props.getTmDimension(),
										new TeleporterTime(thePlayer.mcServer
												.worldServerForDimension(props
														.getTmDimension())));
					}
				}
			}
		}
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		if (!player.worldObj.isRemote) {
			for (ItemStack item : player.inventory.mainInventory) {
				if (item != null)
					if (item.itemID == ItemIds.WATCH) {
						NBTTagCompound nbt = item.getTagCompound();
						if (nbt == null) {
							nbt = new NBTTagCompound();
							item.setTagCompound(nbt);
						}

						if (TravellingRegistry
								.isValidTimeDestination(player.worldObj.provider.dimensionId)) {
							nbt.setBoolean("Travelling", true);
						} else {
							nbt.setBoolean("Travelling", false);
						}
					}
			}
		}
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {

	}

}
