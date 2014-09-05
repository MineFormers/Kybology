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

package de.mineformers.kybology.core.tileentity

import cpw.mods.fml.relauncher.{Side, SideOnly}
import de.mineformers.core.client.util.RenderUtils
import de.mineformers.core.tileentity.{Describing, Describable, MFTile}
import de.mineformers.core.util.{PlayerUtils, MathUtils}
import de.mineformers.core.util.world.{SphereUtils, Vector3, BlockSphere, BlockPos}
import de.mineformers.kybology.Core
import de.mineformers.kybology.core.entity.EntityPulledBlock
import de.mineformers.kybology.core.window.{WorldWindowData, WorldWindow}
import net.minecraft.block.{Block, BlockLiquid}
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.AxisAlignedBB
import de.mineformers.core.util.Implicits._
import net.minecraft.world.WorldServer
import net.minecraftforge.fluids.BlockFluidBase

/**
 * TileRift
 *
 * @author PaleoCrafter
 */
class TileRift extends MFTile with Describable {
  override def init(): Unit = {
  }

  override def shouldRenderInPass(pass: Int): Boolean = true

  override def updateServer(): Unit = {
    if (deathTimer > 25) {
      pullRadius = 50
    }
    if (deathTimer == 40) {
      for (i <- 0 until 10)
        world.createExplosion(null, x + world.rand.nextInt(43) - 21, y + world.rand.nextInt(43) - 21, z + world.rand.nextInt(43) - 21, 3, false)
      WorldWindow.createAnomaly(world, pos)
    }
    if (deathTimer >= 50) {
      for (i <- 0 until 5)
        world.createExplosion(null, x + world.rand.nextInt(43) - 21, y + world.rand.nextInt(43) - 21, z + world.rand.nextInt(43) - 21, 3, false)
      SphereUtils.destroy(world, pos, 21)
      WorldWindowData(world).sync(0)
      destroy()
    } else {
      if (storedEnergy <= 0) {
        scale = MathUtils.easeInOut(deathTimer, 0, 50, 50)
        deathTimer += 1
        update()
      } else if (storedEnergy > 0 && deathTimer > 0) {
        deathTimer = 0
        scale = 1
        update()
      }
      if(deathTimer == 0) {
        sphere.walk()
        sphere.reset()
      }
      pullEntities()
    }
  }

  @SideOnly(Side.CLIENT)
  override def updateClient(): Unit = {
    val radiusSquared = pullRadius * pullRadius
    for (layer <- RenderUtils.particles;
         e <- layer) {
      if (Vector3(e.posX, e.posY, e.posZ).distanceSq(vecPos + Vector3.Center) <= radiusSquared) {
        val entityPos = Vector3.fromEntityCenter(e)
        var distanceVector = (Vector3.Center + vecPos) - entityPos
        val squareDistance = distanceVector.magSq

        if (squareDistance > 1)
          distanceVector = distanceVector.normalize

        val factor = 1 / math.sqrt(squareDistance) * 0.3F * (if (deathTimer > 20) 10F else 1)
        e.addVelocity(distanceVector.x * factor, distanceVector.y * factor, distanceVector.z * factor)
      }
    }
    pullEntities()
  }

  def pullEntities(): Unit = {
    val bounding = pullRadius + 1
    val radiusSquared = pullRadius * pullRadius
    val playerRadius = math.pow(pullRadius + 2, 2)
    val centerX = x + 0.5
    val centerY = y + 0.5
    val centerZ = z + 0.5
    world.selectEntities(AxisAlignedBB.getBoundingBox(centerX - bounding, centerY - bounding, centerZ - bounding, centerX + bounding, centerY + bounding, centerZ + bounding)) {
      case e: EntityPlayer if e != null =>
        try {
          Vector3(e.posX, e.posY, e.posZ).distanceSq(vecPos + Vector3.Center) <= playerRadius && !e.capabilities.isCreativeMode
        } catch {
          case e: Exception => false
        }
      case e: Entity if e != null =>
        try {
          Vector3(e.posX, e.posY, e.posZ).distanceSq(vecPos + Vector3.Center) <= radiusSquared
        } catch {
          case e: Exception => false
        }
      case _ => false
    } foreach pullEntity
  }

