package de.mineformers.timetravel.block;

import cpw.mods.fml.common.registry.GameRegistry;
import de.mineformers.timetravel.item.ItemBlockCrystalOre;
import de.mineformers.timetravel.item.ItemBlockTimeMachine;
import de.mineformers.timetravel.lib.BlockIds;
import de.mineformers.timetravel.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

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
	public static Block tests;
	public static Block energyExtractor;
	public static Block crystalOre;

	public static void init() {
		timeMachine = new BlockTimeMachine(BlockIds.TIMEMACHINE);
		tests = new BlockTest(BlockIds.TEST);
		energyExtractor = new BlockEnergyExtractor(BlockIds.EXTRACTOR,
				Material.rock);
		crystalOre = new BlockCrystalOre(BlockIds.CRYSTAL_ORE);

		GameRegistry.registerBlock(timeMachine, ItemBlockTimeMachine.class,
				Strings.TIMEMACHINE_NAME);
		GameRegistry.registerBlock(tests, Strings.TEST_NAME);
		GameRegistry.registerBlock(energyExtractor,
				Strings.ENERGY_EXTRACTOR_NAME);
		GameRegistry.registerBlock(crystalOre, ItemBlockCrystalOre.class,
				Strings.CRYSTAL_ORE_NAME);
	}

}
