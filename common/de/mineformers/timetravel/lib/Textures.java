package de.mineformers.timetravel.lib;

import net.minecraft.util.ResourceLocation;
import de.mineformers.timetravel.core.util.ResourceHelper;

/**
 * TimeTravel
 * 
 * Textures
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Textures {

	// Base file paths
	public static final String MODEL_SHEET_LOCATION = "textures/models/";
	public static final String GUI_LOCATION = "textures/guis/";

	// Model textures
	public static final ResourceLocation MODEL_TIMEMACHINE = ResourceHelper
	        .getResourceLocation(MODEL_SHEET_LOCATION + "timeMachine.png");
	public static final ResourceLocation MODEL_WATCH = ResourceHelper
	        .getResourceLocation(MODEL_SHEET_LOCATION + "watch.png");

	// GUI textures
	public static final ResourceLocation GUI_TIMEMACHINE = ResourceHelper
	        .getResourceLocation(GUI_LOCATION + "timeMachine.png");
	
	public static final ResourceLocation GUI_WIDGETS = ResourceHelper
	        .getResourceLocation(GUI_LOCATION + "widgets.png");
	public static final ResourceLocation GUI_OVERLAY = ResourceHelper
	        .getResourceLocation(GUI_LOCATION + "overlay.png");

}
