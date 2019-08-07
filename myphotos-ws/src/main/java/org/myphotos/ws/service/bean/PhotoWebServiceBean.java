package org.myphotos.ws.service.bean;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import org.myphotos.converter.AbsoluteUrlConverter;
import org.myphotos.converter.ModelConverter;
import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.model.Image;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.model.SortMode;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.ws.error.ExceptionMapperInterceptor;
import org.myphotos.ws.model.ImageLinkSOAP;
import org.myphotos.ws.model.PhotoSOAP;
import org.myphotos.ws.model.PhotosSOAP;
import org.myphotos.ws.service.PhotoWebService;

@MTOM
@Singleton
@ConcurrencyManagement(BEAN)
@WebService(
		portName = "PhotoServicePort",
		serviceName = "PhotoService",
		targetNamespace = "http://soap.myphotos.com/ws/PhotoService?wsdl",
		endpointInterface = "org.myphotos.ws.service.PhotoWebService"
		)
@Interceptors(ExceptionMapperInterceptor.class)
public class PhotoWebServiceBean implements PhotoWebService {
	
	@EJB
	private PhotoService photoService;
	
	@Inject
	private ModelConverter modelConverter;
	
	@Inject
	private AbsoluteUrlConverter urlConverter;

	@Override
	public PhotosSOAP findAllOrderByPhotoPopularity(int page, int limit, boolean withTotal) {
		return findAll(SortMode.POPULAR_PHOTO, page, limit, withTotal);
	}

	@Override
	public PhotosSOAP findAllOrderBAuthorPopularity(int page, int limit, boolean withTotal) {
		return findAll(SortMode.POPULAR_AUTHOR, page, limit, withTotal);
	}

	private PhotosSOAP findAll(SortMode sortMode, int page, int limit, boolean withTotal) {
		PhotosSOAP result = new PhotosSOAP();
		
		List<Photo> photos = photoService.findPopularPhotos(sortMode, Pageable.of(page, limit));
		result.setPhotos(modelConverter.convertList(photos, PhotoSOAP.class));
		
		if (withTotal) {
			result.setTotal(photoService.countAllPhotos());
		}
		
		return result;
	}

	@Override
	public ImageLinkSOAP viewLargePhoto(Long photoId) {
		String relativeUrl = photoService.viewLargePhoto(photoId);
		String absoluteUrl = urlConverter.convert(relativeUrl);
		return new ImageLinkSOAP(absoluteUrl);
	}

	@Override
	public DataHandler downloadOriginalImage(Long photoId) {
		Image originalImage = photoService.downloadOriginalPhoto(photoId);
		return new DataHandler(new OriginalImageDataSource(originalImage));
	}

	void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	void setModelConverter(ModelConverter modelConverter) {
		this.modelConverter = modelConverter;
	}

	void setUrlConverter(AbsoluteUrlConverter urlConverter) {
		this.urlConverter = urlConverter;
	}
	
	private static class OriginalImageDataSource implements DataSource {
		private final Image originalImage;

		public OriginalImageDataSource(Image originalImage) {
			this.originalImage = originalImage;
		}

		@Override
		public String getContentType() {
			return "image/jpeg";
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return originalImage.getInput();
		}

		@Override
		public String getName() {
			return originalImage.getName();
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			throw new UnsupportedOperationException("OutputStream is not supported");
		}
		
	}
	
}
