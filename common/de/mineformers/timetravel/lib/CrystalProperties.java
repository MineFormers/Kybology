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
			return "MaxStorage";
		}

		public String getDisplayKey() {
			return "storage";
		}
	},
	TRANSFER() {
		@Override
		public int getValue(int i) {
			return transferValues[i];
		}

		@Override
		public String getValueName() {
			return "TransferRate";
		}

		public String getDisplayKey() {
			return "transfer";
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

		public String getDisplayKey() {
			return "nothing";
		}
	};

	private static int[] storageValues = { 2000, 5000, 10000 };
	private static int[] transferValues = { 20, 50, 100 };

	public abstract int getValue(int i);

	public abstract String getValueName();

	public abstract String getDisplayKey();

}
