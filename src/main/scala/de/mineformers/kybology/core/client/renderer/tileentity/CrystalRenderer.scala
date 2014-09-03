/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 MineFormers
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.mineformers.kybology.core.client.renderer.tileentity

import de.mineformers.core.client.renderer.TileRenderer
import de.mineformers.kybology.Core
import de.mineformers.kybology.core.tileentity.TileCrystal
import net.minecraft.client.renderer.Tessellator
import net.minecraftforge.client.MinecraftForgeClient
import net.minecraftforge.client.model.AdvancedModelLoader
import org.lwjgl.opengl.GL11

/**
 * CrystalRenderer
 *
 * @author PaleoCrafter
 */
class CrystalRenderer extends TileRenderer[TileCrystal] {
  final val model = AdvancedModelLoader.loadModel(Core("models/crystal_ore.obj"))
  final val texture = Core.texture("blocks", "crystal")

  override def render(tile: TileCrystal, x: Double, y: Double, z: Double, partialTicks: Float): Unit = {
    GL11.glTranslatef(0.5f, 0.5f, 1.5f)
    import net.minecraftforge.common.util.ForgeDirection._
    Core.blocks.crystal.getRotation(tile.world, tile.x, tile.y, tile.z) match {
      case DOWN => GL11.glRotatef(180, 0, 0, 1)
      case WEST => GL11.glRotatef(90, 0, 0, 1)
      case EAST => GL11.glRotatef(90, 0, 0, -1)
      case NORTH =>
        GL11.glTranslatef(0, 1, -1)
        GL11.glRotatef(90, -1, 0, 0)
      case SOUTH =>
        GL11.glTranslatef(0, -1, -1)
        GL11.glRotatef(90, 1, 0, 0)
      case _ =>
    }
    GL11.glTranslatef(-0.5f, -0.5f, -0.5f)
    Tessellator.instance.setBrightness(tile.world.getBlock(tile.x, tile.y, tile.z).getMixedBrightnessForBlock(tile.world, tile.x, tile.y, tile.z))
    bindTexture(texture)
    if (MinecraftForgeClient.getRenderPass == 0)
      model.renderOnly("Stone")
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glColor4f(0, 0.5f, 1, 0.9F)
    if (MinecraftForgeClient.getRenderPass == 1)
      model.renderAllExcept("Stone")
    GL11.glDisable(GL11.GL_BLEND)
  }
}
