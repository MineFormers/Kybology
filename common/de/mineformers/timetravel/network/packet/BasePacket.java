package de.mineformers.timetravel.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.lib.Reference;

/**
 * TimeTravel
 * 
 * BasePacket
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public abstract class BasePacket {
    private static final BiMap<Integer, Class<? extends BasePacket>> idMap;

    static {
        ImmutableBiMap.Builder<Integer, Class<? extends BasePacket>> builder = ImmutableBiMap
                .builder();

        builder.put(Integer.valueOf(0), PacketOpenGui.class);
        builder.put(Integer.valueOf(1), PacketTileUpdate.class);
        builder.put(Integer.valueOf(2), PacketTimeMachineUpdate.class);
        builder.put(Integer.valueOf(3), PacketTMPanelUpdate.class);
        builder.put(Integer.valueOf(4), PacketTMModuleUpdate.class);
        builder.put(Integer.valueOf(5), PacketStartTravel.class);
        builder.put(Integer.valueOf(6), PacketUpdateWatch.class);
        builder.put(Integer.valueOf(7), PacketSpawnParticle.class);

        idMap = builder.build();
    }

    public static BasePacket constructPacket(int packetId)
            throws ProtocolException, InstantiationException,
            IllegalAccessException {
        Class<? extends BasePacket> clazz = idMap
                .get(Integer.valueOf(packetId));
        if (clazz == null) {
            throw new ProtocolException("Unknown Packet Id!");
        } else {
            return clazz.newInstance();
        }
    }

    @SuppressWarnings("serial")
    public static class ProtocolException extends Exception {

        public ProtocolException() {
        }

        public ProtocolException(String message, Throwable cause) {
            super(message, cause);
        }

        public ProtocolException(String message) {
            super(message);
        }

        public ProtocolException(Throwable cause) {
            super(cause);
        }
    }

    public final int getPacketId() {
        if (idMap.inverse().containsKey(getClass())) {
            return idMap.inverse().get(getClass()).intValue();
        } else {
            throw new RuntimeException("Packet " + getClass().getSimpleName()
                    + " is missing a mapping!");
        }
    }

    public final Packet makePacket() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte(getPacketId());
        write(out);
        return PacketDispatcher.getPacket(Reference.CHANNEL_NAME,
                out.toByteArray());
    }

    public abstract void write(ByteArrayDataOutput out);

    public abstract void read(ByteArrayDataInput in);

    public abstract void execute(EntityPlayer player, Side side);
}
