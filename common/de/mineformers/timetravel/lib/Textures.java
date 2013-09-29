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
    public static final ResourceLocation MODEL_TM_MODULE_DEFAULT = ResourceHelper
            .getResourceLocation(MODEL_SHEET_LOCATION + "moduleDefault.png");
    public static final ResourceLocation MODEL_TM_MODULE_POWER = ResourceHelper
            .getResourceLocation(MODEL_SHEET_LOCATION + "modulePower.png");
    public static final ResourceLocation MODEL_TM_MODULE_TIME = ResourceHelper
            .getResourceLocation(MODEL_SHEET_LOCATION + "moduleTime.png");
    public static final ResourceLocation MODEL_TM_MODULE_SPACE = ResourceHelper
            .getResourceLocation(MODEL_SHEET_LOCATION + "moduleSpace.png");
    public static final ResourceLocation MODEL_TM_MODULE_ITEM = ResourceHelper
            .getResourceLocation(MODEL_SHEET_LOCATION + "moduleItem.png");
    public static final ResourceLocation MODEL_TM_MODULE_PLAYER = ResourceHelper
            .getResourceLocation(MODEL_SHEET_LOCATION + "modulePlayer.png");
    public static final ResourceLocation MODEL_WATCH = ResourceHelper
            .getResourceLocation(MODEL_SHEET_LOCATION + "watch.png");
    public static final ResourceLocation MODEL_CRYSTALS = ResourceHelper
            .getResourceLocation(MODEL_SHEET_LOCATION + "crystals.png");
    public static final ResourceLocation MODEL_EXTRACTOR = ResourceHelper
            .getResourceLocation(MODEL_SHEET_LOCATION + "extractor" + ".png");

    // GUI textures
    public static final ResourceLocation GUI_TIMEMACHINE = ResourceHelper
            .getResourceLocation(GUI_LOCATION + "timeMachine.png");

    public static final ResourceLocation GUI_WIDGETS = ResourceHelper
            .getResourceLocation(GUI_LOCATION + "widgets.png");
    public static final ResourceLocation GUI_OVERLAY = ResourceHelper
            .getResourceLocation(GUI_LOCATION + "overlay.png");

}
