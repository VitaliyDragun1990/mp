package org.myphotos.media;

import javax.enterprise.inject.Vetoed;

import org.myphotos.domain.model.Image;

@Vetoed
public interface ImageService extends ImageUploader {

	Image downloadImage(String imageUrl);
	
	void removeImage(String imageUrl);
	
}
