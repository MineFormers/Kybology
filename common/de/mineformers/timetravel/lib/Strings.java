package de.mineformers.timetravel.lib;

/**
 * TimeTravel
 * 
 * Strings
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Strings {

	public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase()
			+ ":";

	// Block names
	// Time Machine
	public static final String TIMEMACHINE_NAME = "timeMachine";
	public static final String TM_BASE_NAME = "base";
	public static final String TM_PANEL_NAME = "panel";
	public static final String TM_STAIRS_NAME = "stairs";
	public static final String TM_PILLAR_NAME = "pillar";
	public static final String TM_MODULE_NAME = "module";

	public static final String ENERGY_EXTRACTOR_NAME = "energyExtractor";
	public static final String TEST_NAME = "testBlock";

	public static final String CRYSTAL_ORE_NAME = "crystalOre";

	// Item names
	public static final String WATCH_NAME = "pocketWatch";
	public static final String CRYSTAL_NAME = "crystal";
	public static final String[] CRYSTAL_QUALITIES = { "pure", "purified" };
	public static final String[] CRYSTAL_COLORS = { "blue", "red", "gold" };

	// TE names
	public static final String TE_TIMEMACHINE_NAME = "tileEntityTimeMachine";
	public static final String TE_ENERGY_EXTRACTOR_NAME = "tileEntityEnergyExtractor";
	public static final String TE_CRYSTAL_ORE_NAME = "tileEntityCrystalOre";

	// NBT keys
	public static final String NBT_TE_STATE_KEY = "teState";
	public static final String NBT_TE_CUSTOM_NAME = "CustomName";
	public static final String NBT_TE_DIRECTION_KEY = "teDirection";

	public static final String NBT_CRYSTAL_TYPE = "Type";
	public static final String NBT_CRYSTAL_QUALITY = "Quality";
	public static final String NBT_CRYSTAL_COLOR = "Color";

	// Container names
	public static final String CONTAINER_EXTRACTOR = "extractor";
}
