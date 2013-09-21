package de.mineformers.timetravel.travelling.timemachine;

import java.lang.reflect.Field;

import de.mineformers.timetravel.lib.Textures;
import de.mineformers.timetravel.network.packet.PacketTMModuleUpdate;
import de.mineformers.timetravel.network.packet.PacketTimeMachineUpdate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

/**
 * TimeTravel
 * 
 * TMPartModule
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TMPartModule extends TimeMachinePart {

	public enum ModuleType {
		DEFAULT, POWER, TIME, SPACE, ITEM, PLAYER
	}

	private ModuleType type;

	public TMPartModule(TileEntity parent) {
		super(TimeMachinePart.TYPE_MODULE, parent);
		this.type = ModuleType.DEFAULT;
	}

	public ModuleType getType() {
		return type;
	}

	public void setType(ModuleType type) {
		this.type = type;
	}

	public boolean switchType(ItemStack stack) {
		if (stack.itemID == Item.eyeOfEnder.itemID) {
			type = ModuleType.POWER;
			return true;
		} else if (stack.itemID == Item.pocketSundial.itemID) {
			type = ModuleType.TIME;
			return true;
		} else if (stack.itemID == Item.compass.itemID) {
			type = ModuleType.SPACE;
			return true;
		} else if (stack.itemID == Item.skull.itemID
		        && stack.getItemDamage() == 3) {
			type = ModuleType.PLAYER;
			return true;
		}

		return false;
	}

	public ItemStack getTypeItem(ModuleType type) {
		switch (type) {
			case POWER:
				return new ItemStack(Item.eyeOfEnder.itemID, 1, 0);
			case TIME:
				return new ItemStack(Item.pocketSundial.itemID, 1, 0);
			case SPACE:
				return new ItemStack(Item.compass.itemID, 1, 0);
			case PLAYER:
				return new ItemStack(Item.skull.itemID, 1, 3);
			default:
				return null;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("ModuleType", type.ordinal());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		type = ModuleType.values()[compound.getInteger("ModuleType")];
	}

	@Override
	public PacketTimeMachineUpdate getPacket() {
		return new PacketTMModuleUpdate((int) this.getPos().xCoord,
		        (int) this.getPos().yCoord, (int) this.getPos().zCoord,
		        ForgeDirection.SOUTH, (byte) 0, null, this.isValidMultiblock(),
		        this.getTypeMeta(), type.ordinal());
	}

	public ResourceLocation getTexture() {
		try {
			Field field = Textures.class.getField("MODEL_TM_MODULE_"
			        + type.toString());

			return (ResourceLocation) field.get(null);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(
			        "Unexpected Reflection exception during Packet construction!",
			        e);
		}
	}

}
