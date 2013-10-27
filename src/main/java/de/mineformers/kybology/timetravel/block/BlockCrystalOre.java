package de.mineformers.kybology.timetravel.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.kybology.KybologyCore;
import de.mineformers.kybology.timetravel.lib.Strings;
import de.mineformers.kybology.timetravel.tileentity.TileCrystalOre;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Kybology
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
        this.setCreativeTab(KybologyCore.tabKybology);
        this.setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.7F, 0.8F);
        this.setLightOpacity(0);
        this.setLightValue(0.75F);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int meta) {
        super.onNeighborBlockChange(world, x, y, z, meta);
        if (!canBlockStay(world, x, y, z)) {
            world.destroyBlock(x, y, z, true);
        }
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return world.getBlockId(x, y - 1, z) != 0;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.getBlockId(x, y - 1, z) != 0;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(int id, CreativeTabs tab, List subItems) {
        subItems.add(new ItemStack(id, 1, 0));
        subItems.add(new ItemStack(id, 1, 1));
        subItems.add(new ItemStack(id, 1, 2));
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
    
    @Override
    public int damageDropped(int dmg) {
        return dmg;
    }

}
