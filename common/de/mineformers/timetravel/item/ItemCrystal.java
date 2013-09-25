/**
 * Time Travel
 *
 * ItemCrystall
 *
 * @author Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
package de.mineformers.timetravel.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCrystal extends ItemTT {

	/**
	 * @param id
	 * @param name
	 */
	public ItemCrystal(int id, String name) {
		super(id, name);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}


	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World par2World,
			EntityPlayer par3EntityPlayer0) {
		if(itemStack.getItemDamage() == 0)
			return itemStack;
		if (itemStack.stackTagCompound == null)
			itemStack.setTagCompound(new NBTTagCompound());

		itemStack.stackTagCompound.setInteger("maxStorage", 2000);

		return itemStack;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int i = 0; i < 2; i++) {
			ItemStack stack  = new ItemStack(id, 1, i);
			if(stack.getItemDamage() == 1) {
				if (stack.stackTagCompound == null)
					stack.setTagCompound(new NBTTagCompound());
	
				stack.stackTagCompound.setInteger("maxStorage", 2000);
			}
			list.add(stack);
		}
	}

}
