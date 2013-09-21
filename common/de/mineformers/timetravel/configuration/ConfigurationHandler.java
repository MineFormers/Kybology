package de.mineformers.timetravel.configuration;

import java.io.File;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import de.mineformers.timetravel.lib.BlockIds;
import de.mineformers.timetravel.lib.DimIds;
import de.mineformers.timetravel.lib.ItemIds;
import de.mineformers.timetravel.lib.Reference;
import de.mineformers.timetravel.lib.Strings;
import net.minecraftforge.common.Configuration;

/**
 * TimeTravel
 * 
 * ConfigurationHandler
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ConfigurationHandler {

	public static Configuration configuration;

	public static final String CATEGORY_DIMENSIONS = "dimensions";

	public static void init(File configFile) {

		configuration = new Configuration(configFile);

		try {
			configuration.load();

			/* Block configs */
			BlockIds.TIMEMACHINE = configuration.getBlock(
			        Strings.TIMEMACHINE_NAME, BlockIds.TIMEMACHINE_DEFAULT)
			        .getInt(BlockIds.TIMEMACHINE_DEFAULT);
			BlockIds.TEST = configuration.getBlock(Strings.TEST_NAME,
			        BlockIds.TEST_DEFAULT).getInt(BlockIds.TEST_DEFAULT);

			ItemIds.WATCH = configuration.getItem(Strings.WATCH_NAME,
			        ItemIds.WATCH_DEFAULT).getInt(ItemIds.WATCH_DEFAULT);

			DimIds.REDWOOD = configuration.get("Dimensions", "redwood",
			        DimIds.REDWOOD_DEFAULT, "Redwood dimension").getInt(
			        DimIds.REDWOOD_DEFAULT);

			/* Item configs */
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME
			        + " has had a problem loading its configuration");
		} finally {
			configuration.save();
		}
	}

	public static void set(String categoryName, String propertyName,
	        String newValue) {

		configuration.load();
		if (configuration.getCategoryNames().contains(categoryName)) {
			if (configuration.getCategory(categoryName).containsKey(
			        propertyName)) {
				configuration.getCategory(categoryName).get(propertyName)
				        .set(newValue);
			}
		}
		configuration.save();
	}

}
