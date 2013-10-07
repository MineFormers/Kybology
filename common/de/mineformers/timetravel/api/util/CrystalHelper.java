package de.mineformers.timetravel.api.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import de.mineformers.timetravel.api.energy.CrystalType;

/**
 * TimeTravel
 * 
 * CrystalHelper
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class CrystalHelper {

    public static final String NBT_CRYSTAL_QUALITY = "Quality";
    public static final String NBT_CRYSTAL_COLOR = "Color";

    public static final String NBT_TRANSFER_SIGNATURE = "Signature";
    public static final String NBT_TRANSFER_RATE = "TransferRate";
    public static final String NBT_STORAGE_ENERGY = "Energy";

    public static final String[] CRYSTAL_COLORS = { "blue", "red", "gold" };
    public static final String[] CRYSTAL_QUALITIES = { "pure", "purified" };

    public static CrystalType getType(ItemStack stack) {
        stack = getItemStackDefaultTags(stack);
        int quality = stack.getTagCompound().getInteger(NBT_CRYSTAL_QUALITY);
        int color = stack.getTagCompound().getInteger(NBT_CRYSTAL_COLOR);
        CrystalType type = new CrystalType(stack.getItemDamage(), quality,
                color);
        switch (type.getFunction()) {
            case STORAGE:
                type.addData(NBT_STORAGE_ENERGY, stack.getTagCompound()
                        .getInteger(NBT_STORAGE_ENERGY));
                break;
            case TRANSFER:
                type.addData(NBT_TRANSFER_RATE, stack.getTagCompound()
                        .getInteger(NBT_TRANSFER_RATE));
                type.addData(NBT_TRANSFER_SIGNATURE, stack.getTagCompound()
                        .getInteger(NBT_TRANSFER_SIGNATURE));
                break;
            default:
                break;
        }
        return type;
    }

    public static CrystalType.Color getCrystalColor(ItemStack stack) {
        return getType(stack).getColor();
    }

    public static int getEnergyData(ItemStack stack) {
        CrystalType type = getType(stack);
        switch (type.getFunction()) {
            case NOTHING:
                return 0;
            case STORAGE:
                return (Integer) type.getData(NBT_STORAGE_ENERGY);
            case TRANSFER:
                return (Integer) type.getData(NBT_TRANSFER_RATE);
        }

        return 0;
    }

    public static int getSignature(ItemStack stack) {
        CrystalType type = getType(stack);
        switch (type.getFunction()) {
            case NOTHING:
                return 0;
            case STORAGE:
                return 0;
            case TRANSFER:
                return (Integer) type.getData(NBT_TRANSFER_SIGNATURE);
        }

        return 0;
    }

    public static ItemStack construct(ItemStack stack, CrystalType type) {
        stack.setItemDamage(type.getFunction().ordinal());
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger(NBT_CRYSTAL_QUALITY, type.getQuality().ordinal());
        compound.setInteger(NBT_CRYSTAL_COLOR, type.getColor().ordinal());
        stack.setTagCompound(compound);
        return getItemStackDefaultTags(stack);
    }

    public static ItemStack getItemStackDefaultTags(ItemStack stack) {
        if (stack.getTagCompound() == null) {
            CrystalType type = new CrystalType(CrystalType.Function.NOTHING,
                    CrystalType.Quality.PURE, CrystalType.Color.BLUE);
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger(NBT_CRYSTAL_QUALITY, type.getQuality()
                    .ordinal());
            compound.setInteger(NBT_CRYSTAL_COLOR, type.getColor().ordinal());
            stack.setItemDamage(2);
        } else if (!stack.getTagCompound().hasKey(NBT_CRYSTAL_QUALITY)
                || !stack.getTagCompound().hasKey(NBT_CRYSTAL_COLOR)) {
            CrystalType type = new CrystalType(CrystalType.Function.NOTHING,
                    CrystalType.Quality.PURE, CrystalType.Color.BLUE);
            stack.getTagCompound().setInteger(NBT_CRYSTAL_QUALITY,
                    type.getQuality().ordinal());
            stack.getTagCompound().setInteger(NBT_CRYSTAL_COLOR,
                    type.getColor().ordinal());
        }

        switch (stack.getItemDamage()) {
            case 0: {
                NBTTagCompound compound = stack.getTagCompound();
                if (!compound.hasKey(NBT_STORAGE_ENERGY))
                    compound.setInteger(
                            NBT_STORAGE_ENERGY,
                            getMaximumStorage(CrystalType.Color.values()[compound
                                    .getInteger(NBT_CRYSTAL_COLOR)]));
                break;
            }
            case 1: {
                NBTTagCompound compound = stack.getTagCompound();
                if (!compound.hasKey(NBT_TRANSFER_RATE))
                    compound.setInteger(NBT_TRANSFER_RATE,
                            getTransferRate(CrystalType.Color.values()[compound
                                    .getInteger(NBT_CRYSTAL_COLOR)]));
                if (!compound.hasKey(NBT_TRANSFER_SIGNATURE))
                    compound.setInteger(NBT_TRANSFER_SIGNATURE, 20);
                break;
            }
            case 2:
                break;
            default:
                stack.setItemDamage(2);
        }

        return stack;
    }

    public static int getMaximumStorage(CrystalType.Color color) {
        switch (color) {
            case BLUE:
                return 2000;
            case RED:
                return 5000;
            case GOLD:
                return 10000;
        }
        return 0;
    }

    public static int getTransferRate(CrystalType.Color color) {
        switch (color) {
            case BLUE:
                return 20;
            case RED:
                return 50;
            case GOLD:
                return 100;
        }
        return 0;
    }

}
