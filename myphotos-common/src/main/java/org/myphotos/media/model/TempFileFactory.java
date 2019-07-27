package org.myphotos.media.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

class TempFileFactory {

	static Path createTempFile(String extension) {
		String uniqueFileName = String.format("%s.%s", UUID.randomUUID(), extension);
		String temDirectoryPath = System.getProperty("java.io.tmpdir");
		Path tempFilePath = Paths.get(temDirectoryPath, uniqueFileName);
		try {
			return Files.createFile(tempFilePath);
		} catch (IOException e) {
			throw new CantCreateTempFileException(tempFilePath, e);
		}
	}
	
	static void deleteTempFile(Path path) {
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
