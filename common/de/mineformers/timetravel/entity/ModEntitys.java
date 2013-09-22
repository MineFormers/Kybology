package de.mineformers.timetravel.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import de.mineformers.timetravel.TimeTravel;

public class ModEntitys {
	public static void init() {
		EntityRegistry.registerModEntity(Rift.class, "EntityRift", 0, TimeTravel.instance, 80, 3, true);

	}
}
