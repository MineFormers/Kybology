package de.mineformers.kybology.core.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import de.mineformers.kybology.core.client.particle.updater.IParticleUpdater;
import de.mineformers.kybology.core.util.Vector3;

/**
 * Kybology
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

    public float scale;
    public int ticksExisted;
    public int ticksLifespan;
    private int color;

    private String texture;

    public ParticleFX(World world, double x, double y, double z,
            IParticleUpdater updater, int lifespan, String texture) {
        super(world, x, y, z);
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        this.updater = updater;
        this.startPosition = new Vector3(x, y, z);
        this.scale = 1;
        this.ticksLifespan = lifespan;
        this.texture = texture;
    }

    @Override
    public void onUpdate() {
        updater.onUpdate(this);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.ticksExisted++ >= this.ticksLifespan) {
            this.setDead();
        }
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTicks,
            float arX, float arXZ, float arZ, float arYZ, float arXY) {
        tessellator.draw();

        updater.render(tessellator, partialTicks, arX, arXZ, arZ, arYZ, arXY,
                this);

        Minecraft.getMinecraft().renderEngine
                .bindTexture(EffectRenderer.particleTextures);
        tessellator.startDrawingQuads();
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
    }

    public void setR(int r) {
        color &= 0x00FFFF;
        color |= (r << 16) & 0xFF0000;
    }

    public void setB(int b) {
        color &= 0xFFFF00;
        color |= b & 0x0000FF;
    }

    public void setG(int g) {
        color &= 0xFF00FF;
        color |= (g << 8) & 0x00FF00;
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

    public int getAlpha() {
        return (int) (particleAlpha * 255);
    }

    public Vector3 getStartPosition() {
        return startPosition;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTexture() {
        return texture;
    }

}
