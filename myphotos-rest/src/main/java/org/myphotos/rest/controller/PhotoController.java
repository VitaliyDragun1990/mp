package org.myphotos.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
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
import javax.ws.rs.core.Response;

import org.myphotos.converter.AbsoluteUrlConverter;
import org.myphotos.converter.ModelConverter;
import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.model.Image;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.model.SortMode;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.rest.model.ImageLinkREST;
import org.myphotos.rest.model.PhotoREST;
import org.myphotos.rest.model.PhotosREST;

@Path("/photo")
@Produces({APPLICATION_JSON})
@RequestScoped
public class PhotoController {

	@EJB
	private PhotoService photoService;
	
	@Inject
	private AbsoluteUrlConverter urlConverter;
	
	@Inject
	private ModelConverter modelConverter;
	
	@GET
	@Path("/all")
	public PhotosREST findAllPhotos(
			@DefaultValue("false") @QueryParam("withTotal") boolean withTotal,
			@DefaultValue("popular_photo") @QueryParam("sortMode") String sortMode,
			@DefaultValue("1") @QueryParam("page") int page,
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
	public ImageLinkREST viewLargePhoto(
			@PathParam("id") Long photoId) {
		String relativeUrl = photoService.viewLargePhoto(photoId);
		String absoluteUrl = urlConverter.convert(relativeUrl);
		
		return new ImageLinkREST(absoluteUrl);
	}
	
	@GET
	@Path("/download/{id}")
	@Produces(APPLICATION_OCTET_STREAM)
	public Response downloadOriginalImage(
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
