package org.myphotos.web.model;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.Part;

import org.myphotos.infra.util.Checks;
import org.myphotos.media.model.AbstractMimeTypeImageResource;

/**
 * Represents image resource uploaded via multipart form-data submit
 * 
 * @author Vitaliy Dragun
 *
 */
public class MultipartImageResource extends AbstractMimeTypeImageResource {
	
	private Part part;
	
	public MultipartImageResource(Part part) {
		Checks.checkParam(part != null, "Part parameter can not be null: " + getClass());
		this.part = part;
	}

	@Override
	protected String getContentType() {
		return part.getContentType();
	}

	@Override
	protected void deleteTempResources() throws Exception {
		part.delete();
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", getClass().getSimpleName(), part);
	}

	@Override
	protected void copyContent() throws Exception {
		Files.copy(part.getInputStream(), getPath(), StandardCopyOption.REPLACE_EXISTING);
	}

}
