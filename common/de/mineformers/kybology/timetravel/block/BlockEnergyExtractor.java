package de.mineformers.kybology.timetravel.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.kybology.KybologyCore;
import de.mineformers.kybology.KybologyTimeTravel;
import de.mineformers.kybology.core.block.BlockKybology;
import de.mineformers.kybology.core.entity.EntityRift;
import de.mineformers.kybology.core.util.NetworkHelper;
import de.mineformers.kybology.timetravel.lib.GuiIds;
import de.mineformers.kybology.timetravel.lib.Strings;
import de.mineformers.kybology.timetravel.tileentity.TileEnergyExtractor;

/**
 * Kybology
 * 
 * BlockEnergyExtractor
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class BlockEnergyExtractor extends BlockKybology {

    public BlockEnergyExtractor(int id, Material material) {
        super(id, material, Strings.ENERGY_EXTRACTOR_NAME);
        this.setCreativeTab(KybologyCore.tabKybology);
        this.setBlockBounds(0.1F, 0F, 0.1F, 0.9F, 0.95F, 0.9F);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEnergyExtractor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("Kybology:connected");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int hitX, float hitY, float hitZ, float par9) {
        if (player.isSneaking()) {
            if (!world.isRemote) {
                @SuppressWarnings("unchecked")
                List<Entity> riftList = world.getEntitiesWithinAABB(
                        EntityRift.class, AxisAlignedBB.getBoundingBox(x - 25,
                                y - 25, z - 25, x + 25, y + 25, z + 25));
                for (Entity temp : riftList) {
                    ((TileEnergyExtractor) world.getBlockTileEntity(x, y, z))
                            .addEnergy(((EntityRift) temp).drawEnergy(5));
                    player.addChatMessage("Rift found with "
                            + ((EntityRift) temp).getStoredEnergy() + " Energy");
                }

                NetworkHelper.sendTilePacket(world.getBlockTileEntity(x, y, z));
            }
        } else {
            if (!world.isRemote) {
                player.openGui(KybologyTimeTravel.instance, GuiIds.EXTRACTOR,
                        world, x, y, z);
            }
        }
        return true;
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

}
