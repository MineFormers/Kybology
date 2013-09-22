package de.mineformers.timetravel.client.gui.widget;

import cpw.mods.fml.client.FMLClientHandler;
import de.mineformers.timetravel.core.util.TextHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

/**
 * TimeTravel
 * 
 * Widget
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public abstract class Widget {

	protected int x, y;
	protected int drawX, drawY;
	protected Minecraft mc;
	protected ResourceLocation texture;
	private int zLevel;

	public Widget(ResourceLocation texture, int x, int y) {
		this.mc = FMLClientHandler.instance().getClient();
		this.texture = texture;
		this.zLevel = 0;
		this.x = x;
		this.y = y;
	}

	public void drawSplitString(String text, int x, int y, int color,
	        boolean drawShadow) {
		String[] splits = text.split("<br>");
		for (int i = 0; i < splits.length; i++) {
			this.mc.fontRenderer.drawString(splits[i], x, y + i * 10, color,
			        drawShadow);
		}
	}

	public void drawSplitStringCentered(String text, int x, int y, int color,
	        boolean drawShadow, int canvasWidth) {
		String[] splits = text.split("<br>");
		int longest = mc.fontRenderer.getStringWidth(TextHelper
		        .getLongestString(splits));
		for (int i = 0; i < splits.length; i++) {
			this.mc.fontRenderer.drawString(
			        splits[i],
			        x
			                + ((canvasWidth - longest) / 2)
			                + ((longest - mc.fontRenderer
			                        .getStringWidth(splits[i])) / 2), y + i
			                * 10, color, drawShadow);
		}
	}

	public void drawRectangle(int x, int y, int u, int v, int width, int height) {
		drawRectangle(texture, x, y, u, v, width, height);
	}

	public void drawRectangle(ResourceLocation texture, int x, int y, int u,
	        int v, int width, int height) {
		this.mc.getTextureManager().bindTexture(texture);
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + height),
		        (double) this.zLevel, (double) ((float) (u + 0) * f),
		        (double) ((float) (v + height) * f1));
		tessellator.addVertexWithUV((double) (x + width),
		        (double) (y + height), (double) this.zLevel,
		        (double) ((float) (u + width) * f),
		        (double) ((float) (v + height) * f1));
		tessellator.addVertexWithUV((double) (x + width), (double) (y + 0),
		        (double) this.zLevel, (double) ((float) (u + width) * f),
		        (double) ((float) (v + 0) * f1));
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0),
		        (double) this.zLevel, (double) ((float) (u + 0) * f),
		        (double) ((float) (v + 0) * f1));
		tessellator.draw();
	}

	public void drawRectangleStretched(int x, int y, int u, int v, int width,
	        int height, int uOff, int vOff) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + height),
		        (double) this.zLevel, (double) ((float) (u + 0) * f),
		        (double) ((float) (v + vOff) * f1));
		tessellator.addVertexWithUV((double) (x + width),
		        (double) (y + height), (double) this.zLevel,
		        (double) ((float) (u + uOff) * f),
		        (double) ((float) (v + vOff) * f1));
		tessellator.addVertexWithUV((double) (x + width), (double) (y + 0),
		        (double) this.zLevel, (double) ((float) (u + uOff) * f),
		        (double) ((float) (v + 0) * f1));
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0),
		        (double) this.zLevel, (double) ((float) (u + 0) * f),
		        (double) ((float) (v + 0) * f1));
		tessellator.draw();
	}

	public abstract void draw(int mouseX, int mouseY);

	public void setZIndex(int zIndex) {
		this.zLevel = zIndex;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setDrawPos(int x, int y) {
		this.drawX = x;
		this.drawY = y;
	}

}
