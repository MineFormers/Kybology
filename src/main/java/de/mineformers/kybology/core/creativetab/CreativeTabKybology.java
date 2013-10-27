package de.mineformers.kybology.core.creativetab;

import de.mineformers.kybology.timetravel.lib.ItemIds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Kybology
 * 
 * CreativeTabKybology
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class CreativeTabKybology extends CreativeTabs {

	public CreativeTabKybology(int id, String label) {
		super(id, label);
	}

	@Override
	public ItemStack getIconItemStack() {
		ItemStack stack = new ItemStack(ItemIds.WATCH, 1, 0);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setBoolean("Travelling", true);
		return stack;
	}

}
