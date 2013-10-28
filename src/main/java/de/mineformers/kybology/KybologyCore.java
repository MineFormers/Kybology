package de.mineformers.kybology;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import de.mineformers.kybology.core.creativetab.CreativeTabKybology;
import de.mineformers.kybology.core.entity.ModEntities;
import de.mineformers.kybology.core.lib.Reference;
import de.mineformers.kybology.core.lib.Version;
import de.mineformers.kybology.core.network.PacketHandler;
import de.mineformers.kybology.core.util.LogHelper;
import de.mineformers.kybology.core.world.GeneratorRift;
import de.mineformers.kybology.timetravel.handler.CommandHandler;
import de.mineformers.kybology.core.proxy.CommonProxy;

/**
 * 
 * Kybology
 * 
 * KybologyCore
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@Mod(modid = KybologyCore.MOD_ID,
        name = KybologyCore.MOD_NAME,
        version = Version.VERSION,
        dependencies = KybologyCore.DEPENDENCIES,
        certificateFingerprint = Reference.FINGERPRINT)
@NetworkMod(channels = { KybologyCore.CHANNEL_NAME },
        clientSideRequired = true,
        serverSideRequired = false,
        packetHandler = PacketHandler.class)
public class KybologyCore {

    public static final String MOD_ID = "Kybology|Core";
    public static final String MOD_ID_RAW = "Core";
    public static final String MOD_NAME = "Kybology Core";
    public static final String CHANNEL_NAME = Reference.MOD_GROUP;
    public static final String RESOURCE_PATH = "core" + File.separator;
    public static final String DEPENDENCIES = "required-after:Forge@[9.11.1.935,)";
    public static final String SERVER_PROXY_CLASS = "de.mineformers.kybology.core.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "de.mineformers.kybology.core.proxy.ClientProxy";

    @Instance(KybologyCore.MOD_ID)
    public static KybologyCore instance;

    @SidedProxy(clientSide = KybologyCore.CLIENT_PROXY_CLASS,
            serverSide = KybologyCore.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final CreativeTabs tabKybology = new CreativeTabKybology(
            CreativeTabs.getNextID(), KybologyCore.MOD_ID);

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        // Initialize the custom commands
        CommandHandler.initCommands(event);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LogHelper.init();

        ModEntities.init();
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new GeneratorRift());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
