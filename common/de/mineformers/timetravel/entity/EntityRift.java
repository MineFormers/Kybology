package de.mineformers.timetravel.entity;

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
	public EntityRift(World world) {
		super(world);
		setSize(1.5F, 0.6F);
	}

	@Override
	public void writeSpawnData(ByteArrayDataOutput data) {
		data.writeInt(getType());
		data.writeInt(getStoredEnergy());
		data.writeInt(getMaximumEnergy());
	}

	@Override
	public void readSpawnData(ByteArrayDataInput data) {
		setType(data.readInt());
		setEnergy(data.readInt());
		setMaximumEnergy(data.readInt());
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		setType(compound.getInteger("type"));
		setEnergy(compound.getInteger("power"));
		setMaximumEnergy(compound.getInteger("maxStorage"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("type", getType());
		compound.setInteger("power", getStoredEnergy());
		compound.setInteger("maxStorage", getMaximumEnergy());
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
		this.energy += energy;
	}
	
}
