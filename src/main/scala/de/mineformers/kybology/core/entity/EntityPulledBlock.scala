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

package de.mineformers.kybology.core.entity

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import de.mineformers.kybology.Core
import io.netty.buffer.ByteBuf
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.DamageSource
import net.minecraft.world.World

/**
 * EntityPulledBlock
 *
 * @author PaleoCrafter
 */
class EntityPulledBlock(world: World, x: Double, y: Double, z: Double, var block: Block, var metadata: Int) extends Entity(world) with IEntityAdditionalSpawnData {
  this.preventEntitySpawning = true
  this.setSize(0.98F, 0.98F)
  this.yOffset = this.height / 2.0F
  this.setPosition(x + 0.5, y + 0.5, z + 0.5)
  this.motionX = 0.0D
  this.motionY = 0.0D
  this.motionZ = 0.0D
  this.prevPosX = x + 0.5
  this.prevPosY = y + 0.5
  this.prevPosZ = z + 0.5
  private var health: Int = 5
  private var timer: Int = 0

  def this(world: World) = this(world, 0, 0, 0, null, 0)

  override def entityInit(): Unit = {
  }

  override def onUpdate(): Unit = {
    timer += 1
    if(timer >= 40)
      this.attackEntityFrom(Core.blocks.rift.damage, 0.5F)
    this.moveEntity(this.motionX, this.motionY, this.motionZ)
  }

  override def canTriggerWalking: Boolean = false

  override def canRenderOnFire: Boolean = false

  override def getShadowSize: Float = 0

  /**
   * Called when the entity is attacked.
   */
  override def attackEntityFrom(src: DamageSource, amount: Float): Boolean = {
    if (this.isEntityInvulnerable) {
      false
    }
    else {
      this.setBeenAttacked()
      this.health = (this.health - amount).toInt
      if (this.health <= 0) {
        this.setDead()
      }
      false
    }
  }

  override def writeEntityToNBT(nbt: NBTTagCompound): Unit = {
    nbt.setInteger("Block", Block.getIdFromBlock(block))
    nbt.setByte("Metadata", metadata.toByte)
  }

  override def readEntityFromNBT(nbt: NBTTagCompound): Unit = {
    block = Block.getBlockById(nbt.getInteger("Block"))
    metadata = nbt.getByte("Metadata")
  }

  override def readSpawnData(additionalData: ByteBuf): Unit = {
    block = Block.getBlockById(additionalData.readInt())
    metadata = additionalData.readByte()
  }

  override def writeSpawnData(buffer: ByteBuf): Unit = {
    buffer.writeInt(Block.getIdFromBlock(block))
    buffer.writeByte(metadata)
  }

  override def shouldRenderInPass(pass: Int): Boolean = true
}
