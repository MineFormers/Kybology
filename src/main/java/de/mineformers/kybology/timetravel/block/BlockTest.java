package de.mineformers.kybology.timetravel.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.kybology.core.block.BlockKybology;
import de.mineformers.kybology.core.client.particle.ModParticles;
import de.mineformers.kybology.core.util.Vector3;
import de.mineformers.kybology.timetravel.lib.RenderIds;
import de.mineformers.kybology.timetravel.lib.Strings;

/**
 * Kybology
 * 
 * BlockTest
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class BlockTest extends BlockKybology {

    public BlockTest(int id) {
        super(id, Material.rock, Strings.TEST_NAME);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("Kybology:connected");
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
        return RenderIds.testRender;
    }

    @Override
    public boolean onBlockActivated(final World world, final int x,
            final int y, final int z, EntityPlayer par5EntityPlayer, int par6,
            float par7, float par8, float par9) {
        if (world.isRemote) {
            ModParticles.ENERGY_TRAIL.spawnParticles(world, x, y, z,
                    new Vector3(x + 20, y + 20, z + 20));
        }

        return true;
    }
}
