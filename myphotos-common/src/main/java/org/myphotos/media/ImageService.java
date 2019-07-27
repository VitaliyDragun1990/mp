package org.myphotos.media;

import javax.enterprise.inject.Vetoed;

import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.model.Image;
import org.myphotos.domain.model.ImageResource;

@Vetoed
public interface ImageService {

	Image downloadImage(String imageUrl);
	
	Photo uploadPhoto(ImageResource uploadedResource);

	String uploadAvatar(ImageResource uploadedResource);
	
	void removeImage(String imageUrl);
	
}
