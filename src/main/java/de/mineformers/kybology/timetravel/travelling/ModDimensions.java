package de.mineformers.kybology.timetravel.travelling;

import net.minecraftforge.common.DimensionManager;
import de.mineformers.kybology.timetravel.lib.DimIds;
import de.mineformers.kybology.timetravel.world.WorldProviderTest;

/**
 * Kybology
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
