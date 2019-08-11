package org.myphotos.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.myphotos.rest.Constants.PHOTO_LIMIT;
import static org.myphotos.rest.StatusMessages.INTERRNAL_ERROR;
import static org.myphotos.rest.StatusMessages.SERVICE_UNAVAILABLE;

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
import org.myphotos.rest.model.ErrorMessageREST;
import org.myphotos.rest.model.PhotosREST;
import org.myphotos.rest.model.ProfilePhotoREST;
import org.myphotos.rest.model.ProfileWithPhotosREST;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("profile")
@Path("/profile")
@Produces({APPLICATION_JSON})
@RequestScoped
@ApiResponses({
	@ApiResponse(code = 500, message = INTERRNAL_ERROR, response = ErrorMessageREST.class),
	@ApiResponse(code = 502, message = SERVICE_UNAVAILABLE),
	@ApiResponse(code = 503, message = SERVICE_UNAVAILABLE),
	@ApiResponse(code = 504, message = SERVICE_UNAVAILABLE)
})
public class PublicProfileController {

	@EJB
	private ProfileService profileService;
	
	@EJB
	private PhotoService photoService;
	
	@Inject
	private ModelConverter converter;
	
	@GET
	@Path("/{id}")
	@ApiOperation(value = "Finds profile by id",
		notes = "withPhotos and limit are optional."
				+ " If withPhotos=true, this mehtod returns profile with all its photos limited by limit parameter")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Profile with provided id not found", response = ErrorMessageREST.class)
	})
	public ProfileWithPhotosREST findProfile(
			@ApiParam(value = "Profile unique numeric id", required = true)
			@PathParam("id") Long id,
			@ApiParam(value = "Flag, which tells if profile photos should be added to the result")
			@DefaultValue("false") @QueryParam("withPhotos") boolean withPhotos,
			@ApiParam(value = "Photos limit")
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
	@ApiOperation(value = "Finds profile photos",
		notes = "If no profile is found by provided 'profileId', then empty list will be returned with status code 200")
	public PhotosREST findProfilePhotos(
			@ApiParam(value = "Profile number id", required = true)
			@PathParam("profileId") Long profileId,
			@ApiParam(value = "Pagination page number (should start with 1)")
			@DefaultValue("1") @QueryParam("page") int page,
			@ApiParam(value = "Max number of photos to return")
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
