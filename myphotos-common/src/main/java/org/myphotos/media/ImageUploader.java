package org.myphotos.media;

import javax.enterprise.inject.Vetoed;

import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.model.ImageResource;

/**
 * Responsible for uploading image resources
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
public interface ImageUploader {

	/**
	 * Uploads new photo image using provided {@code uploadedResource}
	 * 
	 * @param uploadedResource image resource to upload
	 * @return {@link Photo} object that represents newly uploaded photo image
	 */
	Photo uploadPhoto(ImageResource uploadedResource);

	/**
	 * Uploads new avatar image using provided {@code uploadedResource}
	 * 
	 * @param uploadedResource image resource to upload
	 * @return link to newly uploaded avatar image
	 */
	String uploadAvatar(ImageResource uploadedResource);
}
