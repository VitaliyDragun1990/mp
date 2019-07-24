package org.myphotos.domain.model;

import java.io.InputStream;

import org.myphotos.infra.util.Checks;

/**
 * Represents image stored in the application
 * 
 * @author Vitaliy Dragun
 *
 */
public class Image {
	private final InputStream input;
	private final long sizeInBytes;
	private final String name;

	public Image(InputStream input, long sizeInBytes, String name) {
		checkParams(input, sizeInBytes, name);

		this.input = input;
		this.sizeInBytes = sizeInBytes;
		this.name = name;
	}

	public InputStream getInput() {
		return input;
	}

	public long getSizeInBytes() {
		return sizeInBytes;
	}

	public String getName() {
		return name;
	}

	private static void checkParams(InputStream input, long sizeInBytes, String name) {
		Checks.checkParam(input != null, "input to read image data from can not be null");
		Checks.checkParam(name != null, "name of the image can not be null");
		Checks.checkParam(sizeInBytes > 0, "sizeInBytes of the image must be greater that zero");
	}

}
