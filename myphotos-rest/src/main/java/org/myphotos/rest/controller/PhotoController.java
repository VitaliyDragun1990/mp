package org.myphotos.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
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
import javax.ws.rs.core.Response;

import org.myphotos.converter.AbsoluteUrlConverter;
import org.myphotos.converter.ModelConverter;
import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.model.Image;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.model.SortMode;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.rest.model.ErrorMessageREST;
import org.myphotos.rest.model.ImageLinkREST;
import org.myphotos.rest.model.PhotoREST;
import org.myphotos.rest.model.PhotosREST;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("photo")
@Path("/photo")
@Produces({APPLICATION_JSON})
@RequestScoped
@ApiResponses({
	@ApiResponse(code = 500, message = INTERRNAL_ERROR, response = ErrorMessageREST.class),
	@ApiResponse(code = 502, message = SERVICE_UNAVAILABLE),
	@ApiResponse(code = 503, message = SERVICE_UNAVAILABLE),
	@ApiResponse(code = 504, message = SERVICE_UNAVAILABLE)
})
public class PhotoController {

	@EJB
	private PhotoService photoService;
	
	@Inject
	private AbsoluteUrlConverter urlConverter;
	
	@Inject
	private ModelConverter modelConverter;
	
	@GET
	@Path("/all")
	@ApiOperation(value = "Finds popular photos according to provided sortMode",
			notes = "If total photo count is required - set withTotal=true")
	public PhotosREST findAllPhotos(
			@ApiParam(value = "Flag, which indicates whether total photo count should be provided or not")
			@DefaultValue("false") @QueryParam("withTotal") boolean withTotal,
			@ApiParam(value = "Sort mode to sort photos with", allowableValues = "popular_photo,popular_author")
			@DefaultValue("popular_photo") @QueryParam("sortMode") String sortMode,
			@ApiParam(value = "Pagination page number (should start with 1)")
			@DefaultValue("1") @QueryParam("page") int page,
			@ApiParam(value = "Max number of photos to return")
			@DefaultValue(PHOTO_LIMIT) @QueryParam("limit") int limit) {
		PhotosREST result = new PhotosREST();
		
		List<Photo> photos = photoService.findPopularPhotos(SortMode.of(sortMode), Pageable.of(page, limit));
		result.setPhotos(modelConverter.convertList(photos, PhotoREST.class));
		if (withTotal) {
			result.setTotal(photoService.countAllPhotos());
		}
		
		return result;
	}
	
	@GET
	@Path("/preview/{id}")
	@ApiOperation(value = "Gets large photo's url by id",
		notes = "FYI: This method increments photo view count by 1")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Can't find photo with provided id", response = ErrorMessageREST.class)
	})
	public ImageLinkREST viewLargePhoto(
			@ApiParam(value = "Unique identifier of the photo", required = true)
			@PathParam("id") Long photoId) {
		String relativeUrl = photoService.viewLargePhoto(photoId);
		String absoluteUrl = urlConverter.convert(relativeUrl);
		
		return new ImageLinkREST(absoluteUrl);
	}
	
	@GET
	@Path("/download/{id}")
	@Produces(APPLICATION_OCTET_STREAM)
	@ApiOperation(value = "Downloads original photo by its id",
		notes = "FYI: This method increments photo download count by 1")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Can't find photo with provided id", response = ErrorMessageREST.class)
	})
	public Response downloadOriginalImage(
			@ApiParam(value = "Unique identifier of the photo", required = true)
			@PathParam("id") Long photoId) {
		Image originalPhoto = photoService.downloadOriginalPhoto(photoId);
		return buildResponse(originalPhoto);
	}

	private Response buildResponse(Image originalPhoto) {
		return Response
				.ok(originalPhoto.getInput(), APPLICATION_OCTET_STREAM)
				.header("Content-Disposition", "attachment; filename=" + originalPhoto.getName())
				.build();
	}
}
