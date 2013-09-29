package de.mineformers.timetravel;

import java.io.File;

import net.minecraft.client.Minecraft;
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
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.api.TravellingRegistry;
import de.mineformers.timetravel.block.ModBlocks;
import de.mineformers.timetravel.client.gui.overlay.GuiOverlayWatch;
import de.mineformers.timetravel.configuration.ConfigurationHandler;
import de.mineformers.timetravel.core.handler.PlayerTrackerTT;
import de.mineformers.timetravel.core.handler.TickHandlerCountdown;
import de.mineformers.timetravel.core.handler.TickHandlerWatch;
import de.mineformers.timetravel.core.proxy.CommonProxy;
import de.mineformers.timetravel.core.util.LogHelper;
import de.mineformers.timetravel.creativetab.CreativeTabTimeTravel;
import de.mineformers.timetravel.entity.ModEntities;
import de.mineformers.timetravel.item.ModItems;
import de.mineformers.timetravel.lib.Reference;
import de.mineformers.timetravel.network.PacketHandler;
import de.mineformers.timetravel.travelling.DestinationRedwoodTime;
import de.mineformers.timetravel.travelling.ModDimensions;
import de.mineformers.timetravel.world.GeneratorOres;
import de.mineformers.timetravel.world.GeneratorRift;
import de.mineformers.timetravel.world.biome.BiomeTest;

/**
 * TimeTravel
 * 
 * TimeTravel
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@Mod(modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.VERSION_NUMBER,
        dependencies = Reference.DEPENDENCIES,
        certificateFingerprint = Reference.FINGERPRINT)
@NetworkMod(channels = { Reference.CHANNEL_NAME },
        clientSideRequired = true,
        serverSideRequired = false,
        packetHandler = PacketHandler.class)
public class TimeTravel {

	@Instance(Reference.MOD_ID)
	public static TimeTravel instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,
	        serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final CreativeTabs tabTimeTravel = new CreativeTabTimeTravel(
	        CreativeTabs.getNextID(), Reference.MOD_ID);

	public static final BiomeGenBase TutorialBiome = new BiomeTest(25);

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LogHelper.init();

		// Initialize the configuration
		ConfigurationHandler.init(new File(event.getModConfigurationDirectory()
		        .getAbsolutePath()
		        + File.separator
		        + Reference.CHANNEL_NAME
		        + File.separator + Reference.MOD_ID + ".cfg"));

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
		MinecraftForge.EVENT_BUS.register(new GuiOverlayWatch(Minecraft
		        .getMinecraft()));
	}
}
