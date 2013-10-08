package de.mineformers.timetravel.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import de.mineformers.timetravel.client.particle.updater.IParticleUpdater;
import de.mineformers.timetravel.core.util.Vector3;

/**
 * TimeTravel
 * 
 * ParticleFX
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ParticleFX extends EntityFX {

    private Vector3 startPosition;

    private IParticleUpdater updater;

    private int color;

    public ParticleFX(World world, double x, double y, double z,
            IParticleUpdater updater, int lifespan, Icon icon) {
        super(world, x, y, z);
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        this.updater = updater;
        this.startPosition = new Vector3(x, y, z);
        this.particleScale = 1;
        this.particleMaxAge = lifespan;
        this.setParticleIcon(icon);
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        updater.onUpdate(this);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
    }

    public Vector3 getPos() {
        return new Vector3(posX, posY, posZ);
    }

    public void setColor(int r, int g, int b) {
        this.setColor(r, g, b, 1F);
    }

    public void setColor(int r, int g, int b, int alpha) {
        this.setColor(r, g, b, (1F / 255) * alpha);
    }

    public void setColor(int r, int g, int b, float alpha) {
        this.setColor(((r << 16) & 0xFF0000) | ((g << 8) & 0x00FF00)
                | (b & 0x0000FF), alpha);
    }

    public void setColor(int hexCode) {
        this.setColor(hexCode, 1F);
    }

    public void setColor(int hexCode, int alpha) {
        this.setColor(hexCode, (1F / 255f) * alpha);
    }

    public void setColor(int hexCode, float alpha) {
        this.color = hexCode;
        this.particleAlpha = alpha;
        this.particleRed = getR();
        this.particleGreen = getG();
        this.particleBlue = getB();
    }

    public void setR(int r) {
        color &= 0x00FFFF;
        color |= (r << 16) & 0xFF0000;
        setColor(color, particleAlpha);
    }

    public void setB(int b) {
        color &= 0xFFFF00;
        color |= b & 0x0000FF;
        setColor(color, particleAlpha);
    }

    public void setG(int g) {
        color &= 0xFF00FF;
        color |= (g << 8) & 0x00FF00;
        setColor(color, particleAlpha);
    }

    public int getR() {
        return (color & 0xFF0000) >> 16;
    }

    public int getG() {
        return (color & 0x00FF00) >> 8;
    }

    public int getB() {
        return (color & 0x0000FF);
    }

    public Vector3 getStartPosition() {
        return startPosition;
    }

    public void setScale(float scale) {
        this.particleScale = scale;
    }

    public int getTicksExisted() {
        return particleAge;
    }
    
    public int getLifespan() {
        return particleMaxAge;
    }

}
