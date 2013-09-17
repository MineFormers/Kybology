package de.mineformers.timetravel.client.gui.overlay;

import org.lwjgl.opengl.GL11;

import de.mineformers.timetravel.api.TravellingRegistry;
import de.mineformers.timetravel.core.handler.TickHandlerWatch;
import de.mineformers.timetravel.core.util.ResourceHelper;
import de.mineformers.timetravel.lib.ItemIds;
import de.mineformers.timetravel.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

/**
 * TimeTravel
 * 
 * GuiOverlayWatch
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class GuiOverlayWatch extends Gui {

	public Minecraft mc;

	public GuiOverlayWatch(Minecraft mc) {
		super();
		this.mc = mc;
	}

	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderText(RenderGameOverlayEvent event) {
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE) {
			return;
		}
		if (TravellingRegistry
		        .isValidTimeDestination(mc.thePlayer.worldObj.provider.dimensionId)) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);

			boolean watchAvail = false;

			for (ItemStack stack : mc.thePlayer.inventory.mainInventory) {
				if (stack != null)
					if (stack.itemID == ItemIds.WATCH) {
						watchAvail = true;
					}
			}

			if (watchAvail) {
				ScaledResolution resolution = new ScaledResolution(
				        mc.gameSettings, mc.displayWidth, mc.displayHeight);
				int height = resolution.getScaledHeight();
				if (mc.gameSettings.guiScale != 0) {
					String time = String.format("%02d", TickHandlerWatch.min)
					        + ":" + String.format("%02d", TickHandlerWatch.sec);
					this.drawString(this.mc.fontRenderer, time, (int) (5 + Math
					        .floor((27 - this.mc.fontRenderer
					                .getStringWidth(time)) / 2)),
					        height - 5 - 10, 0xFFFFFF);
					this.mc.getTextureManager().bindTexture(
					        Textures.GUI_OVERLAY);
					int watchY = height - 30 - 10 - 10;
					this.drawTexturedModalRect(5, watchY, 54, 0, 27, 30);
					GL11.glPushMatrix();
					GL11.glTranslatef(5 + 13, watchY + 16, 0);
					if (TickHandlerWatch.sec >= 30)
						GL11.glTranslatef(0, 1, 0);
					GL11.glRotatef(TickHandlerWatch.sec * 6, 0, 0, 1);
					this.drawTexturedModalRect(0, -7, 58, 30, 1, 7);
					GL11.glPopMatrix();
					GL11.glPushMatrix();
					GL11.glTranslatef(5 + 13, watchY + 16, 0);
					GL11.glRotatef((float) (TickHandlerWatch.min * 6 + Math
					        .floor(TickHandlerWatch.sec / 10)), 0, 0, 1);
					this.drawTexturedModalRect(0, -5, 59, 32, 1, 5);
					GL11.glPopMatrix();
				} else {
					this.drawString(this.mc.fontRenderer, "5:00",
					        (int) (5 + Math.floor((54 - this.mc.fontRenderer
					                .getStringWidth("5:00")) / 2)),
					        height - 5 - 10, 0xFFFFFF);
					this.mc.getTextureManager()
					        .bindTexture(
					                ResourceHelper
					                        .getResourceLocation("textures/gui/overlay.png"));
					this.drawTexturedModalRect(5, height - 60 - 10 - 10, 0, 0,
					        54, 60);
				}
			}
		}
	}
}
