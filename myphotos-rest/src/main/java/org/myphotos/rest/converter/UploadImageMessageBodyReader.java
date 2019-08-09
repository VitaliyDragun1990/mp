package org.myphotos.rest.converter;

import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static org.apache.commons.fileupload.disk.DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD;
import static org.myphotos.config.Constants.MAX_UPLOADED_PHOTO_SIZE_IN_BYTES;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.myphotos.infra.exception.base.ApplicationException;
import org.myphotos.infra.exception.business.ValidationException;
import org.myphotos.rest.model.UploadImageREST;

/**
 * Converter that converts Multipart form data attachment from request into
 * {@link UploadImageREST} instance.
 * 
 * @author Vitaliy Dragun
 *
 */
@Provider
@ApplicationScoped
@Consumes(MULTIPART_FORM_DATA)
public class UploadImageMessageBodyReader implements MessageBodyReader<UploadImageREST> {
	private File tempDirectory;

	@PostConstruct
	private void createTempDir() {
		try {
			tempDirectory = Files.createTempDirectory("upload").toFile();
		} catch (IOException e) {
			throw new ApplicationException("Can't create temporary directory", e);
		}
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return UploadImageREST.class.isAssignableFrom(type);
	}

	@Override
	public UploadImageREST readFrom(Class<UploadImageREST> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		try {
			return readUploadedImageFromRequest(mediaType, httpHeaders, entityStream);
		} catch (ValidationException e) {
			throw e;
		} catch (Exception e) {
			throw new ApplicationException("Can't parse multipart request: " + e.getMessage(), e);
		}
	}

	private UploadImageREST readUploadedImageFromRequest(MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws FileUploadException {
		ServletFileUpload upload = buildFileUpload();
		List<FileItem> items = upload
				.parseRequest(new JAXRSRequestContext(httpHeaders, entityStream, mediaType.toString()));
		for (FileItem fileItem : items) {
			if (!fileItem.isFormField()) {
				return new UploadImageREST(fileItem);
			}
		}
		throw new ValidationException("Missing content");
	}

	private ServletFileUpload buildFileUpload() {
		DiskFileItemFactory factory = new DiskFileItemFactory(DEFAULT_SIZE_THRESHOLD, tempDirectory);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_UPLOADED_PHOTO_SIZE_IN_BYTES);
		return upload;
	}

}
