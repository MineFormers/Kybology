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

import java.util.Random

import de.mineformers.core.client.renderer.TileRenderer
import de.mineformers.core.client.util.{RenderUtils, Color}
import de.mineformers.core.util.MathUtils
import de.mineformers.core.util.world.Vector2
import de.mineformers.kybology.core.tileentity.TileRift
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.{RenderHelper, Tessellator}
import net.minecraftforge.client.MinecraftForgeClient
import org.lwjgl.opengl.GL11

/**
 * RiftRenderer
 *
 * @author PaleoCrafter
 */
class RiftRenderer extends TileRenderer[TileRift] {

  override def render(tile: TileRift, x: Double, y: Double, z: Double, partialTicks: Float): Unit = {
    if(MinecraftForgeClient.getRenderPass == 1) {
      val decay = 1 / tile.scale.toFloat
      if (decay != 1) {
        GL11.glPushMatrix()
        GL11.glRotatef(90, 0, 0, 1)
        RenderUtils.drawBeaconBeam(0, -128, 0)
        GL11.glPopMatrix()
        GL11.glPushMatrix()
        GL11.glTranslatef(0, 1F, 0)
        GL11.glRotatef(90, 1, 0, 0)
        RenderUtils.drawBeaconBeam(0, -128, 0)
        GL11.glPopMatrix()
        GL11.glPushMatrix()
        RenderUtils.drawBeaconBeam(0, -128, 0)
        GL11.glPopMatrix()
      }

      GL11.glDisable(GL11.GL_TEXTURE_2D)
      GL11.glTranslated(0.5D, 0.5D, 0.5D)
      GL11.glPushMatrix()
      GL11.glScalef(1 / decay, 1 / decay, 1 / decay)
      // Beams setup
      RenderUtils.drawBeams(tile.storedEnergy / 20F)
      GL11.glPopMatrix()

      // Sphere rendering
      GL11.glPushMatrix()
      GL11.glEnable(GL11.GL_BLEND)
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
      GL11.glColor4f(0, 0, 0, MathUtils.pulsate(Minecraft.getSystemTime % 2000, 0.1, 0.5, 2000).toFloat)
      GL11.glScalef(decay, decay, decay)
      RenderUtils.rotateFacing()
      RenderUtils.drawCircle(0, 0, 0, 0.5 + MathUtils.pulsate(Minecraft.getSystemTime % 2000, 0, 0.1, 2000), 24)
      GL11.glDisable(GL11.GL_BLEND)
      GL11.glPopMatrix()
      GL11.glEnable(GL11.GL_ALPHA_TEST)

      GL11.glEnable(GL11.GL_TEXTURE_2D)
    }
  }
}
