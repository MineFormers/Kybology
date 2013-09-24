package de.mineformers.timetravel.tileentity;

import de.mineformers.timetravel.api.energy.IEnergyStorage;
import de.mineformers.timetravel.network.packet.PacketExtractorUpdate;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;

public class TileEnergyExtractor extends TileTT implements IEnergyStorage,
        IInventory {

	public static final int INVENTORY_SIZE = 3;

	// Slots indizes
	public static final int SLOT_STORAGE = 0;
	public static final int SLOT_INPUT = 1;
	public static final int SLOT_OUTPUT = 2;

	private int energy;
	private int maxEnergy;
	private ItemStack[] inventory;

	public TileEnergyExtractor() {
		inventory = new ItemStack[INVENTORY_SIZE];
	}

	@Override
	public int getStoredEnergy() {
		return energy;
	}

	@Override
	public int getMaximumEnergy() {
		return maxEnergy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory() {
		return this.inventory.length;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
		this.inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist
			        .tagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.inventory.length) {
				this.inventory[b0] = ItemStack
				        .loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.energy = par1NBTTagCompound.getInteger("energy");
		this.maxEnergy = par1NBTTagCompound.getInteger("maxEnergy");

	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("energy", this.energy);
		par1NBTTagCompound.setInteger("maxEnergy", this.maxEnergy);

		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.inventory.length; ++i) {
			if (this.inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);

	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void addEnergy(int energy) {
		this.energy += energy;
	}

	@Override
	public Packet getDescriptionPacket() {
		return new PacketExtractorUpdate(xCoord, yCoord, zCoord, orientation,
		        state, customName, energy).makePacket();
	}

	// Inventory stuff

	@Override
	public ItemStack getStackInSlot(int i) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {

	}

	@Override
	public String getInvName() {
		return customName;
	}

	@Override
	public boolean isInvNameLocalized() {
		return this.hasCustomName();
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

}
