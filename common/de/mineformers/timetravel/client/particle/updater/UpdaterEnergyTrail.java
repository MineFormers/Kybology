package de.mineformers.timetravel.client.particle.updater;

import de.mineformers.timetravel.client.particle.ParticleFX;
import de.mineformers.timetravel.core.util.Vector3;

/**
 * TimeTravel
 * 
 * UpdaterEnergyTrail
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class UpdaterEnergyTrail implements IParticleUpdater {

    private Vector3 dest;

    public UpdaterEnergyTrail(Vector3 destination) {
        this.dest = destination;
    }

    @Override
    public void onUpdate(ParticleFX fx) {
        double motionX = (dest.x - fx.getStartPosition().x) / fx.getLifespan();
        double motionY = (dest.y - fx.getStartPosition().y) / fx.getLifespan();
        double motionZ = (dest.z - fx.getStartPosition().z) / fx.getLifespan();
        
        fx.moveEntity(motionX, motionY, motionZ);
    }

}
