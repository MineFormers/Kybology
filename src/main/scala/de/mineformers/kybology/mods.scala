package de.mineformers.kybology

import cpw.mods.fml.common.{SidedProxy, Mod}
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.registry.EntityRegistry
import de.mineformers.core.mod._
import de.mineformers.core.util.ResourceUtils.Resource
import de.mineformers.kybology.core.entity.EntityPulledBlock
import de.mineformers.kybology.core.init.{CoreItems, CoreBlocks}
import de.mineformers.kybology.core.network.WorldWindowMessage

/**
 * KybologyCore
 *
 * @author PaleoCrafter
 */

trait KyMod extends MFMod {
  override def apply(resource: String) = Resource("kybology." + ResourcePath, resource)

  override def icon(name: String): String = "kybology." + ResourcePath + ":" + name

  override def name(name: String): String = "kybology." + ResourcePath + ":" + name
}

@Mod(modid = Core.ModId, name = Core.ModName, modLanguage = "scala", dependencies = "required-after:Forge@[10.13.1.1212,]")
object Core extends KyMod with HoldsBlocks[CoreBlocks] with HoldsItems[CoreItems] with Networking {
  final val ModId = "Kybology|Core"
  final val ModName = "Kybology Core"
  override final val ResourcePath = "core"
  @SidedProxy(clientSide = "de.mineformers.kybology.core.CoreClientProxy", serverSide = "de.mineformers.kybology.core.CoreServerProxy", modId = ModId)
  var proxy: Proxy = _

  @EventHandler
  override def preInit(event: FMLPreInitializationEvent): Unit = {
    initBlocks()
    initItems()
    EntityRegistry.registerModEntity(classOf[EntityPulledBlock], "PulledBlock", 0, this, 80, 3, true)
    registerMessage[WorldWindowMessage]
  }

  @EventHandler
  override def init(event: FMLInitializationEvent): Unit = {
    proxy.init()
  }

  @EventHandler
  override def postInit(event: FMLPostInitializationEvent): Unit = ()

  object Names {

    object Blocks {
      final val Rift = "rift"
      final val Crystal = "crystal_ore"
    }

    object Items {
      final val Wrench = "wrench"
    }

  }

}

@Mod(modid = TimeTravel.ModId, name = TimeTravel.ModName, version = "0.1", modLanguage = "scala", dependencies = "required-after:Kybology|Core")
object TimeTravel extends KyMod {
  final val ModId = "Kybology|TimeTravel"
  final val ModName = "Kybology Time Travelling"
  override final val ResourcePath = "timetravel"

  @EventHandler
  override def preInit(event: FMLPreInitializationEvent): Unit = ()

  @EventHandler
  override def init(event: FMLInitializationEvent): Unit = ()

  @EventHandler
  override def postInit(event: FMLPostInitializationEvent): Unit = ()
}




