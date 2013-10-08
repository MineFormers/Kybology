package de.mineformers.timetravel.client.particle.updater;

import de.mineformers.timetravel.client.particle.ParticleFX;

/**
 * TimeTravel
 * 
 * UpdaterSphere
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class UpdaterSphere implements IParticleUpdater {

    @Override
    public void onUpdate(ParticleFX fx) {
        double rMax = 0.76;
        double speed = 0.3;
        double r = Math.sqrt(Math.pow(rMax, 2)
                - Math.pow(fx.getPos().y - fx.getStartPosition().y - rMax, 2));

        fx.motionX = r * Math.sin(fx.getTicksExisted() * speed) - r
                * Math.sin((fx.getTicksExisted() + 1) * speed);
        fx.motionZ = r * Math.cos(fx.getTicksExisted() * speed) - r
                * Math.cos((fx.getTicksExisted() + 1) * speed);

        fx.motionY = speed / 100 * 1;

        fx.moveEntity(fx.motionX, fx.motionY, fx.motionZ);
    }

}