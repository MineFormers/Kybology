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
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import scala.util.Random

/**
 * WorldWindow
 *
 * @author PaleoCrafter
 */
class WorldWindow(var world: World, val position: BlockPos, val radius: Double, val blocks: Structure = null) {
  val timeOffset = new Random(position.hashCode).nextInt(math.pow(radius, 3).toInt)

  lazy val structureWorld = new StructureWorld(blocks, position - BlockPos(radius.toInt, radius.toInt, radius.toInt), if (world.isRemote) Side.CLIENT else Side.SERVER)

  def place(): Unit = {
    StructureHelper.pasteStructure(world, position.x - radius.toInt, position.y - radius.toInt - 1, position.z - radius.toInt, blocks, pasteAir = true, spawnEntities = false)
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case window: WorldWindow => window.world == world && window.position == position && window.radius == radius
    case _ => false
  }
}

object WorldWindow {
  def createAnomaly(world: World, position: BlockPos): Unit = {
    val data = WorldWindowData(world)
    val rand = new Random
    def random = rand.nextInt(21 * 2 + 2) - 21
    val blobs = (for {
      b <- 0 until (rand.nextInt(5) + 5)
      center = position + BlockPos(random, random, random)
      radius = rand.nextInt(4) + 3
    } yield BlockSphere(center, radius)).toBuffer
    for (i <- 0 until blobs.size) {
      var b = blobs(i)
      while (blobs.exists(b1 => !(b1 eq b) && b.intersects(b1)) || b.isAir(world)) {
        b = BlockSphere(position + BlockPos(random, random, random), b.radius)
        blobs.update(i, b)
      }
    }
    blobs foreach {
      b =>
        val radius = b.radius.toInt
        val blocks = new Structure((for (i <- 0 until (radius * 2 + 1)) yield new Layer(radius * 2 + 1, radius * 2 + 1)).toBuffer)
        val center = b.center
        b.operation = {
          pos =>
            if (position.distanceSq(pos) <= 21.5 * 21.5) {
              val block = world.getBlock(pos.x, pos.y, pos.z)
              if (!block.isAir(world, pos.x, pos.y, pos.z) && block != Core.blocks.rift) {
                val localPos: BlockPos = (pos + BlockPos(radius, radius, radius) - center).abs
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
        }
        b.walkAll()
        data.add(new WorldWindow(world, center, radius, blocks))
    }
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