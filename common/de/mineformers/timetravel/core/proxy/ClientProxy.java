package de.mineformers.timetravel.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import de.mineformers.timetravel.client.audio.SoundHandler;
import de.mineformers.timetravel.client.renderer.block.BlockTestRenderer;
import de.mineformers.timetravel.client.renderer.entity.RenderRift;
import de.mineformers.timetravel.client.renderer.item.ItemPocketWatchRenderer;
import de.mineformers.timetravel.client.renderer.item.ItemTimeMachineRenderer;
import de.mineformers.timetravel.client.renderer.tileentity.TileEntityTimeMachineRenderer;
import de.mineformers.timetravel.entity.EntityRift;
import de.mineformers.timetravel.lib.BlockIds;
import de.mineformers.timetravel.lib.ItemIds;
import de.mineformers.timetravel.lib.RenderIds;
import de.mineformers.timetravel.tileentity.TileTimeMachine;

/**
 * TimeTravel
 * 
 * ClientProxy
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void registerTileEntities() {
		super.registerTileEntities();

		ClientRegistry.bindTileEntitySpecialRenderer(TileTimeMachine.class,
		        new TileEntityTimeMachineRenderer());
	}

	@Override
	public void initRenderingAndTextures() {
		super.initRenderingAndTextures();

		RenderIds.testRender = RenderingRegistry.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(new BlockTestRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityRift.class,
		        new RenderRift());
		
		MinecraftForgeClient.registerItemRenderer(BlockIds.TIMEMACHINE,
		        new ItemTimeMachineRenderer());
		MinecraftForgeClient.registerItemRenderer(ItemIds.WATCH,
		        new ItemPocketWatchRenderer());
	}

	@Override
	public void registerSoundHandler() {
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
	}

}
