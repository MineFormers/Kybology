package de.mineformers.timetravel.entity;

import java.util.Random;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import de.mineformers.timetravel.api.energy.IEnergyDrawable;
import de.mineformers.timetravel.api.energy.IEnergyStorage;

/**
 * TimeTravel
 * 
 * EntityRift
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class EntityRift extends Entity implements IEntityAdditionalSpawnData, IEnergyStorage, IEnergyDrawable {
	private int type;
	private int energy;
	private int maxStorage;
	private int signature;
	public EntityRift(World world) {
		super(world);
		setSize(1.5F, 0.6F);
		
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(getType());
		data.writeInt(getStoredEnergy());
		data.writeInt(getMaximumEnergy());
		data.writeInt(signature);
//		signature.writeByteArrayDataOutput(data);
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		setType(data.readInt());
		setEnergy(data.readInt());
		setMaximumEnergy(data.readInt());
		signature = data.readInt();
//		setSignature(Signature.getSignature(data));
	}

	@Override
	protected void entityInit() {

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
		this.energy = power;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int getStoredEnergy() {
		return energy;
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
			if(new Random().nextInt(100) >90)
				this.addEnergy(1);

		}
	}

	@Override
	public int drawEnergy(int amount) {
		int result;
		if(amount > energy) {
			result = energy;
			this.setEnergy(0);
		} else {
			this.setEnergy(energy-amount);
			result = amount;
		}
		
		return result;
	}

	@Override
	public void addEnergy(int energy) {
            if (this.getStoredEnergy() + energy >= this.getMaximumEnergy()) {
                this.energy = this.getMaximumEnergy();
            } else {
                this.energy += energy;
            }
	}

//	public Signature getSignature() {
//		return signature;
//	}
//
//	public void setSignature(Signature signature) {
//		this.signature = signature;
//	}
	public int getSignature() {
		return signature;
	}

	public void setSignature(int signature) {
		this.signature = signature;
	}	
}
