package org.myphotos.domain.service;

import java.util.Optional;

import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.AsyncOperation;
import org.myphotos.domain.model.ImageResource;
import org.myphotos.infra.exception.business.ObjectNotFoundException;

/**
 * Contains business operation related to {@link Profile} business entity.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ProfileService {

	/**
	 * Finds and returns {@link Profile} entity with specified {@code id}
	 * 
	 * @param id id of the profile to find
	 * @return {@link Profile} with provided {@code id}
	 * @throws ObjectNotFoundException if no profile was found with provided
	 *                                 {@code id}
	 */
	Profile findById(Long id) throws ObjectNotFoundException;

	/**
	 * Finds and returns {@link Profile} entity with specified {@code uid}
	 * 
	 * @param uid uid of the profile to find
	 * @return {@link Profile} with provided {@code uid}
	 * @throws ObjectNotFoundException if no profile was found with provided
	 *                                 {@code uid}
	 */
	Profile findByUid(String uid) throws ObjectNotFoundException;

	/**
	 * Finds {@link Profile} with provided {@code email}
	 * 
	 * @param email email of the profile to find
	 * @return {@link Optional} with {@link Profile} if one was found, or empty
	 *         {@link Optional} otherwise
	 */
	Optional<Profile> findByEmail(String email);
	
	/**
	 * Creates new profile
	 * @param profile data holder for new profile to create
	 * @param uploadProfileAvatar
	 */
	void create(Profile profile, boolean uploadProfileAvatar);

	/**
	 * Updates profided {@link Profile}
	 * 
	 * @param profile {@link Profile} to update
	 */
	void update(Profile profile);

	/**
	 * Uploads new avatar image for provided profile
	 * 
	 * @param profile       {@link Profile} to upload new avatar image for
	 * @param imageResource uploaded avatar image
	 * @param callback      asynch callback that should be called when uploading
	 *                      operation completes
	 */
	void uploadNewAvatar(Profile profile, ImageResource imageResource, AsyncOperation<Profile> callback);
}
