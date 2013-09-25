package de.mineformers.timetravel.lib;
/**
 * TimeTravel
 * 
 * IEnergyStorage
 * 
 * @author Weneg
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public enum CrystalProperties {
	STORAGE() {
		@Override
		public int getValue(int i) {
			return storageValues[i];
		}

		@Override
		public String getValueName() {
			return "maxStorage";
		}
	},
	TRANSFER() {
		@Override
		public int getValue(int i) {
			return transferValues[i];
		}

		@Override
		public String getValueName() {
			return "transferRate";
		}
	
	},
	NOTHING() {
		@Override
		public int getValue(int i) {
			return 0;
		}

		@Override
		public String getValueName() {
			return null;
		}
	};
	private static int[] storageValues = { 2000, 5000, 10000 };
	private static int[] transferValues = { 20, 50, 100 };

	public abstract int getValue(int i);
	public abstract String getValueName();

}
