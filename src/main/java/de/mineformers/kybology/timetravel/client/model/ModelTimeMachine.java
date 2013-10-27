package de.mineformers.kybology.timetravel.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * Kybology
 * 
 * ModelTimeMachine
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ModelTimeMachine extends ModelBase {
	// fields
	ModelRenderer Base;
	ModelRenderer Stair;
	ModelRenderer Arm;
	ModelRenderer Panel;
	ModelRenderer Cube;
	ModelRenderer Display;
	ModelRenderer Module;

	private int mod;

	public ModelTimeMachine() {
		mod = 1;
		textureWidth = 256;
		textureHeight = 64;
		setTextureOffset("Base.Pillar", 0, 22);
		setTextureOffset("Base.Main", 0, 0);
		setTextureOffset("Stair.Back", 70, 0);
		setTextureOffset("Stair.Front", 70, 22);
		setTextureOffset("Arm.Top", 140, 12);
		setTextureOffset("Arm.Pillar", 176, 0);
		setTextureOffset("Arm.Base", 140, 0);
		setTextureOffset("Arm.Corner1", 122, 10);
		setTextureOffset("Arm.Corner2", 122, 16);
		setTextureOffset("Arm.Corner3", 122, 0);
		setTextureOffset("Cable1.Part1", 104, 8);
		setTextureOffset("Cable1.Part3", 48, 0);
		setTextureOffset("Cable1.Part4", 48, 0);
		setTextureOffset("Cable1.Part5", 48, 0);
		setTextureOffset("Cable1.Part6", 112, 0);
		setTextureOffset("Cable2.Part1", 104, 3);
		setTextureOffset("Cable2.Part2", 48, 0);
		setTextureOffset("Cable2.Part3", 48, 0);
		setTextureOffset("Cable2.Part4", 48, 0);
		setTextureOffset("Cable2.Part5", 48, 0);
		setTextureOffset("Cable2.Part6", 104, 0);
		setTextureOffset("Cube.Part1", 0, 0);
		setTextureOffset("Cube.Part2", 0, 0);
		setTextureOffset("Cube.Part3", 0, 0);
		setTextureOffset("Cube.Part4", 0, 0);

		setTextureOffset("Panel.Pillar", 176, 5);
		setTextureOffset("Panel.Base", 140, 24);
		setTextureOffset("Panel.Corner1", 122, 10);
		setTextureOffset("Panel.Corner2", 122, 16);
		setTextureOffset("Panel.Corner3", 122, 0);
		setTextureOffset("Cable3.Part1", 48, 3);
		setTextureOffset("Cable3.Part2", 48, 3);
		setTextureOffset("Cable3.Part3", 48, 3);
		setTextureOffset("Cable3.Part4", 48, 3);
		setTextureOffset("Cable3.Part5", 48, 3);
		setTextureOffset("Cable4.Part1", 48, 3);
		setTextureOffset("Cable4.Part2", 48, 3);
		setTextureOffset("Cable4.Part3", 48, 3);
		setTextureOffset("Cable4.Part1", 48, 3);
		setTextureOffset("Cable4.Part1", 48, 3);

		Base = new ModelRenderer(this, "Base");
		Base.setRotationPoint(0F, 21F, 0F);
		setRotation(Base, 0F, 0F, 0F);
		Base.mirror = true;
		Base.addBox("Pillar", -6F, -5F, -6F, 12, 2, 12);
		Base.addBox("Main", -8F, -3F, -8F, 16, 6, 16);
		Stair = new ModelRenderer(this, "Stair");
		Stair.setRotationPoint(12F, 21F, 0F);
		setRotation(Stair, 0F, 0F, 0F);
		Stair.mirror = false;
		Stair.addBox("Back", -4F, -3F, -8F, 9, 6, 16);
		Stair.addBox("Front", 5F, -1F, -7F, 6, 4, 14);
		Arm = new ModelRenderer(this, "Arm");
		Arm.setRotationPoint(16F, 24F, -16F);
		setRotation(Arm, 0F, 0F, 0F);
		Arm.mirror = false;
		Arm.addBox("Top", -2F, -32F, -7F, 9, 3, 9);
		Arm.addBox("Pillar", 0F, -29F, -5F, 5, 26, 5);
		Arm.addBox("Base", -2F, -3F, -7F, 9, 3, 9);
		Arm.addBox("Corner1", -8F, -3F, 1F, 4, 3, 3);
		Arm.addBox("Corner2", -4F, -3F, 4F, 3, 3, 4);
		Arm.addBox("Corner3", -8F, -6F, 4F, 4, 6, 4);
		ModelRenderer Cable1 = new ModelRenderer(this, "Cable1");
		Cable1.setRotationPoint(3F, -1F, 3F);
		setRotation(Cable1, 0F, 0F, 0F);
		Cable1.mirror = true;
		Cable1.addBox("Part1", -4F, -1F, 2F, 1, 2, 3);
		Cable1.addBox("Part3", -1F, 0F, 0F, 1, 1, 2);
		Cable1.addBox("Part4", -2F, 0F, 2F, 1, 1, 1);
		Cable1.addBox("Part5", -3F, 0F, 3F, 1, 1, 1);
		Cable1.addBox("Part6", -2F, -1F, -1F, 3, 2, 1);
		Arm.addChild(Cable1);
		ModelRenderer Cable2 = new ModelRenderer(this, "Cable2");
		Cable2.setRotationPoint(-3F, -1F, -3F);
		setRotation(Cable2, 0F, 0F, 0F);
		Cable2.mirror = true;
		Cable2.addBox("Part1", 0F, -1F, -1F, 1, 2, 3);
		Cable2.addBox("Part2", -1F, 0F, 0F, 1, 1, 1);
		Cable2.addBox("Part3", -2F, 0F, -1F, 1, 1, 1);
		Cable2.addBox("Part4", -3F, 0F, 0F, 1, 1, 2);
		Cable2.addBox("Part5", -4F, 0F, 2F, 1, 1, 1);
		Cable2.addBox("Part6", -5F, -1F, 3F, 3, 2, 1);
		Arm.addChild(Cable2);
		Cube = new ModelRenderer(this, "Cube");
		Cube.setRotationPoint(-1F, -14F, -10F);
		setRotation(Cube, 0F, 0F, 0F);
		Cube.mirror = true;
		Cube.addBox("Part1", 0F, 0F, 11F, 7, 5, 0);
		Cube.addBox("Part2", 0F, 0F, 4F, 7, 5, 0);
		Cube.addBox("Part3", 0F, 0F, 4F, 0, 5, 7);
		Cube.addBox("Part4", 7F, 0F, 4F, 0, 5, 7);
		Arm.addChild(Cube);

		Panel = new ModelRenderer(this, "Panel");
		Panel.setRotationPoint(16F, 24F, -16F);
		setRotation(Panel, 0F, 0F, 0F);
		Panel.addBox("Pillar", 1F, -12F, -3F, 2, 10, 2);
		Panel.addBox("Base", -2F, -2F, -6F, 8, 2, 8);
		Panel.addBox("Corner1", -8F, -3F, 1F, 4, 3, 3);
		Panel.addBox("Corner2", -4F, -3F, 4F, 3, 3, 4);
		Panel.addBox("Corner3", -8F, -6F, 4F, 4, 6, 4);
		ModelRenderer Cable3 = new ModelRenderer(this, "Cable3");
		Cable3.setRotationPoint(3F, -1F, 3F);
		setRotation(Cable3, 0F, 0F, 0F);
		Cable3.mirror = true;
		Cable3.addBox("Part1", -4F, 0F, 3F, 1, 1, 1);
		Cable3.addBox("Part2", -3F, 0F, 2F, 2, 1, 1);
		Cable3.addBox("Part3", -1F, 0F, 1F, 1, 1, 1);
		Cable3.addBox("Part4", 0F, 0F, 0F, 1, 1, 1);
		Cable3.addBox("Part5", -1F, 0F, -1F, 1, 1, 1);
		Panel.addChild(Cable3);
		ModelRenderer Cable4 = new ModelRenderer(this, "Cable4");
		Cable4.setRotationPoint(-3F, -1F, -3F);
		setRotation(Cable4, 0F, 0F, 0F);
		Cable4.mirror = true;
		Cable4.addBox("Part1", -2F, 0F, 0F, 3, 1, 1);
		Cable4.addBox("Part2", -3F, 0F, 1F, 1, 1, 1);
		Cable4.addBox("Part3", -4F, 0F, 2F, 1, 1, 2);
		Panel.addChild(Cable4);

		Display = new ModelRenderer(this, 196, 0);
		Display.addBox(-3F, -0.7F, -4.5F, 6, 1, 9);
		Display.setRotationPoint(18F, 11F, -18F);
		Display.setTextureSize(256, 64);
		Display.mirror = true;
		setRotation(Display, 0.0F, 0, 0);
		
		Module = new ModelRenderer(this, 0, 0);
		Module.setTextureSize(128, 64);
		Module.setRotationPoint(16F, 24F, -16F);
		setRotation(Module, 0, 0, 0);
		Module.addBox(-4F, -10F, -14F, 18, 18, 18);
	}

	public void renderBase() {
		Base.render(0.0625F);
	}

	public void renderStairs() {
		Stair.render(0.0625F);
	}

	public void renderCorner() {
		if (Cube.offsetY <= -0.9F)
			mod = 1;
		else if (Cube.offsetY >= 0.35F)
			mod = -1;
		Cube.offsetY += mod * 0.001;
		Arm.render(0.0625F);
	}

	public void renderPanelStand() {
		Panel.render(0.0625F);
	}

	public void renderPanelDisplay() {
		Display.render(0.0625F);
	}
	
	public void renderModule() {
		Module.render(0.0625F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
