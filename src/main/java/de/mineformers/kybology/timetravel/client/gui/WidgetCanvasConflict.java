package de.mineformers.kybology.timetravel.client.gui;

import de.mineformers.kybology.core.client.gui.widget.WidgetButton;
import de.mineformers.kybology.core.client.gui.widget.WidgetCanvas;
import de.mineformers.kybology.core.client.gui.widget.WidgetWindow;
import de.mineformers.kybology.core.client.gui.widget.listener.ListenerClickable;
import de.mineformers.kybology.core.util.LangHelper;

/**
 * Kybology
 * 
 * WidgetCanvasConflict
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class WidgetCanvasConflict extends WidgetCanvas {

	private String message;

	public WidgetCanvasConflict(String message) {
		super(0, 0);

		this.message = message;
		this.addWidget(new WidgetWindow(0, 0, 176, 75));

		WidgetButton btnClose = new WidgetButton(53, 47, 70, 20,
		        LangHelper.translate("gui", "button.close"));
		btnClose.addListener(new ListenerClickable() {

			@Override
			public void onClick(int mouseX, int mouseY) {
				mc.currentScreen = null;
				mc.setIngameFocus();
			}
		});

		this.addWidget(btnClose);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
		super.drawForeground(mouseX, mouseY);
		this.drawString(
		        LangHelper.translate("gui", "label.error"),
		        x
		                + ((176 - this.getStringWidth(LangHelper.translate(
		                        "gui", "label.error"))) / 2), y + 7, 0x404040,
		        false);
		this.drawSplitStringCentered(message, x, y + 20, 0xFF0000, false, 176);
	}

}