  def pullEntity(entity: Entity): Unit = {
    if (world.isClient) {
      val distance = Vector3(entity.posX, entity.posY, entity.posZ).distanceSq(vecPos + Vector3.Center)
      val strongRangeMin = 5.5 * 5.5
      val strongRangeMax = 4.5 * 4.5
      val weakRangeMin = pullRadius * pullRadius
      val weakRangeMax = (pullRadius - 0.5) * (pullRadius - 0.5)
      entity match {
        case p: EntityPlayer =>
          if (!notifiedWeak.contains(p) && weakRangeMin >= distance && weakRangeMax <= distance) {
            notifiedWeak += p
            PlayerUtils.sendMessage(p, "msg.kybology.core:riftPulling.weak")
          } else if (notifiedWeak.contains(p) && distance > weakRangeMin) {
            notifiedWeak -= p
            return
          }
          if (!notifiedStrong.contains(p) && strongRangeMin >= distance && strongRangeMax <= distance) {
            notifiedStrong += p
            PlayerUtils.sendMessage(p, "msg.kybology.core:riftPulling.strong")
          } else if (notifiedStrong.contains(p) && distance > strongRangeMin) {
            notifiedStrong -= p
            return
          }
        case _ =>
      }
    }
    val entityPos = Vector3.fromEntityCenter(entity)
    var distanceVector = (Vector3.Center + vecPos) - entityPos
    val squareDistance = distanceVector.magSq

    if (squareDistance <= 1)
      entity.attackEntityFrom(Core.blocks.rift.damage, 4F * (if (deathTimer > 30) 5F else 1))

    if (squareDistance > 1)
      distanceVector = distanceVector.normalize

    val factor = 1 / math.sqrt(squareDistance) * 0.45F * (if (deathTimer > 20) 10F else 1)

    entity.addVelocity(distanceVector.x * factor, distanceVector.y * factor, distanceVector.z * factor)
  }

  override def onDescription(): Unit = {
  }

  override def getRenderBoundingBox: AxisAlignedBB = AxisAlignedBB.getBoundingBox(x - 128, y - 128, z - 128, x + 128, y + 128, z + 128)

  private def sphereWalker(pos: BlockPos): Unit = {
    val block = world.getBlock(pos.x, pos.y, pos.z)
    if (!world.isAirBlock(pos.x, pos.y, pos.z) && block != Core.blocks.rift && block.getBlockHardness(world, pos.x, pos.y, pos.z) >= 0) {
      block match {
        case _@(_: BlockLiquid | _: BlockFluidBase) =>
          val worldServer = world.asInstanceOf[WorldServer]
          var distanceVector = (Vector3.Center + vecPos) - pos.toVector
          val squareDistance = distanceVector.magSq
          if (squareDistance > 1)
            distanceVector = distanceVector.normalize
          val factor = 0.25
          for (i <- 0 to 20) {
            worldServer.func_147487_a("blockdust_" + Block.getIdFromBlock(block) + "_" + world.getBlockMetadata(pos.x, pos.y, pos.z), pos.x + world.rand.nextFloat(), pos.y + world.rand.nextFloat(), pos.z + world.rand.nextFloat(), 2, distanceVector.x * factor, distanceVector.y * factor, distanceVector.z * factor, 0)
          }
        case _ =>
          val entity = new EntityPulledBlock(world, pos.x, pos.y, pos.z, block, world.getBlockMetadata(pos.x, pos.y, pos.z))
          world.spawnEntityInWorld(entity)
      }
      world.setBlockToAir(pos.x, pos.y, pos.z)
    } else
      sphere.walk()
  }

  private val notifiedWeak = collection.mutable.ListBuffer.empty[EntityPlayer]
  private val notifiedStrong = collection.mutable.ListBuffer.empty[EntityPlayer]
  @Describing
  private var deathTimer = 0
  private lazy val sphere = BlockSphere(pos, 3, sphereWalker)
  var pullRadius = 12
  @Describing
  var scale = 1D
  @Describing
  var storedEnergy = 100
}
