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

package de.mineformers.kybology.core.client.renderer.item

import java.util.Random

import de.mineformers.core.client.util.{RenderUtils, Color}
import de.mineformers.core.util.MathUtils
import de.mineformers.core.util.world.Vector3
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.{Tessellator, RenderHelper}
import net.minecraft.item.ItemStack
import net.minecraftforge.client.IItemRenderer
import net.minecraftforge.client.IItemRenderer.{ItemRendererHelper, ItemRenderType}
import org.lwjgl.opengl.GL11

/**
 * RiftItemRenderer
 *
 * @author PaleoCrafter
 */
class RiftItemRenderer extends IItemRenderer {
  override def handleRenderType(item: ItemStack, `type`: ItemRenderType): Boolean = true

  override def shouldUseRenderHelper(`type`: ItemRenderType, item: ItemStack, helper: ItemRendererHelper): Boolean = helper != ItemRendererHelper.ENTITY_ROTATION && helper != ItemRendererHelper.ENTITY_BOBBING

  import ItemRenderType._

  override def renderItem(`type`: ItemRenderType, item: ItemStack, data: AnyRef*): Unit = `type` match {
    case ENTITY => render(1, -0.5, 0, -0.5, beams = true)
    case INVENTORY => render(0.5F, 0, 0, 0, rotateFacing = false, rotation = Vector3(90, -45, 0), beams = true)
    case EQUIPPED_FIRST_PERSON => render(1, 0, 0, 0, rotateFacing = false, rotation = Vector3(0, 90, 0), beams = true)
    case _ => render(1, 0, 0, 0, beams = true)
  }

  def render(totalScale: Float, x: Double, y: Double, z: Double, beams: Boolean = false, rotation: Vector3 = Vector3.Zero, rotateFacing: Boolean = true): Unit = {
    GL11.glPushMatrix()
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    GL11.glTranslated(x + 0.5D, y + 0.5D, z + 0.5D)

    GL11.glScalef(totalScale, totalScale, totalScale)

    if (beams) {
      // Beams setup
      val random: Random = new Random(432L)
      GL11.glDisable(GL11.GL_LIGHTING)
      GL11.glShadeModel(GL11.GL_SMOOTH)
      GL11.glEnable(GL11.GL_BLEND)
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
      GL11.glDisable(GL11.GL_ALPHA_TEST)
      GL11.glEnable(GL11.GL_CULL_FACE)
      GL11.glDepthMask(false)
      val step: Float = (Minecraft.getSystemTime % 700000L) / 73000.0F
      val lengthFactor: Float = (step / 0.2F) + 10

      // Beams rendering
      GL11.glPushMatrix()
      GL11.glRotated(720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL, 0, 1, 0)
      val tessellator = Tessellator.instance
      val scale = 1 / 150F
      val speed = 5
      for (i <- 0 until 12) {
        GL11.glRotatef(random.nextFloat * 360.0F, 1.0F, 0.0F, 0.0F)
        GL11.glRotatef(random.nextFloat * 360.0F, 0.0F, 1.0F, 0.0F)
        GL11.glRotatef(random.nextFloat * 360.0F, 0.0F, 0.0F, 1.0F)
        GL11.glRotatef(random.nextFloat * 360.0F, 1.0F, 0.0F, 0.0F)
        GL11.glRotatef(random.nextFloat * 360.0F, 0.0F, 1.0F, 0.0F)
        GL11.glRotatef(random.nextFloat * 360.0F + step * speed * 2 * 90.0F, 0.0F, 0.0F, 1.0F)
        tessellator.startDrawing(6)
        val length: Float = (clampedRandom(random) * 20.0F + 5.0F + lengthFactor * 10.0F) * scale
        val horOff: Float = (clampedRandom(random) * 2.0F + 1.0F + lengthFactor * 2.0F) * scale
        val color = Color.fromHSV(1, 0, random.nextFloat()).integer
        tessellator.setColorRGBA_I(color, 127)
        tessellator.addVertex(0.0D, 0.0D, 0.0D)
        tessellator.setColorRGBA_I(color, 0)
        tessellator.addVertex(-0.866D * horOff, length, -0.5F * horOff)
        tessellator.addVertex(0.866D * horOff, length, -0.5F * horOff)
        tessellator.addVertex(0.0D, length, 1.0F * horOff)
        tessellator.addVertex(-0.866D * horOff, length, -0.5F * horOff)
        tessellator.setColorRGBA_F(1, 1, 1, 1)
        tessellator.draw()
      }
      GL11.glPopMatrix()

      GL11.glDepthMask(true)
      GL11.glDisable(GL11.GL_CULL_FACE)
      GL11.glEnable(GL11.GL_LIGHTING)
      GL11.glDisable(GL11.GL_BLEND)
      GL11.glShadeModel(GL11.GL_FLAT)
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
      GL11.glEnable(GL11.GL_ALPHA_TEST)
    }

    // Sphere rendering
    GL11.glPushMatrix()
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glColor4f(0, 0, 0, MathUtils.pulsate(Minecraft.getSystemTime % 2000, 0.1, 0.5, 2000).toFloat)
    if (rotateFacing)
      RenderUtils.rotateFacing()
    else {
      GL11.glRotated(rotation.x, 1, 0, 0)
      GL11.glRotated(rotation.y, 0, 1, 0)
      GL11.glRotated(rotation.z, 0, 0, 1)
    }
    RenderUtils.drawCircle(0, 0, 0, 0.5, 24)
    GL11.glDisable(GL11.GL_BLEND)
    GL11.glPopMatrix()

    GL11.glEnable(GL11.GL_TEXTURE_2D)
    GL11.glPopMatrix()
  }

  def clampedRandom(random: Random): Float = MathUtils.scale(random.nextFloat(), 0, 1, 0, 0.45).toFloat + 0.4F
}
