package de.mineformers.kybology.core.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.kybology.core.client.particle.updater.UpdaterEnergyTrail;
import de.mineformers.kybology.core.util.Vector3;

/**
 * Kybology
 * 
 * ModParticles
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public enum ModParticles {

    ENERGY_TRAIL() {
        @Override
        public ParticleFX constructParticle(World world, double x, double y,
                double z, Object... params) {
            ParticleFX particle = new ParticleFX(world, x, y, z,
                    new UpdaterEnergyTrail((Vector3) params[0]), 40,
                    "energyTrail.png");

            particle.noClip = true;
            particle.setScale(0.125F);
            particle.setColor(0xFF0000);

            return particle;
        }
    };

    public void spawnParticles(World world, double x, double y, double z,
            Object... params) {
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

            try {
                FMLClientHandler.instance().getClient().effectRenderer
                        .addEffect(this.constructParticle(world, x, y, z,
                                params));
            } catch (ClassCastException e) {
                throw new RuntimeException(
                        "Error during particle construction: Wrong argument passed",
                        e);
            }
        }
    }

    public abstract ParticleFX constructParticle(World world, double x,
            double y, double z, Object... params);

}
