package de.mineformers.timetravel.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.mineformers.timetravel.lib.Models;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

/**
 * TimeTravel
 * 
 * ModelCrystalOre
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@SideOnly(Side.CLIENT)
public class ModelCrystals {

	private WavefrontObject modelCrystals;

	public ModelCrystals() {
		modelCrystals = (WavefrontObject) AdvancedModelLoader
				.loadModel(Models.CRYSTALS);
	}

	public void render() {
		modelCrystals.renderAll();
	}
	
	public void renderOre() {
		modelCrystals.renderOnly("Ore");
	}
	
	public void renderTransferCrystal() {
		modelCrystals.renderOnly("Transfer");
	}
	
	public void renderStorageCrystal() {
        modelCrystals.renderOnly("Storage");
    }
	
	public void renderUndefinedCrystal() {
        modelCrystals.renderOnly("Undefined");
    }


}
