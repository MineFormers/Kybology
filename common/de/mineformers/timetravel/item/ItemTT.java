package de.mineformers.timetravel.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.timetravel.TimeTravel;
import de.mineformers.timetravel.lib.Reference;
import de.mineformers.timetravel.lib.Strings;

/**
 * TimeTravel
 * 
 * ItemTT
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemTT extends Item {

	public ItemTT(int id, String name) {
		super(id - Reference.SHIFTED_ID_RANGE_CORRECTION);
		this.setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
		this.setCreativeTab(TimeTravel.tabTimeTravel);
	}

	public ItemTT(int id) {
		super(id - Reference.SHIFTED_ID_RANGE_CORRECTION);
		this.setCreativeTab(TimeTravel.tabTimeTravel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Reference.MOD_ID.toLowerCase()
				+ ":"
				+ super.getUnlocalizedName().substring(
						super.getUnlocalizedName().indexOf(":") + 1));
	}
}
