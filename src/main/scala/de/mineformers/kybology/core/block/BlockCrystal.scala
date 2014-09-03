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

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler
import cpw.mods.fml.relauncher.{Side, SideOnly}
import de.mineformers.core.block.{MetaRotation, Rotatable6D, TileProvider, BaseBlock}
import de.mineformers.core.client.util.{RenderingProxy, Rendering, MultiPass}
import de.mineformers.kybology.Core
import de.mineformers.kybology.Core.Names
import de.mineformers.kybology.core.client.renderer.tileentity.CrystalRenderer
import de.mineformers.kybology.core.tileentity.TileCrystal
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import de.mineformers.core.util.Implicits.RichWorld

/**
 * BlockCrystal
 *
 * @author PaleoCrafter
 */
class BlockCrystal extends BaseBlock(Names.Blocks.Crystal, Core.CreativeTab, Material.rock) with TileProvider[TileCrystal] with Rendering with Rotatable6D with MetaRotation with MultiPass {
  @SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
  override def textureProxy: String = getTextureName

  override def tileClass = classOf[TileCrystal]

  override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, entity: EntityLivingBase, stack: ItemStack): Unit = ()

  override def onBlockPlaced(world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float, meta: Int): Int = {
    if (world.isServer) {
      this.setRotation(world, x, y, z, ForgeDirection.getOrientation(side))
      this.getRotation(world, x, y, z).ordinal
    } else
      meta
  }

  override def canRenderInPass(pass: Int): Boolean = true

  @SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
  override def hasSingleIcon: Boolean = true

  override def canRotate(world: World, x: Int, y: Int, z: Int, axis: ForgeDirection): Boolean = {
    world.isSideSolid(x + axis.getOpposite.offsetX, y + axis.getOpposite.offsetY, z + axis.getOpposite.offsetZ, axis)
  }

  @SideOnly(Side.CLIENT)
  override protected def createProxy: RenderingProxy = new RenderingProxy {
    @SideOnly(Side.CLIENT)
    override def createTileRenderer = new CrystalRenderer
  }

  override def canPlaceBlockAt(world: World, x: Int, y: Int, z: Int): Boolean =
    ForgeDirection.VALID_DIRECTIONS exists {
      d =>
        world.isSideSolid(x + d.offsetX, y + d.offsetY, z + d.offsetZ, d.getOpposite)
    }
}
