package de.mineformers.timetravel.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import de.mineformers.timetravel.lib.RenderIds;

/**
 * TimeTravel
 * 
 * BlockTestRenderer
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class BlockTestRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
	        RenderBlocks renderer) {

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
	        Block block, int modelId, RenderBlocks renderer) {
		Icon icon = renderer.getBlockIcon(block);

		renderer.setOverrideBlockTexture(icon);

		Tessellator tess = Tessellator.instance;

		// Render top right
		tess.addVertexWithUV(x, y + 1F, z, icon.getInterpolatedU(0),
		        icon.getInterpolatedV(0));
		tess.addVertexWithUV(x, y + 1F, z + 0.1875F, icon.getInterpolatedU(0),
		        icon.getInterpolatedV(3));
		tess.addVertexWithUV(x + 0.1875F, y + 1F, z + 0.1875F,
		        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
		tess.addVertexWithUV(x + 0.1875F, y + 1F, z, icon.getInterpolatedU(3),
		        icon.getInterpolatedV(0));

		// Render top left
		tess.addVertexWithUV(x, y + 1F, z + 1F, icon.getInterpolatedU(0),
		        icon.getInterpolatedV(0));
		tess.addVertexWithUV(x + 0.1875F, y + 1F, z + 1, icon.getInterpolatedU(0),
		        icon.getInterpolatedV(3));
		tess.addVertexWithUV(x + 0.1875F, y + 1F, z + 0.8125F,
		        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
		tess.addVertexWithUV(x, y + 1F, z + 0.8125F, icon.getInterpolatedU(3),
		        icon.getInterpolatedV(0));

		// Render bottom right
		tess.addVertexWithUV(x + 1F, y + 1F, z + 0F, icon.getInterpolatedU(0),
		        icon.getInterpolatedV(0));
		tess.addVertexWithUV(x + 0.8125F, y + 1F, z, icon.getInterpolatedU(0),
		        icon.getInterpolatedV(3));
		tess.addVertexWithUV(x + 0.8125F, y + 1F, z + 0.1875F,
		        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
		tess.addVertexWithUV(x + 1F, y + 1F, z + 0.1875F,
		        icon.getInterpolatedU(3), icon.getInterpolatedV(0));

		// Render bottom left
		tess.addVertexWithUV(x + 1F, y + 1F, z + 1F, icon.getInterpolatedU(0),
		        icon.getInterpolatedV(0));
		tess.addVertexWithUV(x + 1F, y + 1F, z + 0.8125F,
		        icon.getInterpolatedU(3), icon.getInterpolatedV(0));
		tess.addVertexWithUV(x + 0.8125F, y + 1F, z + 0.8125F,
		        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
		tess.addVertexWithUV(x + 0.8125F, y + 1F, z + 1F,
		        icon.getInterpolatedU(0), icon.getInterpolatedV(3));

		renderer.clearOverrideBlockTexture();

		/*
		 * int bottomBlock = world.getBlockId(x, y - 1, z); if (bottomBlock != 0
		 * && bottomBlock != BlockIds.TEST) {
		 * renderer.renderBlockByRenderType(Block.blocksList[bottomBlock], x, y,
		 * z); } if (bottomBlock == BlockIds.TEST) { for (int yOff = 1; (y -
		 * yOff) > 0; yOff++) { bottomBlock = world.getBlockId(x, y - yOff, z);
		 * if (bottomBlock != 0 && bottomBlock != BlockIds.TEST) {
		 * renderer.renderBlockByRenderType( Block.blocksList[bottomBlock], x,
		 * y, z); break; } } }
		 */
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return RenderIds.testRender;
	}

}
