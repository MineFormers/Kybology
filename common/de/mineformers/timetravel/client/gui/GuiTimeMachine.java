package de.mineformers.timetravel.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

import de.mineformers.timetravel.api.TravellingRegistry;
import de.mineformers.timetravel.lib.Textures;
import de.mineformers.timetravel.network.packet.PacketStartTravel;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

/**
 * TimeTravel
 * 
 * GuiTimeMachine
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class GuiTimeMachine extends GuiScreen {

	private int currentPage;
	private int xSize;
	private int ySize;

	private int blockX, blockY, blockZ;
	private boolean counting;

	public GuiTimeMachine(int x, int y, int z, boolean counting) {
		super();
		xSize = 256;
		ySize = 210;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.counting = counting;
		currentPage = 0;
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;

		GuiNextPrevButton prevButton = new GuiNextPrevButton(0, xStart + 10,
		        yStart + 93, true);
		GuiNextPrevButton nextButton = new GuiNextPrevButton(1, xStart + 236,
		        yStart + 93, false);

		prevButton.enabled = false;
		nextButton.enabled = TravellingRegistry.getTimeDestinationCount() > 1;

		this.buttonList.add(prevButton);
		this.buttonList.add(nextButton);
		GuiButton buttonTravel = new GuiButton(2, xStart + 28, yStart + 180,
		        95, 20, "Travel");
		buttonTravel.enabled = !counting;
		this.buttonList.add(buttonTravel);
		this.buttonList.add(new GuiButton(3, xStart + 133, yStart + 180, 95,
		        20, "Cancel"));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 0:
				currentPage -= 1;
				if (currentPage == 0)
					button.enabled = false;
				if (currentPage < TravellingRegistry.getTimeDestinationCount() - 1)
					for (int k = 0; k < this.buttonList.size(); ++k) {
						GuiButton guibutton = (GuiButton) this.buttonList
						        .get(k);
						if (guibutton.id == 1) {
							button.enabled = true;
							break;
						}
					}
				break;
			case 1:
				currentPage += 1;
				if (currentPage == TravellingRegistry.getTimeDestinationCount() - 1)
					button.enabled = false;
				if (currentPage > 0) {
					for (int k = 0; k < this.buttonList.size(); ++k) {
						GuiButton guibutton = (GuiButton) this.buttonList
						        .get(k);
						if (guibutton.id == 0) {
							button.enabled = true;
							break;
						}
					}
				}
				break;
			case 2:
				PacketDispatcher.sendPacketToServer(new PacketStartTravel(
				        blockX, blockY, blockZ, TravellingRegistry
				                .getTimeDestination(currentPage, false)
				                .getDimensionId()).makePacket());
				mc.currentScreen = null;
				mc.setIngameFocus();
				break;
			case 3:
				mc.currentScreen = null;
				mc.setIngameFocus();
				break;
		}
	}

	@Override
	public void drawScreen(int x, int y, float opacity) {
		this.drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(Textures.GUI_TIMEMACHINE);

		int xStart = (width - xSize) / 2;
		int yStart = (height - ySize) / 2;
		this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
		this.mc.getTextureManager().bindTexture(
		        TravellingRegistry.getTimeDestination(currentPage, false)
		                .getPreviewImage());
		this.drawTexturedModalRect(xStart + 28, yStart + 25, 0, 0, 200, 150);
		this.mc.fontRenderer.drawString("Time Machine", xStart + 10,
		        yStart + 10, 0x404040, false);

		super.drawScreen(x, y, opacity);
	}

}
