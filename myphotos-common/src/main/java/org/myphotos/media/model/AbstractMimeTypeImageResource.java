package org.myphotos.media.model;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.myphotos.domain.model.ImageResource;
import org.myphotos.infra.exception.base.ApplicationException;
import org.myphotos.infra.exception.business.ValidationException;

public abstract class AbstractMimeTypeImageResource implements ImageResource {
	
	private Path tempPath;
	
	protected abstract String getContentType();
	
	protected String getExtension() {
		String contentType = getContentType();
		if ("image/jpeg".equalsIgnoreCase(contentType)) {
			return "jpeg";
		} else if ("image/png".equalsIgnoreCase(contentType)) {
			return "png";
		} else {
			throw new ValidationException("Only JPEG/JPG and PNG formats are supported. Current format is " + contentType);
		}
	}
	
	protected abstract void copyContent() throws Exception;
	
	@Override
	public Path getPath() {
		if (tempPath == null) {
			createTempFileWithImageContent();
		}
		return tempPath;
	}

	@Override
	public void close() throws Exception {
		TempFileFactory.deleteTempFile(tempPath);
		try {
			deleteTempResources();
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING, "Can't delete temporary resources from " + toString(), e);
		}
	}
	
	protected abstract void deleteTempResources() throws Exception;

	@Override
	public abstract String toString();

	private void createTempFileWithImageContent() {
		tempPath = TempFileFactory.createTempFile(getExtension());
		try {
			copyContent();
		} catch (Exception e) {
			throw new ApplicationException(
					String.format("Can't copy content from %s to temp file %s", toString(), tempPath), e);
		}
	}
}
