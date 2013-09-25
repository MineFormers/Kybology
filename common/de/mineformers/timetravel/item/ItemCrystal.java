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
		this.setMaxDamage(2000);
	}

	@Override
	public void onCreated(ItemStack itemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		if (itemStack.stackTagCompound == null)
			itemStack.setTagCompound(new NBTTagCompound());

		itemStack.stackTagCompound.setInteger("maxStorage", 2000);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World par2World,
			EntityPlayer par3EntityPlayer0) {
		if (itemStack.stackTagCompound == null)
			itemStack.setTagCompound(new NBTTagCompound());

		itemStack.stackTagCompound.setInteger("maxStorage", 2000);

		return itemStack;
	}

}
