package org.myphotos.media;

import javax.enterprise.inject.Vetoed;

import org.myphotos.domain.model.Image;
import org.myphotos.domain.model.ImageResource;

@Vetoed
public interface ImageService {

	Image downloadImage(String imageUrl);
	
	void uploadPhoto(ImageResource uploadedResource);

	void uploadAvatar(ImageResource uploadedResource);
	
}
