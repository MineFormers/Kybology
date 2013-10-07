package de.mineformers.timetravel.client.entity;

import java.lang.reflect.Constructor;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * TimeTravel
 * 
 * ModParticles
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public enum ModParticles {

    ENERGY_TRAIL(EntityEnergyTrailFX.class);

    private Class<? extends EntityTimeTravelFX> particleClass;

    private ModParticles(Class<? extends EntityTimeTravelFX> particleClass) {
        this.particleClass = particleClass;
    }

    public void spawnParticles(World world, double x, double y, double z,
            double motionX, double motionY, double motionZ) {
        Minecraft mc = FMLClientHandler.instance().getClient();

        if (mc != null && mc.renderViewEntity != null
                && mc.effectRenderer != null) {
            int particleSetting = mc.gameSettings.particleSetting;

            if (particleSetting == 2
                    || (particleSetting == 1 && world.rand.nextInt(3) == 0))
                return;

            double distanceX = mc.renderViewEntity.posX - x;
            double distanceY = mc.renderViewEntity.posY - y;
            double distanceZ = mc.renderViewEntity.posZ - z;

            double maxDistance = Math.pow(16, 2);
            double distanceSquared = Math.pow(distanceX, 2)
                    + Math.pow(distanceY, 2) + Math.pow(distanceZ, 2);

            if (distanceSquared > maxDistance)
                return;

            if (!EntityTimeTravelFX.class.isAssignableFrom(particleClass))
                return;

            try {
                Constructor<? extends EntityTimeTravelFX> constructor = particleClass
                        .getConstructor(World.class, Double.TYPE, Double.TYPE,
                                Double.TYPE, Double.TYPE, Double.TYPE,
                                Double.TYPE);

                EntityTimeTravelFX entity = constructor.newInstance(world, x,
                        y, z, motionX, motionY, motionZ);
                mc.effectRenderer.addEffect(entity);
            } catch (Throwable e) {
                throw new RuntimeException(
                        "Reflection exception during particle construction", e);
            }
        }
    }

    public Class<? extends EntityTimeTravelFX> getParticleClass() {
        return particleClass;
    }

}
