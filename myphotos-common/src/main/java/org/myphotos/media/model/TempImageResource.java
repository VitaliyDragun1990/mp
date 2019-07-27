package org.myphotos.media.model;

import java.nio.file.Path;

import org.myphotos.domain.model.ImageResource;

/**
 * Represents temporary stored image resource
 * 
 * @author Vitaliy Dragun
 *
 */
public class TempImageResource implements ImageResource {
	private final Path path;

	public TempImageResource() {
		this("jpg");
	}

	public TempImageResource(String extension) {
		this.path = TempFileFactory.createTempFile(extension);
	}

	@Override
	public Path getPath() {
		return path;
	}

	@Override
	public void close() {
		TempFileFactory.deleteTempFile(path);
	}

	@Override
	public String toString() {
		return String.format("%s.%s", getClass().getSimpleName(), path);
	}

}
