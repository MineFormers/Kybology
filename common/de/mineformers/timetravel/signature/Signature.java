/**
 * Time Travel
 *
 * Signature
 *
 * @author Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
package de.mineformers.timetravel.signature;

import net.minecraft.nbt.NBTTagCompound;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class Signature {
	private int signature;



	/**
	 * @param readInt
	 */
	public Signature(int signature) {
		this.signature = signature;
	}

	/**
	 * 
	 */
	public Signature() {
	}

	public int getSignature() {
		return signature;
	}

	public void setSignature(int signature) {
		this.signature = signature;
	}
	public boolean compareSignature(Signature sig) {
		return signature == sig.getSignature();
	}

	/**
	 * @param data
	 */
	public void writeByteArrayDataOutput(ByteArrayDataOutput data) {
		data.write(signature);
		
	}

	/**
	 * @param data
	 * @return
	 */
	public static Signature getSignature(ByteArrayDataInput data) {
		return new Signature(data.readInt());
	}

	/**
	 * @param compound
	 * @return
	 */
	public static Signature createFromNBT(NBTTagCompound compound) {
		
		return new Signature(compound.getCompoundTag("signature").getInteger("signature"));
	}

	/**
	 * @return
	 */
	public NBTTagCompound createNBTTagCompound() {
		NBTTagCompound temp = new NBTTagCompound();
		temp.setInteger("signature", signature);
		return temp;
	}

	
}
