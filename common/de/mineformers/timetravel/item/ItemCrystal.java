
package de.mineformers.timetravel.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.timetravel.lib.CrystalProperties;
import de.mineformers.timetravel.lib.Reference;
import de.mineformers.timetravel.lib.Strings;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
/**
 * Time Travel
 *
 * ItemCrystall
 *
 * @author Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
public class ItemCrystal extends ItemTT {


	/**
	 * @param id
	 * @param name
	 */
	public ItemCrystal(int id) {
		super(id);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase()
		        + ":crystal");
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		CrystalProperties crystallProperties = null;
		for(int i = 0;i<21;i+=3) {
			switch(i) {
			case 9:
			case 12:
				crystallProperties = CrystalProperties.STORAGE;
				break;
			case 15:
			case 18:
				crystallProperties = CrystalProperties.TRANSFER;
				break;
			default:
				crystallProperties = CrystalProperties.NOTHING;

			}
			for(int ii = 0;ii<3;ii++) {
				ItemStack stack  = new ItemStack(id, 1, i+ii);
				if(crystallProperties != CrystalProperties.NOTHING) {
					if (stack.stackTagCompound == null)
						stack.setTagCompound(new NBTTagCompound());
					stack.stackTagCompound.setInteger(crystallProperties.getValueName(), crystallProperties.getValue(ii));
				}
				list.add(stack);
			}
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "item." + Strings.RESOURCE_PREFIX + Strings.CRYSTAL_NAME[itemstack.getItemDamage()];
		
	}

}
