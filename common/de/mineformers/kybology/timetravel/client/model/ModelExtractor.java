package de.mineformers.kybology.timetravel.client.model;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.kybology.timetravel.lib.Models;

/**
 * Kybology
 *
 * ModelExtractor
 *
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 *
 */
@SideOnly(Side.CLIENT)
public class ModelExtractor {

    private WavefrontObject modelExtractor;
    
    public ModelExtractor() {
        modelExtractor = (WavefrontObject) AdvancedModelLoader
                .loadModel(Models.EXTRACTOR);
    }
    
    public void render() {
        modelExtractor.renderAll();
    }
    
    public void renderExtractor() {
        modelExtractor.renderPart("Extractor");
    }
    
}
