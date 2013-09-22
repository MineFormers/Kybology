package de.mineformers.timetravel.client.gui.widget;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.mineformers.timetravel.lib.Textures;
import net.minecraft.client.gui.FontRenderer;

/**
 * TimeTravel
 * 
 * WidgetButton
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class WidgetButton extends Widget {

	public interface ButtonListener {

		public void onClick();

	}

	private ArrayList<ButtonListener> listeners;

	protected boolean enabled;

	protected int width, height;
	protected String text;

	public WidgetButton(int x, int y, int width, int height, String text) {
		super(Textures.GUI_WIDGETS, x, y);
		this.width = width;
		this.height = height;
		this.text = text;
		this.enabled = true;
		listeners = new ArrayList<ButtonListener>();
	}

	public void addListener(ButtonListener listener) {
		listeners.add(listener);
	}

	public void triggerClick() {
		for (ButtonListener listener : listeners)
			listener.onClick();
	}

	@Override
	public void draw(int mouseX, int mouseY) {
		FontRenderer fontRenderer = mc.fontRenderer;
		boolean hovering = isHovering(mouseX, mouseY);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int state = getHoverState(hovering);

		// Corners clockwise
		this.drawRectangle(drawX, drawY, 1 + 19 * state, 31, 5, 5);
		this.drawRectangle(drawX + width - 5, drawY, 13 + 19 * state, 31, 5, 5);
		this.drawRectangle(drawX + width - 5, drawY + height - 5,
		        13 + 19 * state, 43, 5, 5);
		this.drawRectangle(drawX, drawY + height - 5, 1 + 19 * state, 43, 5, 5);

		// Sides clockwise
		this.drawRectangleStretched(drawX + 5, drawY, 7 + 19 * state, 31,
		        width - 10, 5, 5, 5);
		this.drawRectangleStretched(drawX + width - 5, drawY + 5,
		        13 + 19 * state, 37, 5, height - 10, 5, 5);
		this.drawRectangleStretched(drawX + 5, drawY + height - 5,
		        7 + 19 * state, 43, width - 10, 5, 5, 5);
		this.drawRectangleStretched(drawX, drawY + 5, 1 + 19 * state, 37, 5,
		        height - 10, 5, 5);

		// Canvas
		this.drawRectangleStretched(drawX + 5, drawY + 5, 7 + 19 * state, 37,
		        width - 10, height - 10, 5, 5);

		int color = 0xe0e0e0;

		if (!this.enabled) {
			color = -0x5f5f60;
		} else if (hovering) {
			color = 0xffffa0;
		}

		fontRenderer.drawString(text,
		        drawX + ((width - fontRenderer.getStringWidth(text)) / 2),
		        drawY + ((height - 8) / 2), color, true);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	protected int getHoverState(boolean hovering) {
		byte b0 = 1;

		if (!this.enabled) {
			b0 = 0;
		} else if (hovering) {
			b0 = 2;
		}

		return b0;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isHovering(int mouseX, int mouseY) {
		return mouseX > drawX && mouseY > drawY && mouseX < (drawX + width)
		        && mouseY < (drawY + height);
	}
	
    public boolean isEnabled() {
	    return enabled;
    }
    
}
