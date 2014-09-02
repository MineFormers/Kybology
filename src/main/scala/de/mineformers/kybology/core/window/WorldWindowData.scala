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

import de.mineformers.core.util.world.BlockPos
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

  def add(window: WorldWindow): Unit = {
    remove(window.position)
    windows += window
    markDirty()
  }

  def remove(pos: BlockPos): Unit = windows.find(_.position == pos) match {
    case Some(w) => windows -= w
      markDirty()
    case None =>
  }

  override def readFromNBT(nbt: NBTTagCompound): Unit = {
    windows.clear()
    val list = nbt.getTagList("Windows", Constants.NBT.TAG_COMPOUND)
    for (i <- 0 until list.tagCount())
      add(WorldWindow.fromNBT(world, list.getCompoundTagAt(i)))
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
    if(data != null && data.world == null)
      data.world = world
    if (data == null) {
      data = new WorldWindowData(world)
      world.setItemData(Identifier, data)
    }
    data
  }
}
