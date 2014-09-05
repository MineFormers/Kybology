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

package de.mineformers.kybology.core

import cpw.mods.fml.client.registry.RenderingRegistry
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.relauncher.Side
import de.mineformers.core.mod.ClientProxy
import de.mineformers.core.util.world.BlockPos
import de.mineformers.kybology.Core
import de.mineformers.kybology.core.client.renderer.entity.RenderPulledBlock
import de.mineformers.kybology.core.client.renderer.misc.WorldWindowRenderer
import de.mineformers.kybology.core.entity.EntityPulledBlock
import de.mineformers.kybology.core.network.WorldWindowMessage
import de.mineformers.kybology.core.window.{WorldWindow, WorldWindowData}
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge

/**
 * CoreClientProxy
 *
 * @author PaleoCrafter
 */
class CoreClientProxy extends CoreProxy with ClientProxy {
  override def init(): Unit = {
    super.init()
    RenderingRegistry.registerEntityRenderingHandler(classOf[EntityPulledBlock], new RenderPulledBlock)
    val renderer = new WorldWindowRenderer
    MinecraftForge.EVENT_BUS.register(renderer)
    FMLCommonHandler.instance().bus().register(renderer)
    Core.net.addHandler({
      case (WorldWindowMessage(x, y, z, action, data), context) =>
        action match {
          case 0 if data != null =>
            val w = WorldWindow.fromNBT(Minecraft.getMinecraft.theWorld, data)
            WorldWindowData(Minecraft.getMinecraft.theWorld).add(w)
          case 1 =>
            WorldWindowData(Minecraft.getMinecraft.theWorld).remove(BlockPos(x, y, z))
          case _ =>
        }
        null
    }, Side.CLIENT)
  }
}
