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

import de.mineformers.core.util.world.{BlockSphere, BlockPos}
import de.mineformers.kybology.Core
import net.minecraft.block.Block
import net.minecraft.client.multiplayer.ChunkProviderClient
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.gen.ChunkProviderServer

/**
 * WorldWindow
 *
 * @author PaleoCrafter
 */
class WorldWindow(val world: World, val position: BlockPos, val radius: Double) {
  private val sphere = BlockSphere(position, radius, collect)
  private val blocks = collection.mutable.Map.empty[BlockPos, (Block, Int)]
  private val chunks = collection.mutable.Map.empty[BlockPos, Chunk]
  sphere.walkAll({
    chunks.clear()
  })

  def place(): Unit = {
    for (e <- blocks) {
      val rel = e._1
      val block = e._2
      world.setBlock(position.x - rel.x, position.y - rel.y, position.z - rel.z, block._1, block._2, 3)
    }
  }

  private def collect(pos: BlockPos): Unit = {
    if (world.getBlock(pos.x, pos.y, pos.z) != Core.blocks.rift) {
      val chunkCoords = BlockPos(pos.x >> 4, 0, pos.z >> 4)
      val chunk = chunks.getOrElseUpdate(chunkCoords, {
        val newChunk = world.getChunkProvider match {
          case c: ChunkProviderClient => c.provideChunk(chunkCoords.x, chunkCoords.z)
          case s: ChunkProviderServer =>
            s.currentChunkProvider.provideChunk(chunkCoords.x, chunkCoords.z)
        }
        newChunk
      })
      val chunkPos = BlockPos(pos.x & 15, pos.y, pos.z & 15)
      val localPos = position - pos
      blocks += localPos ->(chunk.getBlock(chunkPos.x, chunkPos.y, chunkPos.z), chunk.getBlockMetadata(chunkPos.x, chunkPos.y, chunkPos.z))
    }
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case window: WorldWindow => window.world == world && window.position == position && window.radius == radius
    case _ => false
  }
}

object WorldWindow {
  def createAnomaly(world: World, pos: BlockPos): Unit = {
    WorldWindowData(world).add(new WorldWindow(world, pos, 21))
  }

  def toNBT(window: WorldWindow): NBTTagCompound = {
    val nbt = new NBTTagCompound
    nbt.setIntArray("Position", window.position.toArray)
    nbt.setDouble("Radius", window.radius)
    nbt
  }

  def fromNBT(world: World, nbt: NBTTagCompound): WorldWindow = new WorldWindow(world, BlockPos.fromArray(nbt.getIntArray("Position")), nbt.getDouble("Radius"))
}