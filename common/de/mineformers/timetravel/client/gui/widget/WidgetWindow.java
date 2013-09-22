package de.mineformers.timetravel.client.gui.widget;

import de.mineformers.timetravel.lib.Textures;

/**
 * TimeTravel
 * 
 * WidgetWindow
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class WidgetWindow extends Widget {

	private int width, height;

	public WidgetWindow(int x, int y, int width, int height) {
		super(Textures.GUI_WIDGETS, x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public void draw(int mouseX, int mouseY) {
		// Corners clockwise
		this.drawRectangle(drawX, drawY, 31, 1, 5, 5);
		this.drawRectangle(drawX + width - 5, drawY, 39, 1, 5, 5);
		this.drawRectangle(drawX + width - 5, drawY + height - 5, 39, 9, 5, 5);
		this.drawRectangle(drawX, drawY + height - 5, 31, 9, 5, 5);

		// Sides clockwise
		this.drawRectangleStretched(drawX + 5, drawY, 37, 1, width - 10, 5, 1,
		        5);
		this.drawRectangleStretched(drawX + width - 5, drawY + 5, 39, 7, 5,
		        height - 10, 5, 1);
		this.drawRectangleStretched(drawX + 5, drawY + height - 5, 37, 9,
		        width - 10, 5, 1, 5);
		this.drawRectangleStretched(drawX, drawY + 5, 31, 7, 5, height - 10, 5,
		        1);

		// Canvas
		this.drawRectangleStretched(drawX + 5, drawY + 5, 37, 7, width - 10,
		        height - 10, 1, 1);
	}

}
