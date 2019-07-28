package org.myphotos.media.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.domain.model.Image;
import org.myphotos.infra.cdi.annotation.Property;
import org.myphotos.media.generator.ImageFileNameGenerator;
import org.myphotos.media.model.ImageCategory;
import org.myphotos.media.storage.exception.ImageStorageException;

/**
 * Saves images on the same machine where application is running
 * 
 * @author Vitaliy Dragun
 *
 */
@ApplicationScoped
class LocalPathImageStorage implements ImageStorage {
	
	@Inject
	private Logger logger;
	
	@Inject
	@Property("myphotos.storage.root.dir")
	private String storageRoot;
	
	@Inject
	@Property("myphotos.media.absolute.root")
	private String mediaRoot;
	
	@Inject
	private ImageFileNameGenerator fileNameGenerator;

	@Override
	public String saveOriginalImage(Path path) {
		String fileName = fileNameGenerator.generateUniqueFileName();
		Path destinationPath = Paths.get(storageRoot, fileName);
		saveImage(path, destinationPath);
		return fileName;
	}

	@Override
	public Image getOriginalImage(String originalImageName) {
		Path originalPath = Paths.get(storageRoot, originalImageName);
		try {
			return new Image(
					Files.newInputStream(originalPath),
					Files.size(originalPath),
					originalPath.getFileName().toString());
		} catch (IOException e) {
			throw new ImageStorageException(String.format("Can't get access to original image: %s", originalPath), e);
		}
	}

	@Override
	public String savePublicImage(Path path, ImageCategory imageCategory) {
		String fileName = fileNameGenerator.generateUniqueFileName();
		Path destinationPath = Paths.get(mediaRoot, imageCategory.getRelativeRoot(), fileName);
		saveImage(path, destinationPath);
		return "/" + imageCategory.getRelativeRoot() + fileName;
	}

	@Override
	public void deletePublicImage(String url) {
		Path destinationPath = Paths.get(mediaRoot, url.substring(1));
		try {
			Files.deleteIfExists(destinationPath);
		} catch (IOException | RuntimeException e) {
			logger.log(Level.WARNING, "Delete public image operation failed: " + destinationPath, e);
		}
	}

	private void saveImage(Path sourcePath, Path destinationPath) {
		try {
			moveFile(sourcePath, destinationPath);
		} catch (IOException | RuntimeException e) {
			logger.log(Level.WARNING,
					String.format("Image file move from %s to %s failed. Trying to copy...", sourcePath, destinationPath), e);
			copyFile(sourcePath, destinationPath, e);
		}
		logger.log(Level.INFO, "Saved image: {0}", destinationPath);
	}

	private void copyFile(Path sourcePath, Path destinationPath, Exception suppressedException) {
		try {
			Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			throw buildImageStorageException("Can't save image", ex, suppressedException);
		}
	}
	
	private ImageStorageException buildImageStorageException(String message, Exception targetExc, Exception suppressedExc) {
		ImageStorageException imageStorageException = new ImageStorageException(message, targetExc);
		imageStorageException.addSuppressed(suppressedExc);
		return imageStorageException;
	}

	private void moveFile(Path sourcePath, Path destinationPath) throws IOException {
		Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
	}

	void setLogger(Logger logger) {
		this.logger = logger;
	}

	void setStorageRoot(String storageRoot) {
		this.storageRoot = storageRoot;
	}

	void setMediaRoot(String mediaRoot) {
		this.mediaRoot = mediaRoot;
	}

	void setFileNameGenerator(ImageFileNameGenerator fileNameGenerator) {
		this.fileNameGenerator = fileNameGenerator;
	}
	
}
