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

package de.mineformers.kybology.core.window

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint
import de.mineformers.core.util.world.BlockPos
import de.mineformers.kybology.Core
import de.mineformers.kybology.core.network.WorldWindowMessage
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import net.minecraft.world.{World, WorldSavedData}
import net.minecraftforge.common.util.Constants

/**
 * WorldWindowData
 *
 * @author PaleoCrafter
 */
class WorldWindowData(var world: World) extends WorldSavedData(WorldWindowData.Identifier) {
  def this() = this(null.asInstanceOf[World])

  def this(string: String) = this()

  private val windows = collection.mutable.ListBuffer.empty[WorldWindow]

  def view = windows.view

  def syncChunk(player: EntityPlayerMP, chunkX: Int, chunkZ: Int): Unit = {
    windows foreach {
      w =>
        if (chunkX == (w.position.x >> 4) && chunkZ == (w.position.z >> 4)) {
          sync(w, 0, player)
        }
    }
  }

  def sync(action: Int = 0): Unit = {
    windows foreach {
      sync(_, action)
    }
  }

  def sync(w: WorldWindow, action: Int): Unit = {
    if (!world.isRemote)
      Core.net.sendToAllAround(WorldWindowMessage(w.position.x, w.position.y, w.position.z, action, if (action == 0) WorldWindow.toNBT(w) else null), new TargetPoint(world.provider.dimensionId, w.position.x, w.position.y, w.position.z, 128))
  }

  def sync(w: WorldWindow, action: Int, player: EntityPlayerMP): Unit = {
    if (!world.isRemote)
      Core.net.sendTo(WorldWindowMessage(w.position.x, w.position.y, w.position.z, action, if (action == 0) WorldWindow.toNBT(w) else null), player)
  }

  def add(window: WorldWindow): Unit = {
    remove(window.position)
    windows += window
    sync(window, 0)
    markDirty()
  }

  def remove(pos: BlockPos): Unit = windows.find(_.position == pos) match {
    case Some(w) => windows -= w
      sync(w, 1)
      markDirty()
    case None =>
  }

  def get(pos: BlockPos) = windows.find(_.position == pos)

  override def readFromNBT(nbt: NBTTagCompound): Unit = {
    windows.clear()
    val list = nbt.getTagList("Windows", Constants.NBT.TAG_COMPOUND)
    for (i <- 0 until list.tagCount())
      windows += WorldWindow.fromNBT(world, list.getCompoundTagAt(i))
  }

  override def writeToNBT(nbt: NBTTagCompound): Unit = {
    val list = new NBTTagList
    windows foreach {
      w =>
        list.appendTag(WorldWindow.toNBT(w))
    }
    nbt.setTag("Windows", list)
  }
}

object WorldWindowData {
  final val Identifier = "kybology.worldwindow"

  def apply(world: World): WorldWindowData = {
    var data = world.loadItemData(classOf[WorldWindowData], Identifier).asInstanceOf[WorldWindowData]
    if (data != null && data.world == null) {
      data.world = world
      data.view.foreach(_.world = world)
    }
    if (data == null) {
      data = new WorldWindowData(world)
      world.setItemData(Identifier, data)
    }
    data
  }
}
