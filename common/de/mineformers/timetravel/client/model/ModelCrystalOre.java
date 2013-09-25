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
public class ModelCrystalOre {

	private WavefrontObject modelCrystalOre;

	public ModelCrystalOre() {
		modelCrystalOre = (WavefrontObject) AdvancedModelLoader
				.loadModel(Models.CRYSTAL_ORE);
	}

	public void render() {
		modelCrystalOre.renderAll();
	}

}
