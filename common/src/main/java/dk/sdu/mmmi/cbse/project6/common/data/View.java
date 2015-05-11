package dk.sdu.mmmi.cbse.project6.common.data;

import java.io.Serializable;

public final class View implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imageFilePath;

	public View(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

}
