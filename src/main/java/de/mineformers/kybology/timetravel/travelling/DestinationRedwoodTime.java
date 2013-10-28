package de.mineformers.kybology.timetravel.travelling;

import de.mineformers.kybology.api.travelling.DestinationTime;
import de.mineformers.kybology.core.util.ResourceHelper;
import de.mineformers.kybology.timetravel.lib.DimIds;

/**
 * Kybology
 * 
 * DestinationRedwoodTime
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class DestinationRedwoodTime extends DestinationTime {

	public DestinationRedwoodTime() {
		super(DimIds.REDWOOD, ResourceHelper
		        .getCoreResource("textures/guis/previews/redwood.png"));
	}

}
