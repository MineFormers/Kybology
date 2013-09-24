package de.mineformers.timetravel.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import de.mineformers.timetravel.client.gui.widget.WidgetCanvasContainer;
import de.mineformers.timetravel.client.gui.widget.WidgetInventoryPlayer;
import de.mineformers.timetravel.client.gui.widget.WidgetWindow;
import de.mineformers.timetravel.inventory.ContainerExtractor;
import de.mineformers.timetravel.tileentity.TileEnergyExtractor;

/**
 * TimeTravel
 * 
 * WidgetCanvasExtractor
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class WidgetCanvasExtractor extends WidgetCanvasContainer {

	private TileEnergyExtractor tile;

	public WidgetCanvasExtractor(int x, int y, TileEnergyExtractor tile,
	        InventoryPlayer inventory) {
		super(x, y, new ContainerExtractor(inventory, tile), tile, true);

		this.tile = tile;

		this.addWidget(new WidgetWindow(0, 0, 176, 187));
		this.addWidget(new WidgetInventoryPlayer(7, 95));
	}

	@Override
	public void drawForeground(int mouseX, int mouseY) {
		super.drawForeground(mouseX, mouseY);

		this.drawString(Integer.toString(tile.getStoredEnergy()), 5, 15,
		        0x404040, false);
	}

}
