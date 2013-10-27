package de.mineformers.kybology.timetravel.lib;

import net.minecraft.util.ResourceLocation;
import de.mineformers.kybology.core.util.ResourceHelper;

/**
 * Kybology
 * 
 * Textures
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class Textures {

    // Base file path
    public static final String MODEL_SHEET_LOCATION = "textures/models/";
    public static final String GUI_SHEET_LOCATION = "textures/guis/";
    public static final String PARTICLE_SHEET_LOCATION = "textures/particles/";

    // Model textures
    public static final ResourceLocation MODEL_TIMEMACHINE = ResourceHelper
            .getCoreResource(MODEL_SHEET_LOCATION + "timeMachine.png");
    public static final ResourceLocation MODEL_TM_MODULE_DEFAULT = ResourceHelper
            .getCoreResource(MODEL_SHEET_LOCATION + "moduleDefault.png");
    public static final ResourceLocation MODEL_TM_MODULE_POWER = ResourceHelper
            .getCoreResource(MODEL_SHEET_LOCATION + "modulePower.png");
    public static final ResourceLocation MODEL_TM_MODULE_TIME = ResourceHelper
            .getCoreResource(MODEL_SHEET_LOCATION + "moduleTime.png");
    public static final ResourceLocation MODEL_TM_MODULE_SPACE = ResourceHelper
            .getCoreResource(MODEL_SHEET_LOCATION + "moduleSpace.png");
    public static final ResourceLocation MODEL_TM_MODULE_ITEM = ResourceHelper
            .getCoreResource(MODEL_SHEET_LOCATION + "moduleItem.png");
    public static final ResourceLocation MODEL_TM_MODULE_PLAYER = ResourceHelper
            .getCoreResource(MODEL_SHEET_LOCATION + "modulePlayer.png");
    public static final ResourceLocation MODEL_WATCH = ResourceHelper
            .getCoreResource(MODEL_SHEET_LOCATION + "watch.png");
    public static final ResourceLocation MODEL_CRYSTALS = ResourceHelper
            .getCoreResource(MODEL_SHEET_LOCATION + "crystals.png");
    public static final ResourceLocation MODEL_EXTRACTOR = ResourceHelper
            .getCoreResource(MODEL_SHEET_LOCATION + "extractor" + ".png");

    // GUI textures
    public static final ResourceLocation GUI_TIMEMACHINE = ResourceHelper
            .getCoreResource(GUI_SHEET_LOCATION + "timeMachine.png");

    public static final ResourceLocation GUI_WIDGETS = ResourceHelper
            .getCoreResource(GUI_SHEET_LOCATION + "widgets.png");
    public static final ResourceLocation GUI_OVERLAY = ResourceHelper
            .getCoreResource(GUI_SHEET_LOCATION + "overlay.png");

    public static final ResourceLocation PARTICLE_ENERGY = ResourceHelper
            .getCoreResource(PARTICLE_SHEET_LOCATION + "energyTrail.png");

}
