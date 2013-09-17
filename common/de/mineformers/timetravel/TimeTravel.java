package de.mineformers.timetravel;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
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
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.api.TravellingRegistry;
import de.mineformers.timetravel.block.ModBlocks;
import de.mineformers.timetravel.client.gui.overlay.GuiOverlayWatch;
import de.mineformers.timetravel.configuration.ConfigurationHandler;
import de.mineformers.timetravel.core.handler.TickHandlerWatch;
import de.mineformers.timetravel.core.proxy.CommonProxy;
import de.mineformers.timetravel.core.util.LogHelper;
import de.mineformers.timetravel.item.ModItems;
import de.mineformers.timetravel.lib.DimIds;
import de.mineformers.timetravel.lib.Reference;
import de.mineformers.timetravel.network.PacketHandler;
import de.mineformers.timetravel.travelling.DestinationRedwoodTime;
import de.mineformers.timetravel.world.WorldProviderTest;
import de.mineformers.timetravel.world.biome.BiomeTest;

/**
 * TimeTravel
 * 
 * TimeTravel
 * 
 * @author PaleoCrafter
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

		proxy.registerSoundHandler();

		ModBlocks.init();

		ModItems.init();

		DimensionManager.registerProviderType(DimIds.REDWOOD,
		        WorldProviderTest.class, true);
		DimensionManager.registerDimension(DimIds.REDWOOD, DimIds.REDWOOD);

		TravellingRegistry.addTimeDestination(new DestinationRedwoodTime());
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
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
