package de.mineformers.timetravel.inventory;

import de.mineformers.timetravel.lib.ItemIds;
import de.mineformers.timetravel.tileentity.TileEnergyExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * TimeTravel
 * 
 * ContainerExtractor
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ContainerExtractor extends Container {

	private final int PLAYER_INVENTORY_ROWS = 3;
	private final int PLAYER_INVENTORY_COLUMNS = 9;

	public ContainerExtractor(InventoryPlayer inventoryPlayer,
	        TileEnergyExtractor tile) {
		this.addSlotToContainer(new Slot(tile,
		        TileEnergyExtractor.SLOT_STORAGE, 44, 74) {
			@Override
		    public boolean isItemValid(ItemStack itemStack)
		    {
		        return itemStack.itemID == ItemIds.CRYSTAL && itemStack.getItemDamage() == 1;
		    }
		});
		this.addSlotToContainer(new Slot(tile, TileEnergyExtractor.SLOT_INPUT,
		        44, 18));
		this.addSlotToContainer(new Slot(tile, TileEnergyExtractor.SLOT_OUTPUT,
		        44, 39));

		// Add the player's inventory slots to the container
		for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
			for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
				this.addSlotToContainer(new Slot(inventoryPlayer,
				        inventoryColumnIndex + inventoryRowIndex * 9 + 9,
				        8 + inventoryColumnIndex * 18,
				        106 + inventoryRowIndex * 18));
			}
		}

		// Add the player's action bar slots to the container
		for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
			this.addSlotToContainer(new Slot(inventoryPlayer,
			        actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 164));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityPlayer,
	        int slotIndex) {

		ItemStack itemStack = null;
		Slot slot = (Slot) inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {

			ItemStack slotItemStack = slot.getStack();
			itemStack = slotItemStack.copy();

			/**
			 * If we are shift-clicking an item out of the Aludel's container,
			 * attempt to put it in the first available slot in the player's
			 * inventory
			 */
			if (slotIndex < TileEnergyExtractor.INVENTORY_SIZE) {

				if (!this.mergeItemStack(slotItemStack,
				        TileEnergyExtractor.INVENTORY_SIZE,
				        inventorySlots.size(), false)) {
					return null;
				}
			} else {

				/**
				 * Finally, attempt to put stack into the input slot
				 */
				if (!this.mergeItemStack(slotItemStack,
				        TileEnergyExtractor.SLOT_INPUT,
				        TileEnergyExtractor.SLOT_STORAGE, false)) {
					return null;
				}
			}

			if (slotItemStack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemStack;
	}

}
