package de.mineformers.timetravel.block;

import cpw.mods.fml.common.registry.GameRegistry;
import de.mineformers.timetravel.item.ItemBlockTimeMachine;
import de.mineformers.timetravel.lib.BlockIds;
import de.mineformers.timetravel.lib.Strings;
import net.minecraft.block.Block;

/**
 * TimeTravel
 * 
 * ModBlocks
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ModBlocks {

	public static Block timeMachine;

	public static void init() {
		timeMachine = new BlockTimeMachine(BlockIds.TIMEMACHINE);
		
		GameRegistry.registerBlock(timeMachine, ItemBlockTimeMachine.class,
		        Strings.TIMEMACHINE_NAME);
	}
	
}
