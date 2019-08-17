package org.myphotos.media.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory which provides temporary files creation and deletion functionality
 * 
 * @author Vitaliy Dragun
 *
 */
public class TempFileFactory {

	static Path createTempFile(String extension) {
		Path temporaryFilePath = buildTemporaryFilePath(extension);
		try {
			return Files.createFile(temporaryFilePath);
		} catch (IOException e) {
			throw new CantCreateTempFileException(temporaryFilePath, e);
		}
	}

	private static Path buildTemporaryFilePath(String extension) {
		String uniqueFileName = String.format("%s.%s", UUID.randomUUID(), extension);
		String temporaryDirectoryPath = System.getProperty("java.io.tmpdir");
		return Paths.get(temporaryDirectoryPath, uniqueFileName);
	}

	public static void deleteTempFile(Path path) {
		try {
			Files.deleteIfExists(path);
		} catch (IOException | RuntimeException e) {
			Logger.getLogger("TempFileEraser").log(Level.WARNING, "Can't delete temp file: " + path, e);
		}
	}

	private static class CantCreateTempFileException extends IllegalStateException {
		private static final long serialVersionUID = 1L;

		private CantCreateTempFileException(Path tempFilePath, Throwable t) {
			super("Can't create temp file: " + tempFilePath, t);
		}
	}

	private TempFileFactory() {
	}
}
