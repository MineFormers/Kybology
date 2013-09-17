package de.mineformers.timetravel.travelling;

import net.minecraftforge.common.DimensionManager;
import de.mineformers.timetravel.lib.DimIds;
import de.mineformers.timetravel.world.WorldProviderTest;

/**
 * TimeTravel
 * 
 * ModDimensions
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ModDimensions {

	public static void init() {
		DimensionManager.registerProviderType(DimIds.REDWOOD,
		        WorldProviderTest.class, true);
		DimensionManager.registerDimension(DimIds.REDWOOD, DimIds.REDWOOD);
	}

}
