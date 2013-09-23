package de.mineformers.timetravel.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.timetravel.TimeTravel;
import de.mineformers.timetravel.entity.EntityRift;
import de.mineformers.timetravel.lib.Strings;
import de.mineformers.timetravel.tileentity.TileEntityEnergyExtractor;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockEnergyExtractor extends BlockContainer {

	public BlockEnergyExtractor(int id, Material material) {
		super(id, material);
		this.setUnlocalizedName(Strings.RESOURCE_PREFIX + "extractor");
		this.setCreativeTab(TimeTravel.tabTimeTravel);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityEnergyExtractor();
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	    this.blockIcon = iconRegister.registerIcon("timetravel:connected");
	}
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(world.isRemote) {
			player.addChatMessage("Energy: " + ((TileEntityEnergyExtractor)world.getBlockTileEntity(x, y, z)).getStoredEnergy());
		} else {
			@SuppressWarnings("unchecked")
			List<Entity> riftList = world.getEntitiesWithinAABB(EntityRift.class, AxisAlignedBB.getBoundingBox(x-25,y-25,z-25,x+25,y+25,z+25));
			for(Entity temp : riftList) {
				((TileEntityEnergyExtractor)world.getBlockTileEntity(x, y, z)).addEnergy(((EntityRift)temp).drawEnergy(5));
				System.out.println("Rift found with " + ((EntityRift)temp).getStoredEnergy() + " Energy");
				
			}
		}
		return true;
	}
	protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
		return unlocalizedName.substring(unlocalizedName.indexOf(":") + 1);
	}
}
