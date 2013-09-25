package de.mineformers.timetravel.block;

import de.mineformers.timetravel.TimeTravel;
import de.mineformers.timetravel.lib.Strings;
import de.mineformers.timetravel.tileentity.TileCrystalOre;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * TimeTravel
 * 
 * BlockCrystalOre
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class BlockCrystalOre extends Block implements ITileEntityProvider {

	public BlockCrystalOre(int id) {
		super(id, Material.rock);
		this.setUnlocalizedName(Strings.RESOURCE_PREFIX
				+ Strings.CRYSTAL_ORE_NAME);
		this.setCreativeTab(TimeTravel.tabTimeTravel);
		this.setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.7F, 0.8F);
		this.setLightOpacity(0);
		this.setLightValue(0.75F);
	}

	protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(":") + 1);
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = Block.glass.getIcon(0, 0);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileCrystalOre();
	}

}
