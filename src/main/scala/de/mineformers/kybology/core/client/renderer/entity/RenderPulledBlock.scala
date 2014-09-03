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

package de.mineformers.kybology.core.client.renderer.entity

import de.mineformers.kybology.core.entity.EntityPulledBlock
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.entity.Entity
import net.minecraft.util.{MathHelper, ResourceLocation}
import net.minecraftforge.client.MinecraftForgeClient
import org.lwjgl.opengl.GL11

/**
 * RenderPulledBlock
 *
 * @author PaleoCrafter
 */
class RenderPulledBlock extends Render {
  override def doRender(entity: Entity, x: Double, y: Double, z: Double, partialTicks: Float, p_76986_9_ : Float): Unit = {
    val pulled = entity.asInstanceOf[EntityPulledBlock]
    GL11.glPushMatrix()
    GL11.glTranslated(x, y, z)
    bindEntityTexture(entity)
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glDisable(GL11.GL_ALPHA_TEST)
    GL11.glDisable(GL11.GL_LIGHTING)
    val tess = Tessellator.instance
    this.field_147909_c.blockAccess = entity.worldObj
    val block = pulled.block
    tess.startDrawingQuads()
    tess.setTranslation(-MathHelper.floor_double(entity.posX) - 0.5F, -MathHelper.floor_double(entity.posY) - 0.5F, -MathHelper.floor_double(entity.posZ) - 0.5F)
    if (block.canRenderInPass(MinecraftForgeClient.getRenderPass)) {
      if (block.getRenderType == -1 || !this.field_147909_c.renderBlockByRenderType(block, MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ))) {
        block.setBlockBoundsBasedOnState(pulled.worldObj, MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ))
        this.field_147909_c.setRenderBoundsFromBlock(block)
        this.field_147909_c.renderStandardBlock(block, MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ))
      }
    }
    tess.setTranslation(0, 0, 0)
    tess.draw()
    GL11.glDisable(GL11.GL_BLEND)
    GL11.glEnable(GL11.GL_ALPHA_TEST)
    GL11.glEnable(GL11.GL_LIGHTING)
    GL11.glPopMatrix()
  }

  override def getEntityTexture(entity: Entity): ResourceLocation = TextureMap.locationBlocksTexture
}
