package de.mineformers.timetravel.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import de.mineformers.timetravel.client.audio.SoundHandler;
import de.mineformers.timetravel.client.renderer.item.ItemTimeMachineRenderer;
import de.mineformers.timetravel.client.renderer.tileentity.TileEntityTimeMachineRenderer;
import de.mineformers.timetravel.lib.BlockIds;
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
		MinecraftForgeClient.registerItemRenderer(BlockIds.TIMEMACHINE,
		        new ItemTimeMachineRenderer());
	}

	@Override
	public void registerSoundHandler() {
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
	}

}
