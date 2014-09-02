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
import de.mineformers.core.tileentity.{Describing, Describable, MFTile}
import de.mineformers.core.util.{PlayerUtils, MathUtils}
import de.mineformers.core.util.world.{SphereUtils, Vector3, BlockSphere, BlockPos}
import de.mineformers.kybology.Core
import de.mineformers.kybology.core.entity.EntityPulledBlock
import de.mineformers.kybology.core.window.WorldWindow
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.AxisAlignedBB
import de.mineformers.core.util.Implicits.RichWorld

/**
 * TileRift
 *
 * @author PaleoCrafter
 */
class TileRift extends MFTile with Describable {
  override def init(): Unit = {
  }

  override def updateServer(): Unit = {
    if (deathTimer > 25) {
      pullRadius = 50
    }
    if (deathTimer == 40) {
      world.newExplosion(null, x + 0.5, y + 0.5, z + 0.5, 20, true, true)
      destroyBlocks()
    }
    if (deathTimer == 50) {
      WorldWindow.createAnomaly(world, pos)
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
      sphere.walk()
      sphere.reset()
      pullEntities()
    }
  }

  @SideOnly(Side.CLIENT)
  override def updateClient(): Unit = {
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
        Vector3(e.posX, e.posY, e.posZ).distanceSq(vecPos + Vector3.Center) <= playerRadius && !e.capabilities.isCreativeMode
      case e: Entity if e != null =>
        if (e != null)
          Vector3(e.posX, e.posY, e.posZ).distanceSq(vecPos + Vector3.Center) <= radiusSquared
        else
          false
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

  override def getRenderBoundingBox: AxisAlignedBB = AxisAlignedBB.getBoundingBox(x - 2, y - 2, z - 2, x + 2, y + 2, z + 2)

  private def sphereWalker(pos: BlockPos): Unit = {
    if (!world.isAirBlock(pos.x, pos.y, pos.z) && world.getBlock(pos.x, pos.y, pos.z) != Core.blocks.rift && world.getBlock(pos.x, pos.y, pos.z).getBlockHardness(world, pos.x, pos.y, pos.z) >= 0) {
      val entity = new EntityPulledBlock(world, pos.x, pos.y, pos.z, world.getBlock(pos.x, pos.y, pos.z), world.getBlockMetadata(pos.x, pos.y, pos.z))
      world.spawnEntityInWorld(entity)
      world.setBlockToAir(pos.x, pos.y, pos.z)
    } else
      sphere.walk()
  }

  private def destroyBlocks(): Unit = {
    BlockSphere(pos, 21, pos => if (!world.isAirBlock(pos.x, pos.y, pos.z) && world.getBlock(pos.x, pos.y, pos.z).getBlockHardness(world, pos.x, pos.y, pos.z) >= 0) world.setBlockToAir(pos.x, pos.y, pos.z)).walkAll()
  }

  private val notifiedWeak = collection.mutable.ListBuffer.empty[EntityPlayer]
  private val notifiedStrong = collection.mutable.ListBuffer.empty[EntityPlayer]
  private var deathTimer = 0
  private lazy val sphere = BlockSphere(pos, 3, sphereWalker)
  var pullRadius = 12
  @Describing
  var scale = 1D
  @Describing
  var storedEnergy = 100
}
