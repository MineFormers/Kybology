package de.mineformers.timetravel.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.timetravel.api.TravellingRegistry;
import de.mineformers.timetravel.lib.Reference;
import de.mineformers.timetravel.lib.Strings;
import de.mineformers.timetravel.world.TeleporterTest;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * TimeTravel
 * 
 * ItemPocketWatch
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemPocketWatch extends ItemTT {

	private Icon iconOpened;
	private Icon iconClosed;

	public ItemPocketWatch(int id) {
		super(id, Strings.WATCH_NAME);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
	        EntityPlayer player) {
		if (!world.isRemote) {
			if (TravellingRegistry
			        .isValidTimeDestination(world.provider.dimensionId)) {
				if ((player.ridingEntity == null)
				        && (player.riddenByEntity == null)
				        && ((player instanceof EntityPlayerMP))) {
					EntityPlayerMP thePlayer = (EntityPlayerMP) player;
					if (thePlayer.timeUntilPortal > 0) {
						thePlayer.timeUntilPortal = 10;
					} else {
						thePlayer.timeUntilPortal = 10;
						thePlayer.mcServer.getConfigurationManager()
						        .transferPlayerToDimension(
						                thePlayer,
						                0,
						                new TeleporterTest(thePlayer.mcServer
						                        .worldServerForDimension(0)));
					}
				}
			} else {
				player.addChatMessage("You can't travel from here");
			}
		}
		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		iconOpened = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase()
		        + ":pocketWatch");
		iconClosed = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase()
		        + ":pocketWatchClosed");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 1;
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("Travelling")
			        && stack.getTagCompound().getBoolean("Travelling") == true) {
				return iconOpened;
			}
		}
		return iconClosed;
	}
}
