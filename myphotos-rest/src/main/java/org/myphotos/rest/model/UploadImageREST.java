package org.myphotos.rest.model;

import org.apache.commons.fileupload.FileItem;
import org.myphotos.domain.model.ImageResource;
import org.myphotos.media.model.AbstractMimeTypeImageResource;

/**
 * Represents image uploaded to the application via REST request.
 * 
 * @author Vitaliy Dragun
 *
 */
public class UploadImageREST {

	private final FileItem fileItem;

	public UploadImageREST(FileItem fileItem) {
		this.fileItem = fileItem;
	}

	public ImageResource getImageResource() {
		return new FileItemImageResource(fileItem);
	}

	static class FileItemImageResource extends AbstractMimeTypeImageResource {
		private final FileItem fileItem;

		public FileItemImageResource(FileItem fileItem) {
			this.fileItem = fileItem;
		}

		@Override
		protected String getContentType() {
			return fileItem.getContentType();
		}

		@Override
		protected void deleteTempResources() throws Exception {
			fileItem.delete();
		}

		@Override
		public String toString() {
			return String.format("%s(%s)", getClass().getSimpleName(), fileItem);
		}

		@Override
		protected void copyContent() throws Exception {
			fileItem.write(getPath().toFile());
		}

	}
}
