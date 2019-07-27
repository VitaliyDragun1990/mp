package org.myphotos.media.resizer;

import java.nio.file.Path;

import javax.enterprise.inject.Vetoed;

import org.myphotos.media.model.ImageCategory;

@Vetoed
public interface ImageResizer {

	/**
	 * Resizes image using specified parameters
	 * 
	 * @param sourcePath path to the image to resize
	 * @param destinationPath path where resized image should be saved
	 * @param imageCategory category of the image o resize
	 */
	void resize(Path sourcePath, Path destinationPath, ImageCategory imageCategory);
}
