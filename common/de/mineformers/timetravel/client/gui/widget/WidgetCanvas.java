package de.mineformers.timetravel.client.gui.widget;

import java.util.LinkedList;

import org.lwjgl.opengl.GL11;

import de.mineformers.timetravel.client.gui.widget.listener.ListenerClickable;
import de.mineformers.timetravel.client.gui.widget.listener.ListenerKeyboard;
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
			if (widget.isVisible()) {
				GL11.glPushMatrix();
				GL11.glTranslatef(x, y, 0);
				widget.setScreenPos(widget.getX() + x, widget.getY() + y);
				widget.draw(mouseX, mouseY);
				GL11.glPopMatrix();
			}
		}
	}

	public void mouseClick(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			for (Widget widget : widgets) {
				if (widget.isVisible())
					if (widget.isHovered(mouseX, mouseY)) {
						widget.notifyListeners(ListenerClickable.class,
						        "onClick", mouseX, mouseY);
					}
			}
		}
	}

	public void keyType(char keyChar, int keyCode) {
		for (Widget widget : widgets) {
			if (widget.isVisible())
				widget.notifyListeners(ListenerKeyboard.class, "onKeyTyped",
				        keyChar, keyCode);
		}
	}

}
