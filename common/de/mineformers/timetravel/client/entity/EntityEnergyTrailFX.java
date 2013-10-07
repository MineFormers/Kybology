package de.mineformers.timetravel.client.entity;

import de.mineformers.timetravel.lib.Icons;
import net.minecraft.world.World;

/**
 * TimeTravel
 * 
 * EntityEnergyTrailFX
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class EntityEnergyTrailFX extends EntityTimeTravelFX {

    private double startX, startY, startZ;
    private double destX, destY, destZ;

    public EntityEnergyTrailFX(World world, double x, double y, double z,
            double destX, double destY, double destZ) {
        super(world, x, y, z, 0, 0, 0);
        this.setParticleIcon(Icons.particleEnergyTrail);
        posX = x;
        posY = y;
        posZ = z;
        this.noClip = true;
        this.particleBlue = 0F;
        this.particleGreen = 1F;
        this.particleRed = 1F;
        particleScale = 1;
        particleMaxAge = 20;

        this.motionX = (destX - posX) / particleMaxAge;
        this.motionY = (destY - posY) / particleMaxAge;
        this.motionZ = (destZ - posZ) / particleMaxAge;

        this.startX = x;
        this.startY = y;
        this.startZ = z;
        this.destX = destX;
        this.destY = destY;
        this.destZ = destZ;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.motionX = (destX - startX) / particleMaxAge;
        this.motionY = (destY - startY) / particleMaxAge;
        this.motionZ = (destZ - startZ) / particleMaxAge;
    }

}
