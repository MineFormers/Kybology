package de.mineformers.timetravel.client.gui;

import org.lwjgl.opengl.GL11;

import de.mineformers.timetravel.lib.Textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

/**
 * TimeTravel
 * 
 * GuiNextPrevButton
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class GuiNextPrevButton extends GuiButton {

	private boolean prev;

	public GuiNextPrevButton(int id, int x, int y, boolean prev) {
		super(id, x, y, 10, 15, "");
		this.prev = prev;
	}

	public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
		if (this.drawButton) {
			this.field_82253_i = mouseX >= this.xPosition
			        && mouseY >= this.yPosition
			        && mouseX < this.xPosition + this.width
			        && mouseY < this.yPosition + this.height;
			minecraft.getTextureManager().bindTexture(Textures.GUI_WIDGETS);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			int state = this.getHoverState(this.field_82253_i);

			int y = (prev) ? 15 : 0;
			this.drawTexturedModalRect(this.xPosition, this.yPosition,
			        state * 10, y, 10, 15);

			this.mouseDragged(minecraft, mouseX, mouseY);
		}
	}

	@Override
	protected int getHoverState(boolean hovered) {
		byte b = 0;

		if (!this.enabled) {
			b = 2;
		} else if (hovered) {
			b = 1;
		}

		return b;
	}

}
