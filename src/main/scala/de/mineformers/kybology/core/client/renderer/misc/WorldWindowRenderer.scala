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

package de.mineformers.kybology.core.client.renderer.misc

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent
import de.mineformers.core.client.renderer.StructureWorldRenderer
import de.mineformers.core.util.world.BlockPos
import de.mineformers.kybology.core.window.WorldWindowData
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.{RenderWorldEvent, RenderWorldLastEvent}
import org.lwjgl.opengl.GL11

import scala.util.Random

/**
 * WorldWindowRenderer
 *
 * @author PaleoCrafter
 */
class WorldWindowRenderer {
  val renderer = new StructureWorldRenderer

  @SubscribeEvent
  def onRenderLast(event: RenderWorldLastEvent): Unit = {
    val data = WorldWindowData(Minecraft.getMinecraft.theWorld)
    for (w <- data.view) {
      GL11.glPushMatrix()
      renderer.render(w.structureWorld, event.partialTicks)
      GL11.glPopMatrix()
    }
  }

  @SubscribeEvent
  def onRenderBlock(event: RenderWorldEvent.Pre): Unit = {
    val pos = BlockPos(event.renderer.posXClip, event.renderer.posYClip, event.renderer.posZClip)
    val data = WorldWindowData(event.renderer.worldObj)
    for (w <- data.view) {
      w.structureWorld.checkChunks(pos)
    }
  }

  @SubscribeEvent
  def onTick(event: ClientTickEvent): Unit = {
    if (Minecraft.getMinecraft.theWorld != null) {
      val data = WorldWindowData(Minecraft.getMinecraft.theWorld)
      for (w <- data.view) {
        w.structureWorld.tick()
      }
    }
  }
}
