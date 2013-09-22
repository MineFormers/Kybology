package de.mineformers.timetravel.client.gui.widget;

import java.util.LinkedList;

import de.mineformers.timetravel.lib.Textures;

/**
 * TimeTravel
 * 
 * WidgetCanvas
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class WidgetCanvas extends Widget {

	private LinkedList<Widget> widgets;

	public WidgetCanvas(int x, int y) {
		super(Textures.GUI_WIDGETS, x, y);
		this.widgets = new LinkedList<Widget>();
	}

	public void addWidget(Widget widget) {
		widgets.add(widget);
	}

	@Override
	public void draw(int mouseX, int mouseY) {
		for (Widget widget : widgets) {
			widget.setDrawPos(x + widget.getX(), y + widget.getY());
			widget.draw(mouseX, mouseY);
		}
	}

	public void mouseClick(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			for (Widget widget : widgets) {
				if (widget instanceof WidgetButton) {
					if (((WidgetButton) widget).isHovering(mouseX, mouseY)) {
						mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
						((WidgetButton) widget).triggerClick();
					}
				}
			}
		}
	}

}
