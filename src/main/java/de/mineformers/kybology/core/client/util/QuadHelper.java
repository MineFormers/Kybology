package de.mineformers.kybology.core.client.util;

import net.minecraft.util.MathHelper;
import de.mineformers.kybology.core.util.Vector3;

/**
 * Kybology
 * 
 * QuadHelper
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class QuadHelper {
    public double x;
    public double y;
    public double z;
    public double angle;

    public QuadHelper(double ang, double xx, double yy, double zz) {
        this.x = xx;
        this.y = yy;
        this.z = zz;
        this.angle = ang;
    }

    public static QuadHelper setAxis(Vector3 vec, double angle) {
        angle *= 0.5D;
        double d4 = MathHelper.sin((float) angle);
        return new QuadHelper(MathHelper.cos((float) angle), vec.x * d4, vec.y
                * d4, vec.z * d4);
    }

    public void rotate(Vector3 vec) {
        double d = -this.x * vec.x - this.y * vec.y - this.z * vec.z;
        double d1 = this.angle * vec.x + this.y * vec.z - this.z * vec.y;
        double d2 = this.angle * vec.y - this.x * vec.z + this.z * vec.x;
        double d3 = this.angle * vec.z + this.x * vec.y - this.y * vec.x;
        vec.x = (d1 * this.angle - d * this.x - d2 * this.z + d3 * this.y);
        vec.y = (d2 * this.angle - d * this.y + d1 * this.z - d3 * this.x);
        vec.z = (d3 * this.angle - d * this.z - d1 * this.y + d2 * this.x);
    }
}