package de.mineformers.kybology.timetravel.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import de.mineformers.kybology.core.client.gui.widget.WidgetCanvasContainer;
import de.mineformers.kybology.core.client.gui.widget.WidgetInventoryPlayer;
import de.mineformers.kybology.core.client.gui.widget.WidgetWindow;
import de.mineformers.kybology.core.inventory.ContainerExtractor;
import de.mineformers.kybology.timetravel.tileentity.TileEnergyExtractor;

/**
 * Kybology
 * 
 * WidgetCanvasExtractor
 * 
 * @author PaleoCrafter, Weneg
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

		this.drawString(Integer.toString(tile.getStoredEnergy()) + "/"
				+ Integer.toString(tile.getMaximumEnergy()), 5, 15, 0x404040,
				false);
	}

}
