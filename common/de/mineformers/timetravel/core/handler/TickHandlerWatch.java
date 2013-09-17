package de.mineformers.timetravel.core.handler;

import java.util.EnumSet;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import de.mineformers.timetravel.lib.Reference;

/**
 * TimeTravel
 * 
 * TickHandlerWatch
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TickHandlerWatch implements IScheduledTickHandler {

	public static int min;
	public static int sec;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		sec += 1;
		if (sec >= 60) {
			sec = 0;
			min += 1;
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return Reference.MOD_NAME + ": " + this.getClass().getSimpleName();
	}

	@Override
	public int nextTickSpacing() {
		return 20;
	}

}
