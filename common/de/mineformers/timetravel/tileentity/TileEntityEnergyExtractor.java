package de.mineformers.timetravel.tileentity;

import de.mineformers.timetravel.api.energy.IEnergyStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityEnergyExtractor extends TileEntity implements IEnergyStorage{
	private int energy;
	private int  maxEnergy;
	private ItemStack[] crystallItemStacks;
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
    public int getSizeInventory()
    {
    	
        return this.crystallItemStacks.length;
    }
    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.crystallItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.crystallItemStacks.length)
            {
                this.crystallItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.energy = par1NBTTagCompound.getInteger("energy");
        this.maxEnergy = par1NBTTagCompound.getInteger("maxEnergy");

    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("energy", this.energy);
        par1NBTTagCompound.setInteger("maxEnergy", this.maxEnergy);


        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.crystallItemStacks.length; ++i)
        {
            if (this.crystallItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.crystallItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);

    }
    public int getInventoryStackLimit()
    {
        return 1;
    }
    @Override
	public void addEnergy(int energy) {
		this.energy += energy;
	}
}
