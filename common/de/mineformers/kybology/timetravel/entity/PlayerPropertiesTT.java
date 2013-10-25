package de.mineformers.kybology.timetravel.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import de.mineformers.kybology.core.util.Vector3;

/**
 * Kybology
 * 
 * PlayerPropertiesTT
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PlayerPropertiesTT implements IExtendedEntityProperties {

    public static final String IDENTIFIER = "KybologyProperties";

    private long timeStarted;
    private int secondsAvail;
    private int secondsLeft;
    private int tmDimension;
    private Vector3 tmCoordinates;

    public PlayerPropertiesTT() {
        setTmData(0, 0, 0, 0);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        compound.setLong("TimeStarted", timeStarted);
        compound.setInteger("SecondsAvail", secondsAvail);
        compound.setInteger("SecondsLeft", secondsLeft);
        compound.setIntArray("TMData", new int[] { tmDimension,
                (int) tmCoordinates.x, (int) tmCoordinates.y,
                (int) tmCoordinates.z });
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        timeStarted = compound.getLong("TimeStarted");
        secondsAvail = compound.getInteger("SecondsAvail");
        secondsLeft = compound.getInteger("SecondsLeft");

        int[] data = compound.getIntArray("TMData");
        setTmData(data[0], data[1], data[2], data[3]);
    }

    @Override
    public void init(Entity entity, World world) {
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(long timeStarted) {
        this.timeStarted = timeStarted;
    }

    public int getSecondsAvail() {
        return secondsAvail;
    }

    public void setSecondsAvail(int secondsAvail) {
        this.secondsAvail = secondsAvail;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public void setSecondsLeft(int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    public Vector3 getTmCoordinates() {
        return tmCoordinates;
    }

    public int getTmDimension() {
        return tmDimension;
    }

    public void setTmData(int dim, int x, int y, int z) {
        this.tmDimension = dim;
        this.tmCoordinates = new Vector3(x, y, z);
    }

    public static PlayerPropertiesTT getByEntity(EntityPlayer player) {
        if (player.getExtendedProperties(IDENTIFIER) == null)
            player.registerExtendedProperties(IDENTIFIER,
                    new PlayerPropertiesTT());

        return (PlayerPropertiesTT) player.getExtendedProperties(IDENTIFIER);
    }

}
