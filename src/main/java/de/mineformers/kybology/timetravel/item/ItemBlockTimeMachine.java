package de.mineformers.kybology.timetravel.item;

import de.mineformers.kybology.core.item.ItemBlockSub;
import de.mineformers.kybology.timetravel.lib.Strings;

/**
 * Kybology
 * 
 * ItemBlockTimeMachine
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ItemBlockTimeMachine extends ItemBlockSub {

	public ItemBlockTimeMachine(int id) {
		super(id, Strings.TIMEMACHINE_NAME, new String[] {
		        Strings.TM_BASE_NAME, Strings.TM_PANEL_NAME,
		        Strings.TM_STAIRS_NAME, Strings.TM_PILLAR_NAME,
		        Strings.TM_MODULE_NAME });
	}

}
