package de.mineformers.timetravel.client.entity;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

/**
 * TimeTravel
 * 
 * EntityTimeTravelFX
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class EntityTimeTravelFX extends EntityFX {

    public EntityTimeTravelFX(World world, double x, double y, double z,
            double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

}
