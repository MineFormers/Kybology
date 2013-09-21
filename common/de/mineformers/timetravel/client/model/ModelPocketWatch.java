package de.mineformers.timetravel.client.model;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import cpw.mods.fml.relauncher.SideOnly;

import cpw.mods.fml.relauncher.Side;
import de.mineformers.timetravel.lib.Models;

/**
 * TimeTravel
 * 
 * ModelPocketWatch
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@SideOnly(Side.CLIENT)
public class ModelPocketWatch {

	private IModelCustom modelPocketWatch;

	public ModelPocketWatch() {
		modelPocketWatch = AdvancedModelLoader.loadModel(Models.WATCH);
	}

	public void render() {
		modelPocketWatch.renderAll();
	}
	
	public void renderBut(String part) {
		modelPocketWatch.renderAllExcept(part);
	}
	
	public void renderMain() {
		modelPocketWatch.renderOnly("Outer", "Back");
	}
	
	public void renderPane() {
		modelPocketWatch.renderPart("Pane");
	}
	
	public void renderHours() {
		modelPocketWatch.renderPart("Hours");
	}
	
	public void renderMinutes() {
		modelPocketWatch.renderPart("Minutes");
	}

}
