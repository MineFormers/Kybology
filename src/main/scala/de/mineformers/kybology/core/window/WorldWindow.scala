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

import cpw.mods.fml.relauncher.Side
import de.mineformers.core.structure._
import de.mineformers.core.util.world.{BlockSphere, BlockPos}
import de.mineformers.kybology.Core
import net.minecraft.block.Block
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/**
 * WorldWindow
 *
 * @author PaleoCrafter
 */
class WorldWindow(var world: World, val position: BlockPos, val radius: Double, var blocks: Structure = null) {
  private val sphere = BlockSphere(position, radius, collect)
  if (blocks == null)
    blocks = new Structure((for (i <- 0 to (radius * 2 + 1).toInt) yield new Layer((radius * 2 + 1).toInt, (radius * 2 + 1).toInt)).toBuffer)

  lazy val structureWorld = new StructureWorld(blocks, position - BlockPos(radius.toInt, radius.toInt, radius.toInt), if(world.isRemote) Side.CLIENT else Side.SERVER)

  def build(): Unit = {
    sphere.walkAll()
  }

  def place(): Unit = {
    StructureHelper.pasteStructure(world, position.x - radius.toInt, position.y - radius.toInt - 1, position.z - radius.toInt, blocks, pasteAir = true, spawnEntities = false)
  }

  private def collect(pos: BlockPos): Unit = {
    val block: Block = world.getBlock(pos.x, pos.y, pos.z)
    if (block != Core.blocks.rift) {
      val localPos = -(position - BlockPos(radius.toInt, radius.toInt, radius.toInt) - pos)
      val meta: Int = world.getBlockMetadata(pos.x, pos.y, pos.z)
      val tile: TileEntity = world.getTileEntity(pos.x, pos.y, pos.z)
      val tag: NBTTagCompound = if (tile != null) new NBTTagCompound else null
      if (tag != null) {
        tile.writeToNBT(tag)
        StructureHelper.updateTileCoordinates(tag, localPos.x, localPos.y, localPos.z)
      }
      blocks.setBlock(localPos.x, localPos.y, localPos.z, block, meta, tag)
    }
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case window: WorldWindow => window.world == world && window.position == position && window.radius == radius
    case _ => false
  }
}

object WorldWindow {
  def createAnomaly(world: World, pos: BlockPos): Unit = {
    val window = new WorldWindow(world, pos, 21)
    window.build()
    WorldWindowData(world).add(window)
  }

  def toNBT(window: WorldWindow): NBTTagCompound = {
    val nbt = new NBTTagCompound
    nbt.setIntArray("Position", window.position.toArray)
    nbt.setDouble("Radius", window.radius)
    val data = new NBTTagCompound
    ModMaticFile.write(window.blocks, data)
    nbt.setTag("Data", data)
    nbt
  }

  def fromNBT(world: World, nbt: NBTTagCompound): WorldWindow = new WorldWindow(world, BlockPos.fromArray(nbt.getIntArray("Position")), nbt.getDouble("Radius"), ModMaticFile.read(nbt.getCompoundTag("Data")))
}