package org.myphotos.domain.service;

import java.util.List;

import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.domain.model.Image;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.model.SortMode;
import org.myphotos.domain.model.ImageResource;
import org.myphotos.infra.exception.business.ObjectNotFoundException;

/**
 * Contains business operations related to {@link Photo} business entity.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface PhotoService {

	/**
	 * Returns profile photos for profile with specified {@code id} according to
	 * provided {@code pageable} object
	 * 
	 * @param profileId id of the {@link Profile} to find photos for
	 * @param pageable  object containing pagination information
	 * @return list with profile photos or empty list if no photos found
	 */
	List<Photo> findProfilePhotos(Long profileId, Pageable pageable);

	/**
	 * Returns popular photos sorted according to specified {@code sortMode}
	 * 
	 * @param sortMode specific sorting mode
	 * @param pageable object containing pagination information
	 * @return list with found photos or empty list if no photos found
	 */
	List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable);

	/**
	 * Returns total photos count
	 */
	long countAllPhotos();

	/**
	 * Returns link to large version of photo and increments this photo's total view
	 * count.
	 * 
	 * @param photoId id of the photo
	 * @return link to the large version of some photo
	 * @throws ObjectNotFoundException if no photo exists with provided {@code id}
	 */
	String viewLargePhoto(Long photoId) throws ObjectNotFoundException;

	/**
	 * Returns original version of the photo stored in the application and increment
	 * total download counter for such photo
	 * 
	 * @param photoId id of the photo to get
	 * @return {@link Image} representing original photo stored in the
	 *         application
	 * @throws ObjectNotFoundException if photo with specified {@code id} can not be
	 *                                 found
	 */
	Image downloadOriginalPhoto(Long photoId) throws ObjectNotFoundException;

	/**
	 * Uploads new photo for given {@code profile}
	 * 
	 * @param profile       {@link Profile} which uploads new photo
	 * @param imageResource uploaded photo resource
	 * @param callback      asynch callback that should be called when uploading
	 *                      operation completes
	 */
	void uploadNewPhoto(Profile profile, ImageResource imageResource, AsyncOperation<Photo> callback);
}
