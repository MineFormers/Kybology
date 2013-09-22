package de.mineformers.timetravel.client.gui.timemachine;

import de.mineformers.timetravel.client.gui.widget.WidgetButton;
import de.mineformers.timetravel.client.gui.widget.WidgetButton.ButtonListener;
import de.mineformers.timetravel.client.gui.widget.WidgetCanvas;
import de.mineformers.timetravel.client.gui.widget.WidgetWindow;
import de.mineformers.timetravel.core.util.LangHelper;

/**
 * TimeTravel
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
		btnClose.addListener(new ButtonListener() {

			@Override
			public void onClick() {
				mc.currentScreen = null;
				mc.setIngameFocus();
			}
		});
		
		this.addWidget(btnClose);
	}

	@Override
	public void draw(int mouseX, int mouseY) {
		super.draw(mouseX, mouseY);

		this.mc.fontRenderer.drawString(
		        LangHelper.translate("gui", "label.error"),
		        x
		                + ((176 - mc.fontRenderer.getStringWidth(LangHelper
		                        .translate("gui", "label.error"))) / 2), y + 7,
		        0x404040, false);
		this.drawSplitStringCentered(message, x, y + 20, 0xFF0000, false, 176);
	}

}