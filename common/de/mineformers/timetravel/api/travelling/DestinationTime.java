package de.mineformers.timetravel.api.travelling;

import net.minecraft.util.ResourceLocation;

/**
 * TimeTravel
 * 
 * DestinationTime
 * 
 * @author PaleoCrafter
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class DestinationTime {

	private int dimensionId;
	private boolean technologyAllowed;
	private ResourceLocation previewImage;

	public DestinationTime(int dimensionId, ResourceLocation previewImage) {
		this.dimensionId = dimensionId;
		this.previewImage = previewImage;
	}

	public int getDimensionId() {
		return dimensionId;
	}

	public void setDimensionId(int dimensionId) {
		this.dimensionId = dimensionId;
	}
	
    public ResourceLocation getPreviewImage() {
	    return previewImage;
    }
    
    public void setPreviewImage(ResourceLocation previewImage) {
	    this.previewImage = previewImage;
    }

	public boolean isTechnologyAllowed() {
		return technologyAllowed;
	}

	public void setTechnologyAllowed(boolean technologyAllowed) {
		this.technologyAllowed = technologyAllowed;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DestinationTime) {
			return ((DestinationTime) obj).getDimensionId() == this
			        .getDimensionId();
		} else if (obj instanceof Integer) {
			return (int) obj == this.getDimensionId();
		}

		return false;
	}

}
