package de.mineformers.kybology;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import de.mineformers.kybology.api.TravellingRegistry;
import de.mineformers.kybology.core.creativetab.CreativeTabKybology;
import de.mineformers.kybology.core.entity.ModEntities;
import de.mineformers.kybology.core.lib.Reference;
import de.mineformers.kybology.core.lib.Version;
import de.mineformers.kybology.core.network.PacketHandler;
import de.mineformers.kybology.core.util.LogHelper;
import de.mineformers.kybology.core.world.GeneratorRift;
import de.mineformers.kybology.timetravel.block.ModBlocks;
import de.mineformers.kybology.timetravel.configuration.ConfigurationHandler;
import de.mineformers.kybology.timetravel.handler.CommandHandler;
import de.mineformers.kybology.timetravel.handler.PlayerTrackerTT;
import de.mineformers.kybology.timetravel.handler.TickHandlerCountdown;
import de.mineformers.kybology.timetravel.handler.TickHandlerWatch;
import de.mineformers.kybology.timetravel.item.ModItems;
import de.mineformers.kybology.timetravel.network.ModPackets;
import de.mineformers.kybology.timetravel.proxy.CommonProxy;
import de.mineformers.kybology.timetravel.travelling.DestinationRedwoodTime;
import de.mineformers.kybology.timetravel.travelling.ModDimensions;
import de.mineformers.kybology.timetravel.world.GeneratorOres;
import de.mineformers.kybology.timetravel.world.biome.BiomeTest;

/**
 * 
 * Kybology
 * 
 * KybologyTimeTravel
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@Mod(modid = KybologyTimeTravel.MOD_ID,
        name = KybologyTimeTravel.MOD_NAME,
        version = Version.VERSION,
        dependencies = KybologyTimeTravel.DEPENDENCIES,
        certificateFingerprint = Reference.FINGERPRINT)
@NetworkMod(channels = { KybologyCore.CHANNEL_NAME },
        clientSideRequired = true,
        serverSideRequired = false,
        packetHandler = PacketHandler.class)
public class KybologyTimeTravel {

    public static final String MOD_ID = "Kybology|TimeTravel";
    public static final String MOD_ID_RAW = "TimeTravel";
    public static final String MOD_NAME = "Kybology Time Travelling";
    public static final String RESOURCE_PATH = "timetravel" + File.separator;
    public static final String DEPENDENCIES = "required-after:Forge@[9.11.1.935,);required-after:Kybology|Core@"
            + Version.VERSION;
    public static final String SERVER_PROXY_CLASS = "de.mineformers.kybology.timetravel.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "de.mineformers.kybology.timetravel.proxy.ClientProxy";

    @Instance(KybologyTimeTravel.MOD_ID)
    public static KybologyTimeTravel instance;

    @SidedProxy(clientSide = KybologyTimeTravel.CLIENT_PROXY_CLASS,
            serverSide = KybologyTimeTravel.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final CreativeTabs tabTimeTravel = new CreativeTabKybology(
            CreativeTabs.getNextID(), KybologyTimeTravel.MOD_ID);

    public static final BiomeGenBase TutorialBiome = new BiomeTest(25);

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        // Initialize the custom commands
        CommandHandler.initCommands(event);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModPackets.init();

        LogHelper.init();

        // Initialize the configuration
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory()
                .getAbsolutePath()
                + File.separator
                + Reference.MOD_GROUP
                + File.separator + KybologyTimeTravel.MOD_ID_RAW + ".cfg"));

        TickRegistry.registerScheduledTickHandler(new TickHandlerWatch(),
                Side.CLIENT);
        TickRegistry.registerScheduledTickHandler(new TickHandlerCountdown(),
                Side.SERVER);

        proxy.registerSoundHandler();

        ModBlocks.init();

        ModItems.init();

        ModDimensions.init();

        ModEntities.init();

        TravellingRegistry.addTimeDestination(new DestinationRedwoodTime());
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new GeneratorRift());
        GameRegistry.registerWorldGenerator(new GeneratorOres());

        PlayerTrackerTT tracker = new PlayerTrackerTT();

        MinecraftForge.EVENT_BUS.register(tracker);
        GameRegistry.registerPlayerTracker(tracker);

        NetworkRegistry.instance().registerGuiHandler(instance, proxy);

        proxy.registerTileEntities();

        proxy.initRenderingAndTextures();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.registerEventHandlers();
    }
}
