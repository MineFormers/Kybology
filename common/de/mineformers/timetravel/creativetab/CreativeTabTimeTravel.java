package de.mineformers.timetravel.creativetab;

import de.mineformers.timetravel.lib.ItemIds;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * TimeTravel
 * 
 * CreativeTabTimeTravel
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class CreativeTabTimeTravel extends CreativeTabs {

	public CreativeTabTimeTravel(int id, String label) {
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
