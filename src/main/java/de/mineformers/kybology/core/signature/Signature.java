package de.mineformers.kybology.core.signature;

import java.util.Arrays;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

import com.google.common.collect.HashBasedTable;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

/**
 * Time Travel
 * 
 * Signature
 * 
 * @author Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Signature {

    public enum Instrument {
        BASS_GUITAR, SNARE_DRUM, CLICKS, BASS_DRUM, PIANO
    }

    private HashBasedTable<Instrument, Short, Byte> melody;
    private int signature;

    public Signature(int signature) {
        this.signature = signature;
        melody = HashBasedTable.create();
    }

    public Signature() {
    }

    public int getSignature() {
        return signature;
    }

    public void setSignature(int signature) {
        this.signature = signature;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Signature) {
            Signature sig = (Signature) o;
            if (!Arrays.equals(getInstruments(), sig.getInstruments()))
                return false;
            if (getMelodyLength() != sig.getMelodyLength())
                return false;
            for (Instrument instrument : getInstruments()) {
                Map<Short, Byte> tones = melody.row(instrument);
                for (short pos : tones.keySet()) {
                    if (sig.getTone(instrument, pos) != tones.get(pos))
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    public byte getTone(Instrument instrument, short position) {
        return melody.get(instrument, position) == null ? -1 : melody.get(
                instrument, position);
    }

    public void setTone(Instrument instrument, short position, byte tone) {
        melody.put(instrument, position, tone);
    }

    public Instrument[] getInstruments() {
        return melody.rowKeySet().toArray(
                new Instrument[melody.rowKeySet().size()]);
    }

    public int getMelodyLength() {
        int longest = 0;

        for (Instrument instrument : Instrument.values()) {
            Map<Short, Byte> row = melody.row(instrument);
            if (row != null) {
                if (row.size() > longest) {
                    longest = row.size();
                }
            }
        }

        return longest;
    }

    public void readFromByteInput(ByteArrayDataInput in) {
        signature = in.readInt();
        int count = in.readInt();
        melody.clear();
        for (int i = 0; i < count; i++) {
            int result = in.readInt();
            byte instrument = (byte) ((result >> 24) & 0xFF);
            short pos = (short) ((result >> 8) & 0xFFFF);
            byte tone = (byte) (result & 0xFF);
            melody.put(Instrument.values()[instrument], pos, tone);
        }
    }

    public void writeToByteOutput(ByteArrayDataOutput out) {
        out.writeInt(signature);
        out.writeInt(melody.size());
        for (Instrument instrument : getInstruments()) {
            Map<Short, Byte> tones = melody.row(instrument);
            for (short pos : tones.keySet()) {
                int result = (0xFF & instrument.ordinal()) << 24;
                result |= (0xFFFF & pos) << 8;
                result |= 0xFF & tones.get(pos);
                out.writeInt(result);
            }
        }
    }

    public void readFromNBTTagCompound(NBTTagCompound compound) {
        signature = compound.getInteger("Signature");
        melody.clear();
        for (int result : compound.getIntArray("Melody")) {
            byte instrument = (byte) ((result >> 24) & 0xFF);
            short pos = (short) ((result >> 8) & 0xFFFF);
            byte tone = (byte) (result & 0xFF);
            melody.put(Instrument.values()[instrument], pos, tone);
        }
    }

    public void writeToNBTTagCompound(NBTTagCompound compound) {
        compound.setInteger("Signature", signature);
        int[] notes = new int[melody.size()];
        int i = 0;
        for (Instrument instrument : getInstruments()) {
            Map<Short, Byte> tones = melody.row(instrument);
            for (short pos : tones.keySet()) {
                int result = (0xFF & instrument.ordinal()) << 24;
                result |= (0xFFFF & pos) << 8;
                result |= 0xFF & tones.get(pos);
                notes[i] = result;
                i++;
            }
        }
        compound.setIntArray("Melody", notes);
    }
}
