package de.mineformers.kybology.api;

import java.util.ArrayList;
import java.util.Iterator;

import de.mineformers.kybology.api.travelling.DestinationTime;

/**
 * Kybology
 * 
 * TravellingRegistry
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TravellingRegistry {

	private static ArrayList<DestinationTime> timeDestinations;

	static {
		timeDestinations = new ArrayList<DestinationTime>();
	}

	public static void addTimeDestination(DestinationTime destination) {
		if (!timeDestinations.contains(destination)) {
			timeDestinations.add(destination);
		}
	}

	public static DestinationTime getTimeDestination(int id, boolean isDimId) {
		if (!isDimId) {
			return timeDestinations.get(id);
		} else {
			Iterator<DestinationTime> iter = timeDestinations.iterator();

			while (iter.hasNext()) {
				DestinationTime dest = iter.next();
				if (dest.getDimensionId() == id)
					return dest;
			}
		}

		return null;
	}

	public static int getTimeDestinationCount() {
		return timeDestinations.size();
	}

	public static ArrayList<DestinationTime> getTimeDestinations() {
		return timeDestinations;
	}

	public static boolean isValidTimeDestination(int dimId) {
		Iterator<DestinationTime> iter = timeDestinations.iterator();

		while (iter.hasNext()) {
			if (iter.next().getDimensionId() == dimId)
				return true;
		}

		return false;
	}

}
