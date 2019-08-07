package org.myphotos.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.myphotos.rest.Constants.PHOTO_LIMIT;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.myphotos.converter.ModelConverter;
import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.rest.model.PhotosREST;
import org.myphotos.rest.model.ProfilePhotoREST;
import org.myphotos.rest.model.ProfileWithPhotosREST;

@Path("/profile")
@Produces({APPLICATION_JSON})
@RequestScoped
public class PublicProfileController {

	@EJB
	private ProfileService profileService;
	
	@EJB
	private PhotoService photoService;
	
	@Inject
	private ModelConverter converter;
	
	@GET
	@Path("/{id}")
	public ProfileWithPhotosREST findProfile(
			@PathParam("id") Long id,
			@DefaultValue("false") @QueryParam("withPhotos") boolean withPhotos,
			@DefaultValue(PHOTO_LIMIT) @QueryParam("limit") int limit) {
		
		Profile p = profileService.findById(id);
		ProfileWithPhotosREST profile = converter.convert(p, ProfileWithPhotosREST.class);
		
		if (withPhotos) {
			profile.setPhotos(findPhotos(profile.getId(), 1, limit));
		}
		
		return profile;
	}
	
	@GET
	@Path("/{profileId}/photos")
	public PhotosREST findProfilePhotos(
			@PathParam("profileId") Long profileId,
			@DefaultValue("1") @QueryParam("page") int page,
			@DefaultValue(PHOTO_LIMIT) @QueryParam("limit") int limit) {
		
		return new PhotosREST(findPhotos(profileId, page, limit));
	}

	private List<ProfilePhotoREST> findPhotos(Long profileId, int page, int limit) {
		List<Photo> photos = photoService.findProfilePhotos(profileId, Pageable.of(page, limit));
		return  converter.convertList(photos, ProfilePhotoREST.class);
	}

	void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	void setConverter(ModelConverter converter) {
		this.converter = converter;
	}
	
}
