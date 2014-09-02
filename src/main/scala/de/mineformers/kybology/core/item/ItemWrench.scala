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

package de.mineformers.kybology.core.item

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.{SideOnly, Side}
import de.mineformers.core.block.Rotatable
import de.mineformers.core.item.BaseItem
import de.mineformers.core.util.world.{BlockPos, BlockSphere}
import de.mineformers.core.util.{PlayerUtils, MathUtils}
import de.mineformers.kybology.Core
import de.mineformers.kybology.Core.Names
import de.mineformers.kybology.core.item.ItemWrench.Actions
import de.mineformers.kybology.core.tileentity.TileRift
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.MovingObjectPosition.MovingObjectType
import net.minecraft.world.World
import de.mineformers.core.util.Implicits.RichWorld
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.event.entity.player.PlayerInteractEvent

/**
 * ItemWrench
 *
 * @author PaleoCrafter
 */
class ItemWrench extends BaseItem(Names.Items.Wrench, Core.CreativeTab) {

  @SubscribeEvent
  def onInteract(event: PlayerInteractEvent): Unit = {
    val stack = event.entityPlayer.getHeldItem
    import PlayerInteractEvent.Action._
    if (event.action == RIGHT_CLICK_AIR && stack != null && stack.getItem == this)
      event.setCanceled(Minecraft.getMinecraft.objectMouseOver != null && Minecraft.getMinecraft.objectMouseOver.typeOfHit == MovingObjectType.BLOCK)

    if (event.world.isServer && stack != null && stack.getItem == this)
      event.action match {
        case RIGHT_CLICK_AIR if event.entityPlayer.isSneaking =>
          stack.setItemDamage(MathUtils.wrapAround(stack.getItemDamage + 1, Actions.values.size - 1))
          PlayerUtils.sendMessage(event.entityPlayer, "Mode: " + getAction(stack).text)
        case RIGHT_CLICK_BLOCK =>
          val action = getAction(stack)
          if (event.entityPlayer.isSneaking)
            action.shiftClick(event.world, event.x, event.y, event.z, ForgeDirection.getOrientation(event.face), event.entityPlayer)
          else
            action.normalClick(event.world, event.x, event.y, event.z, ForgeDirection.getOrientation(event.face), event.entityPlayer)
        case _ =>
      }
  }

  def getAction(stack: ItemStack): ItemWrench.Actions = Actions(MathUtils.wrapAround(stack.getItemDamage, Actions.values.size - 1)).asInstanceOf[Actions]

  /**
   * Returns True is the item is renderer in full 3D when hold.
   */
  @SideOnly(Side.CLIENT)
  override def isFull3D: Boolean = true
}

object ItemWrench {

  type Actions = Actions.ActionVal

  object Actions extends Enumeration {
    val RiftIncrease = Value("riftIncrease", "Increase Rift Energy by 1 (10)", (world, x, y, z, side, player) => riftChange(world, x, y, z, 1, player), (world, x, y, z, side, player) => riftChange(world, x, y, z, 10, player))
    val RiftDecrease = Value("riftDecrease", "Decrease Rift Energy by 1 (10)", (world, x, y, z, side, player) => riftChange(world, x, y, z, -1, player), (world, x, y, z, side, player) => riftChange(world, x, y, z, -10, player))
    val Rotate = Value("rotate", "Rotate", rotate, rotate)
    val Place = Value("place", "Place cube (sphere) of Stone", (world, x, y, z, side, player) => place(world, x, y, z, player, false), (world, x, y, z, side, player) => place(world, x, y, z, player, true))

    def riftChange(world: World, x: Int, y: Int, z: Int, change: Int, player: EntityPlayer): Unit = {
      world.getTileEntity(x, y, z) match {
        case rift: TileRift =>
          rift.storedEnergy += change
          PlayerUtils.sendMessage(player, "New Energy: %s", rift.storedEnergy)
          rift.update()
        case _ =>
      }
    }

    def place(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, sphere: Boolean): Unit = {
      if (!sphere) {
        val radius = (55 - 1) / 2
        for (xOff <- -radius to radius;
             yOff <- 1 to 55;
             zOff <- -radius to radius) {
          if (!((yOff == 27 || yOff == 28) && xOff == 0 && zOff == 0))
            world.setBlock(x + xOff, y + yOff, z + zOff, Blocks.stone, 0, 3)
        }
      } else {
        BlockSphere(BlockPos(x, y + 5, z), 6, pos => world.setBlock(pos.x, pos.y, pos.z, Blocks.stone, 0, 3)).walkAll()
      }
      player.setPositionAndUpdate(player.posX, player.posY + (if (sphere) 10 else 27), player.posZ)
    }

    def rotate(world: World, x: Int, y: Int, z: Int, side: ForgeDirection, player: EntityPlayer): Unit = {
      world.getBlock(x, y, z) match {
        case b: Rotatable =>
          b.rotateBlock(world, x, y, z, side)
          PlayerUtils.sendMessage(player, b.getRotation(world, x, y, z) + "")
        case b if b != null =>
          b.rotateBlock(world, x, y, z, side)
      }
    }

    class ActionVal(name: String, val text: String, val normalClick: (World, Int, Int, Int, ForgeDirection, EntityPlayer) => Unit, val shiftClick: (World, Int, Int, Int, ForgeDirection, EntityPlayer) => Unit) extends Val(nextId, name)

    protected final def Value(name: String, text: String, normalClick: (World, Int, Int, Int, ForgeDirection, EntityPlayer) => Unit, shiftClick: (World, Int, Int, Int, ForgeDirection, EntityPlayer) => Unit): ActionVal = new ActionVal(name, text, normalClick, shiftClick)
  }

}
