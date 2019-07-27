package org.myphotos.media.storage;

import java.nio.file.Path;

import javax.enterprise.inject.Vetoed;

import org.myphotos.domain.model.Image;
import org.myphotos.media.model.ImageCategory;

/**
 * Manages storage-related operations for all kind of application-specific
 * images
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
public interface ImageStorage {

	/**
	 * Saves original unprocessed image
	 * 
	 * @param path path to image to save
	 * @return unique name of the saved image
	 */
	String saveOriginalImage(Path path);
	
	/**
	 * Returns original unprocessed image
	 * 
	 * @param originalImageName unique name by which original image can be obtained
	 * @return original image
	 */
	Image getOriginalImage(String originalImageName);

	/**
	 * Saves image that has been processed in some way
	 * 
	 * @param path          path to image to save
	 * @param imageCategory category of the image
	 * @return link to saved image
	 */
	String savePublicImage(Path path, ImageCategory imageCategory);

	/**
	 * Deletes image
	 * 
	 * @param url url of the image to delete
	 */
	void deletePublicImage(String url);
}
