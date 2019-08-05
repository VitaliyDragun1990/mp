package org.myphotos.media.model;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.myphotos.domain.model.ImageResource;
import org.myphotos.infra.exception.base.ApplicationException;
import org.myphotos.infra.exception.business.ValidationException;

/**
 * Abstract parent for any kind of external image resource uploaded to the application.
 * 
 * @author Vitaliy Dragun
 *
 */
public abstract class AbstractMimeTypeImageResource implements ImageResource {

	private Path temporaryResourcePath;

	protected String getExtension() {
		String contentType = getContentType();
		if ("image/jpeg".equalsIgnoreCase(contentType)) {
			return "jpeg";
		} else if ("image/png".equalsIgnoreCase(contentType)) {
			return "png";
		} else {
			throw new ValidationException(
					"Only JPEG/JPG and PNG formats are supported. Current format is " + contentType);
		}
	}

	protected abstract String getContentType();

	@Override
	public Path getPath() {
		if (temporaryResourcePath == null) {
			createTempFileWithImageContent();
		}
		return temporaryResourcePath;
	}

	@Override
	public void close() throws Exception {
		TempFileFactory.deleteTempFile(temporaryResourcePath);
		try {
			deleteTempResources();
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.WARNING,
					"Can't delete temporary resources from " + toString(), e);
		}
	}

	protected abstract void deleteTempResources() throws Exception;

	@Override
	public abstract String toString();

	private void createTempFileWithImageContent() {
		temporaryResourcePath = TempFileFactory.createTempFile(getExtension());
		try {
			copyContent();
		} catch (Exception e) {
			throw new ApplicationException(
					String.format("Can't copy content from %s to temp file %s", toString(), temporaryResourcePath), e);
		}
	}

	protected abstract void copyContent() throws Exception;
}
