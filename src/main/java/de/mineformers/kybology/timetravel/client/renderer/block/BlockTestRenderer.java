package de.mineformers.kybology.timetravel.client.renderer.block;

import java.util.Arrays;
import java.util.HashSet;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.collect.Sets;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import de.mineformers.kybology.timetravel.lib.RenderIds;

/**
 * Kybology
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
            Block block, int modelId, RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;

        tess.draw();
        
        GL11.glPushMatrix();
        tess.startDrawingQuads();

        Icon icon = renderer.getBlockIcon(block);

        renderer.setOverrideBlockTexture(icon);

        HashSet<ForgeDirection> connections = new HashSet<ForgeDirection>();

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (world.getBlockId(x + dir.offsetX, y + dir.offsetY, z
                    + dir.offsetZ) == block.blockID)
                connections.add(dir);
        }
        if (connections.size() == 0) {
            renderer.renderStandardBlock(block, x, y, z);
            tess.draw();
            tess.startDrawingQuads();
            GL11.glScalef(-1, -1, -1);
            renderer.renderStandardBlock(block, x, y, z);
        } else {
            if (!connections.contains(ForgeDirection.UP)) {
                if (Sets.intersection(
                        connections,
                        new HashSet(Arrays.asList(ForgeDirection.EAST,
                                ForgeDirection.WEST, ForgeDirection.SOUTH,
                                ForgeDirection.NORTH))).isEmpty()) {
                    renderer.renderFaceYPos(block, x, y, z, icon);
                    renderer.renderFaceYNeg(block, x, y + 1, z, icon);
                } else {
                    if (!connections.contains(ForgeDirection.NORTH)
                            && !connections.contains(ForgeDirection.EAST))
                        renderTopCorner(icon, x, y, z, 2, true);
                    if (!connections.contains(ForgeDirection.SOUTH)
                            && !connections.contains(ForgeDirection.WEST))
                        renderTopCorner(icon, x, y, z, 1, true);
                    if (!connections.contains(ForgeDirection.SOUTH)
                            && !connections.contains(ForgeDirection.EAST))
                        renderTopCorner(icon, x, y, z, 3, true);
                    if (!connections.contains(ForgeDirection.NORTH)
                            && !connections.contains(ForgeDirection.WEST))
                        renderTopCorner(icon, x, y, z, 0, true);
                }
            }
            if (!connections.contains(ForgeDirection.DOWN)) {
                if (Sets.intersection(
                        connections,
                        new HashSet(Arrays.asList(ForgeDirection.EAST,
                                ForgeDirection.WEST, ForgeDirection.SOUTH,
                                ForgeDirection.NORTH))).isEmpty()) {
                    renderer.renderFaceYPos(block, x, y - 1, z, icon);
                    renderer.renderFaceYNeg(block, x, y, z, icon);
                } else {
                    if (!connections.contains(ForgeDirection.NORTH)
                            && !connections.contains(ForgeDirection.EAST))
                        renderBottomCorner(icon, x, y - 1, z, 2, true);
                    if (!connections.contains(ForgeDirection.SOUTH)
                            && !connections.contains(ForgeDirection.WEST))
                        renderBottomCorner(icon, x, y - 1, z, 1, true);
                    if (!connections.contains(ForgeDirection.SOUTH)
                            && !connections.contains(ForgeDirection.EAST))
                        renderBottomCorner(icon, x, y - 1, z, 3, true);
                    if (!connections.contains(ForgeDirection.NORTH)
                            && !connections.contains(ForgeDirection.WEST))
                        renderBottomCorner(icon, x, y - 1, z, 0, true);
                }
            }
            if (!connections.contains(ForgeDirection.EAST)) {
                if (Sets.intersection(
                        connections,
                        new HashSet(Arrays.asList(ForgeDirection.DOWN,
                                ForgeDirection.UP, ForgeDirection.SOUTH,
                                ForgeDirection.NORTH))).isEmpty()) {
                    renderer.renderFaceXPos(block, x, y, z, icon);
                    renderer.renderFaceXNeg(block, x + 1, y, z, icon);
                }
            }
            if (!connections.contains(ForgeDirection.WEST)) {
                if (Sets.intersection(
                        connections,
                        new HashSet(Arrays.asList(ForgeDirection.DOWN,
                                ForgeDirection.UP, ForgeDirection.SOUTH,
                                ForgeDirection.NORTH))).isEmpty()) {
                    renderer.renderFaceXPos(block, x - 1, y, z, icon);
                    renderer.renderFaceXNeg(block, x, y, z, icon);
                }
            }
            if (!connections.contains(ForgeDirection.SOUTH)) {
                if (Sets.intersection(
                        connections,
                        new HashSet(Arrays.asList(ForgeDirection.DOWN,
                                ForgeDirection.UP, ForgeDirection.EAST,
                                ForgeDirection.WEST))).isEmpty()) {
                    renderer.renderFaceZPos(block, x, y, z, icon);
                    renderer.renderFaceZNeg(block, x, y, z + 1, icon);
                }
            }
            if (!connections.contains(ForgeDirection.NORTH)) {
                if (Sets.intersection(
                        connections,
                        new HashSet(Arrays.asList(ForgeDirection.DOWN,
                                ForgeDirection.UP, ForgeDirection.EAST,
                                ForgeDirection.WEST))).isEmpty()) {
                    renderer.renderFaceZPos(block, x, y, z - 1, icon);
                    renderer.renderFaceZNeg(block, x, y, z, icon);
                }
            }
        }

        renderer.clearOverrideBlockTexture();
        tess.draw();
        GL11.glPopMatrix();
        tess.startDrawingQuads();
        return false;
    }

    private void renderTopCorner(Icon icon, int x, int y, int z, int corner,
            boolean renderBottom) {
        Tessellator tess = Tessellator.instance;
        switch (corner) {
            case 0:
                // Render top right
                tess.addVertexWithUV(x, y + 1F, z, icon.getInterpolatedU(0),
                        icon.getInterpolatedV(0));
                tess.addVertexWithUV(x, y + 1F, z + 0.1875F,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(3));
                tess.addVertexWithUV(x + 0.1875F, y + 1F, z + 0.1875F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
                tess.addVertexWithUV(x + 0.1875F, y + 1F, z,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(0));
                break;
            case 1:
                // Render top left
                tess.addVertexWithUV(x, y + 1F, z + 1F,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(0));
                tess.addVertexWithUV(x + 0.1875F, y + 1F, z + 1,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(3));
                tess.addVertexWithUV(x + 0.1875F, y + 1F, z + 0.8125F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
                tess.addVertexWithUV(x, y + 1F, z + 0.8125F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(0));
                break;
            case 2:
                // Render bottom right
                tess.addVertexWithUV(x + 1F, y + 1F, z + 0F,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(0));
                tess.addVertexWithUV(x + 0.8125F, y + 1F, z,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(3));
                tess.addVertexWithUV(x + 0.8125F, y + 1F, z + 0.1875F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
                tess.addVertexWithUV(x + 1F, y + 1F, z + 0.1875F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(0));
                break;
            case 3:
                // Render bottom left
                tess.addVertexWithUV(x + 1F, y + 1F, z + 1F,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(0));
                tess.addVertexWithUV(x + 1F, y + 1F, z + 0.8125F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(0));
                tess.addVertexWithUV(x + 0.8125F, y + 1F, z + 0.8125F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
                tess.addVertexWithUV(x + 0.8125F, y + 1F, z + 1F,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(3));
                break;
        }
        if (renderBottom)
            renderBottomCorner(icon, x, y, z, corner, false);
    }

    private void renderBottomCorner(Icon icon, int x, int y, int z, int corner,
            boolean renderTop) {
        Tessellator tess = Tessellator.instance;
        switch (corner) {
            case 0:
                // Render top right
                tess.addVertexWithUV(x, y + 1F, z + 0.1875F,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(3)); // 2
                tess.addVertexWithUV(x, y + 1F, z, icon.getInterpolatedU(0),
                        icon.getInterpolatedV(0)); // 1
                tess.addVertexWithUV(x + 0.1875F, y + 1F, z,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(0)); // 4
                tess.addVertexWithUV(x + 0.1875F, y + 1F, z + 0.1875F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(3)); // 3
                break;
            case 1:
                // Render top left
                tess.addVertexWithUV(x + 0.1875F, y + 1F, z + 1,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(3));
                tess.addVertexWithUV(x, y + 1F, z + 1F,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(0));
                tess.addVertexWithUV(x, y + 1F, z + 0.8125F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(0));
                tess.addVertexWithUV(x + 0.1875F, y + 1F, z + 0.8125F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
                break;
            case 2:
                // Render bottom right
                tess.addVertexWithUV(x + 0.8125F, y + 1F, z,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(3));
                tess.addVertexWithUV(x + 1F, y + 1F, z + 0F,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(0));
                tess.addVertexWithUV(x + 1F, y + 1F, z + 0.1875F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(0));
                tess.addVertexWithUV(x + 0.8125F, y + 1F, z + 0.1875F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
                break;
            case 3:
                // Render bottom left
                tess.addVertexWithUV(x + 1F, y + 1F, z + 0.8125F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(0));
                tess.addVertexWithUV(x + 1F, y + 1F, z + 1F,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(0));
                tess.addVertexWithUV(x + 0.8125F, y + 1F, z + 1F,
                        icon.getInterpolatedU(0), icon.getInterpolatedV(3));
                tess.addVertexWithUV(x + 0.8125F, y + 1F, z + 0.8125F,
                        icon.getInterpolatedU(3), icon.getInterpolatedV(3));
                break;
        }
        if (renderTop)
            renderTopCorner(icon, x, y, z, corner, false);
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
