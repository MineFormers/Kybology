package de.mineformers.timetravel.client.gui;

import org.lwjgl.opengl.GL11;

import de.mineformers.timetravel.client.gui.widget.WidgetCanvas;
import net.minecraft.client.gui.GuiScreen;

/**
 * TimeTravel
 * 
 * GuiScreenTT
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class GuiScreenTT extends GuiScreen {

	private int canvasWidth, canvasHeight;
	private WidgetCanvas canvas;

	public GuiScreenTT(int width, int height, WidgetCanvas canvas) {
		this.canvas = canvas;
		this.canvasWidth = width;
		this.canvasHeight = height;
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button) {
		canvas.mouseClick(mouseX, mouseY, button);
	}
	
	@Override
	protected void keyTyped(char keyChar, int keyCode) {
		super.keyTyped(keyChar, keyCode);
		canvas.keyType(keyChar, keyCode);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float opacity) {
		this.drawDefaultBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int xStart = (width - canvasWidth) / 2;
		int yStart = (height - canvasHeight) / 2;

		canvas.setPos(xStart, yStart);
		canvas.draw(mouseX, mouseY);
	}

	public boolean doesGuiPauseGame() {
		return false;
	}

}
