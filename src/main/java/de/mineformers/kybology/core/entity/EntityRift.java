package de.mineformers.kybology.core.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import de.mineformers.kybology.api.energy.IEnergyDrawable;
import de.mineformers.kybology.api.energy.IEnergyStorage;
import de.mineformers.kybology.core.util.Vector3;

/**
 * Kybology
 * 
 * EntityRift
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class EntityRift extends Entity implements IEntityAdditionalSpawnData,
        IEnergyStorage, IEnergyDrawable {
    private int type;
    private int maxStorage;
    private int signature;

    public EntityRift(World world) {
        super(world);
        setSize(1F, 1F);
        this.noClip = true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public void writeSpawnData(ByteArrayDataOutput data) {
        data.writeInt(getType());
        data.writeInt(getMaximumEnergy());
        data.writeInt(signature);
    }

    @Override
    public void readSpawnData(ByteArrayDataInput data) {
        setType(data.readInt());
        setMaximumEnergy(data.readInt());
        signature = data.readInt();
    }

    @Override
    protected void entityInit() {
        dataWatcher.addObject(15, 0);
    }

    @Override
    public boolean interactFirst(EntityPlayer player) {
        if (!worldObj.isRemote) {
            this.addEnergy(10);
            player.sendChatToPlayer(ChatMessageComponent.createFromText(Integer
                    .toString(getStoredEnergy())));

            return true;
        }
        return false;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        setType(compound.getInteger("type"));
        setEnergy(compound.getInteger("power"));
        setMaximumEnergy(compound.getInteger("maxStorage"));
        setSignature(compound.getInteger("signature"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("type", getType());
        compound.setInteger("power", getStoredEnergy());
        compound.setInteger("maxStorage", getMaximumEnergy());
        compound.setInteger("signature", signature);
    }

    public void setEnergy(int power) {
        dataWatcher.updateObject(15, power);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getStoredEnergy() {
        return dataWatcher.getWatchableObjectInt(15);
    }

    @Override
    public int getMaximumEnergy() {
        return maxStorage;
    }

    public void setMaximumEnergy(int maxEnergy) {
        this.maxStorage = maxEnergy;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!worldObj.isRemote) {
            if (rand.nextInt(100) > 90)
                this.addEnergy(1);
        }
        if (worldObj.isRemote) {
            if (getStoredEnergy() > 750) {
                float x = rand.nextFloat() - 0.5F;
                float y = rand.nextFloat() - 0.5F;
                float z = rand.nextFloat() - 0.5F;
                worldObj.spawnParticle("enchantmenttable", this.posX + x, posY
                        + y, posZ + z, 0, 0, 0);
                x = rand.nextFloat() - 0.5F;
                y = rand.nextFloat() - 0.5F;
                z = rand.nextFloat() - 0.5F;
                worldObj.spawnParticle("portal", this.posX + x, posY + y, posZ
                        + z, 0, 0, 0);
            }
        }
    }

    @Override
    public int drawEnergy(int amount) {
        int result;
        if (amount > getStoredEnergy()) {
            result = getStoredEnergy();
            this.setEnergy(0);
        } else {
            this.setEnergy(getStoredEnergy() - amount);
            result = amount;
        }

        return result;
    }

    @Override
    public boolean addEnergy(int energy) {
        this.setEnergy(this.getStoredEnergy() + energy);
        return true;
    }

    public int getSignature() {
        return signature;
    }

    public void setSignature(int signature) {
        this.signature = signature;
    }

    public Vector3 getPosition() {
        return new Vector3(posX, posY, posZ);
    }

}
