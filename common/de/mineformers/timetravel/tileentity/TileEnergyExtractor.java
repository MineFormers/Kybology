package de.mineformers.timetravel.tileentity;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.util.AxisAlignedBB;
import de.mineformers.timetravel.api.energy.IEnergyStorage;
import de.mineformers.timetravel.api.util.CrystalHelper;
import de.mineformers.timetravel.core.util.NetworkHelper;
import de.mineformers.timetravel.entity.EntityRift;
import de.mineformers.timetravel.lib.ItemIds;
import de.mineformers.timetravel.lib.ParticleIds;
import de.mineformers.timetravel.lib.Strings;
import de.mineformers.timetravel.network.packet.PacketSpawnParticle;

/**
 * TimeTravel
 * 
 * BlockEnergyExtractor
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
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
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            if (this.getMaximumEnergy() == this.getStoredEnergy())
                return;
            if (this.inventory[SLOT_INPUT] != null) {
                @SuppressWarnings("unchecked")
                List<EntityRift> riftList = worldObj.getEntitiesWithinAABB(
                        EntityRift.class, AxisAlignedBB.getBoundingBox(
                                xCoord - 25, yCoord - 25, zCoord - 25,
                                xCoord + 25, yCoord + 25, zCoord + 25));
                for (EntityRift temp : riftList) {
                    if (this.getStoredEnergy() != this.getMaximumEnergy()) {
                        if (temp.getSignature() == CrystalHelper
                                .getSignature(inventory[SLOT_INPUT])) {
                            if (this.addEnergy(temp.drawEnergy(CrystalHelper
                                    .getEnergyData(inventory[SLOT_INPUT])))) {
                                double startX = temp.posX;
                                double startY = temp.posY;
                                double startZ = temp.posZ;
                                double destX = xCoord + 0.5D;
                                double destY = yCoord + 1.1D;
                                double destZ = zCoord + 0.5D;
                                PacketSpawnParticle pkt = new PacketSpawnParticle(
                                        ParticleIds.ENERGY_TRAIL, startX,
                                        startY, startZ, destX, destY, destZ);
                                NetworkHelper.sendToAllAround(worldObj, xCoord,
                                        yCoord, zCoord, pkt.makePacket());
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getStoredEnergy() {
        return energy;
    }

    @Override
    public int getMaximumEnergy() {
        return this.inventory[SLOT_STORAGE] != null
                && this.inventory[SLOT_STORAGE].itemID == ItemIds.CRYSTAL
                && this.inventory[SLOT_STORAGE].getItemDamage() == 0 ? (Integer) CrystalHelper
                .getType(getStackInSlot(SLOT_STORAGE)).getData("Energy") : 0;
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
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("energy", this.energy);
        compound.setInteger("maxEnergy", this.maxEnergy);

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        compound.setTag("Items", nbttaglist);

    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean addEnergy(int energy) {
        boolean result = false;
        if (this.inventory[SLOT_STORAGE] != null) {
            if (this.getStoredEnergy() + energy >= this.getMaximumEnergy()) {
                this.energy = this.getMaximumEnergy();
            } else {
                this.energy += energy;
                result = energy > 0;
            }
        }
        if (!worldObj.isRemote)
            NetworkHelper.sendTilePacket(this);
        return result;
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        this.readFromNBT(pkt.data);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound);
        Packet132TileEntityData packet = new Packet132TileEntityData(xCoord,
                yCoord, zCoord, 0, compound);
        return packet;
    }

    // Inventory stuff

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return inventory[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount) {

        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null) {
            if (itemStack.stackSize <= decrementAmount) {
                setInventorySlotContents(slotIndex, null);
            } else {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex) {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null) {
            setInventorySlotContents(slotIndex, null);
        }
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        inventory[slotIndex] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName() {
        return this.hasCustomName() ? customName : Strings.CONTAINER_EXTRACTOR;
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
