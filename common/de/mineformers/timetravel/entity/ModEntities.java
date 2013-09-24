package de.mineformers.timetravel.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import de.mineformers.timetravel.TimeTravel;

/**
 * TimeTravel
 * 
 * ModEntities
 * 
 * @author PaleoCrafter, Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ModEntities {

	public static void init() {
		EntityRegistry.registerModEntity(EntityRift.class, "EntityRift", 0,
		        TimeTravel.instance, 80, 3, true);
	}

}
