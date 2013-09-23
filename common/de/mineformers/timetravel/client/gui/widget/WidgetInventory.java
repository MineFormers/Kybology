package de.mineformers.timetravel.client.gui.widget;

import de.mineformers.timetravel.lib.Textures;

/**
 * TimeTravel
 * 
 * WidgetInventory
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class WidgetInventory extends Widget {

	private WidgetSlot slot;
	private int width, height;

	public WidgetInventory(int x, int y, int slotsX, int slotsY) {
		super(Textures.GUI_WIDGETS, x, y);
		slot = new WidgetSlot(x, y, 18, 18);
		this.width = slotsX;
		this.height = slotsY;
	}

	public void draw(int mouseX, int mouseY) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				slot.setPos(x + i * 18, y + j * 18);
				slot.draw(mouseX, mouseY);
			}
		}
	}

	public void setSlots(int slotsX, int slotsY) {
		this.width = slotsX;
		this.height = slotsY;
	}

    @Override
    public boolean isHovered(int mouseX, int mouseY) {
	    return false;
    }

}
