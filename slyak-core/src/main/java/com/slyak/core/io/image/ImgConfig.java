package com.slyak.core.io.image;

import java.io.Serializable;
import java.util.List;

public class ImgConfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer maxWidth;
	
	private Integer maxHeight;
	
	private List<ImgSize> imgSizes;
	

	public Integer getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}

	public List<ImgSize> getImgSizes() {
		return imgSizes;
	}

	public void setImgSizes(List<ImgSize> imgSizes) {
		this.imgSizes = imgSizes;
	}

	public Integer getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}
}
