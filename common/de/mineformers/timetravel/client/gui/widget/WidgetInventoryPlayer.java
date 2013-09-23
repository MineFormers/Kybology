package de.mineformers.timetravel.client.gui.widget;

import de.mineformers.timetravel.lib.Textures;

/**
 * TimeTravel
 * 
 * WidgetInventoryPlayer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class WidgetInventoryPlayer extends Widget {

	private WidgetInventory widget;

	public WidgetInventoryPlayer(int x, int y) {
		super(Textures.GUI_WIDGETS, x, y);
		this.widget = new WidgetInventory(0, 0, 9, 3);
	}

	@Override
	public void draw(int mouseX, int mouseY) {
		widget.setPos(0, 0);
		widget.setSlots(9, 3);
		widget.draw(mouseX, mouseY);

		widget.setPos(0, 58);
		widget.setSlots(9, 1);
		widget.draw(mouseX, mouseY);
	}

}
