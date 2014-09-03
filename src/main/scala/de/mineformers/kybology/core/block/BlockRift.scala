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

package de.mineformers.kybology.core.block

import cpw.mods.fml.relauncher.{Side, SideOnly}
import de.mineformers.core.block.{TileProvider, BaseBlock}
import de.mineformers.core.client.util.{RenderingProxy, Rendering}
import de.mineformers.kybology.Core
import de.mineformers.kybology.Core.Names
import de.mineformers.kybology.core.client.renderer.item.RiftItemRenderer
import de.mineformers.kybology.core.client.renderer.tileentity.RiftRenderer
import de.mineformers.kybology.core.tileentity.TileRift
import net.minecraft.block.material.Material
import net.minecraft.util.{DamageSource, AxisAlignedBB}
import net.minecraft.world.World

/**
 * BlockRift
 *
 * @author PaleoCrafter
 */
class BlockRift extends BaseBlock(Names.Blocks.Rift, Core.CreativeTab, Material.rock) with TileProvider[TileRift] with Rendering {
  setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F)
  setBlockUnbreakable()
  setResistance(6000000.0F)

  override def getCollisionBoundingBoxFromPool(world: World, x: Int, y: Int, z: Int): AxisAlignedBB = null

  override def getSelectedBoundingBoxFromPool(world: World, x: Int, y: Int, z: Int): AxisAlignedBB = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0)

  override def tileClass: Class[TileRift] = classOf[TileRift]

  @SideOnly(Side.CLIENT)
  override protected def createProxy: RenderingProxy = new RenderingProxy {
    @SideOnly(Side.CLIENT)
    override def createTileRenderer = new RiftRenderer

    @SideOnly(Side.CLIENT)
    override def createItemRenderer = new RiftItemRenderer
  }

  val damage = new DamageSource("kybology:rift")
}
