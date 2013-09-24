package de.mineformers.timetravel.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import de.mineformers.timetravel.api.TravellingRegistry;
import de.mineformers.timetravel.client.gui.widget.WidgetButton;
import de.mineformers.timetravel.client.gui.widget.WidgetButtonPage;
import de.mineformers.timetravel.client.gui.widget.WidgetCanvas;
import de.mineformers.timetravel.client.gui.widget.WidgetSlot;
import de.mineformers.timetravel.client.gui.widget.WidgetWindow;
import de.mineformers.timetravel.client.gui.widget.listener.ListenerClickable;
import de.mineformers.timetravel.core.util.LangHelper;
import de.mineformers.timetravel.network.packet.PacketStartTravel;

/**
 * TimeTravel
 * 
 * WidgetCanvasTimeTravel
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class WidgetCanvasTimeTravel extends WidgetCanvas {

	private int blockX, blockY, blockZ;
	private int currentPage;

	public WidgetCanvasTimeTravel(int x, int y, int z, boolean counting) {
		super(0, 0);
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
		this.addWidget(new WidgetWindow(0, 0, 256, 210));
		this.addWidget(new WidgetSlot(26, 23, 204, 154));

		final WidgetButtonPage btnPrev = new WidgetButtonPage(10, 93,
		        WidgetButtonPage.TYPE_PREV);
		final WidgetButtonPage btnNext = new WidgetButtonPage(236, 93,
		        WidgetButtonPage.TYPE_NEXT);
		WidgetButton btnTravel = new WidgetButton(28, 180, 95, 20,
		        LangHelper.translate("gui", "button.travel"));
		WidgetButton btnCancel = new WidgetButton(133, 180, 95, 20,
		        LangHelper.translate("gui", "button.cancel"));

		btnPrev.setEnabled(false);
		btnNext.setEnabled(TravellingRegistry.getTimeDestinationCount() > 1);
		btnTravel.setEnabled(!counting);

		btnPrev.addListener(new ListenerClickable() {

			@Override
			public void onClick(int mouseX, int mouseY) {
				currentPage -= 1;
				if (currentPage == 0)
					btnPrev.setEnabled(false);
				if (currentPage < TravellingRegistry.getTimeDestinationCount() - 1)
					btnNext.setEnabled(true);
			}
		});

		btnNext.addListener(new ListenerClickable() {

			@Override
			public void onClick(int mouseX, int mouseY) {
				currentPage += 1;
				if (currentPage == TravellingRegistry.getTimeDestinationCount() - 1)
					btnNext.setEnabled(false);
				if (currentPage > 0)
					btnPrev.setEnabled(true);
			}
		});

		btnTravel.addListener(new ListenerClickable() {

			@Override
			public void onClick(int mouseX, int mouseY) {
				PacketDispatcher.sendPacketToServer(new PacketStartTravel(
				        blockX, blockY, blockZ, TravellingRegistry
				                .getTimeDestination(currentPage, false)
				                .getDimensionId()).makePacket());
				mc.currentScreen = null;
				mc.setIngameFocus();
			}
		});

		btnCancel.addListener(new ListenerClickable() {

			@Override
			public void onClick(int mouseX, int mouseY) {
				mc.currentScreen = null;
				mc.setIngameFocus();
			}
		});

		this.addWidget(btnPrev);
		this.addWidget(btnNext);

		this.addWidget(btnTravel);
		this.addWidget(btnCancel);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
		super.drawForeground(mouseX, mouseY);

		this.drawRectangle(
		        TravellingRegistry.getTimeDestination(currentPage, false)
		                .getPreviewImage(), x + 28, y + 25, 0, 0, 200, 150);
		this.mc.fontRenderer.drawString("Time Machine", x + 10, y + 10,
		        0x404040, false);
	}

}
