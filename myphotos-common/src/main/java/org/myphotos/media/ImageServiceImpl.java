package org.myphotos.media;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.model.Image;
import org.myphotos.domain.model.ImageResource;
import org.myphotos.infra.cdi.interceptor.ImagerResourceInterceptor;
import org.myphotos.media.model.ImageCategory;
import org.myphotos.media.model.TempImageResource;
import org.myphotos.media.resizer.ImageResizer;
import org.myphotos.media.storage.ImageStorage;

@ApplicationScoped
class ImageServiceImpl implements ImageService {

	private ImageResizer imageResizer;
	private ImageStorage imageStorage;
	
	@Override
	public Image downloadImage(String imageUrl) {
		return imageStorage.getOriginalImage(imageUrl);
	}

	@Override
	@Interceptors({ImagerResourceInterceptor.class})
	public Photo uploadPhoto(ImageResource uploadedResource) {
		Photo photo = new Photo();
		photo.setLargeUrl(createdResizedImage(uploadedResource, ImageCategory.LARGE_PHOTO));
		photo.setSmallUrl(createdResizedImage(uploadedResource, ImageCategory.SMALL_PHOTO));
		photo.setOriginalUrl(imageStorage.saveOriginalImage(uploadedResource.getPath()));
		
		return photo;
	}

	@Override
	@Interceptors({ImagerResourceInterceptor.class})
	public String uploadAvatar(ImageResource uploadedResource) {
		return createdResizedImage(uploadedResource, ImageCategory.PROFILE_AVATAR);
	}
	
	@Override
	public void removeImage(String imageUrl) {
		imageStorage.deletePublicImage(imageUrl);
	}
	
	private String createdResizedImage(ImageResource imageResource, ImageCategory imageCategory) {
		try (TempImageResource tempResource = new TempImageResource()) {
			imageResizer.resize(imageResource.getPath(), tempResource.getPath(), imageCategory);
			return imageStorage.savePublicImage(tempResource.getPath(), imageCategory);
		}
	}

	@Inject
	void setImageResizer(ImageResizer imageResizer) {
		this.imageResizer = imageResizer;
	}
	
	@Inject
	void setImageStorage(ImageStorage imageStorage) {
		this.imageStorage = imageStorage;
	}

}
