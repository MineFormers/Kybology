package de.mineformers.timetravel.travelling;

import de.mineformers.timetravel.api.travelling.DestinationTime;
import de.mineformers.timetravel.core.util.ResourceHelper;
import de.mineformers.timetravel.lib.DimIds;

/**
 * TimeTravel
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
		        .getResourceLocation("textures/guis/previews/redwood.png"));
	}

}
