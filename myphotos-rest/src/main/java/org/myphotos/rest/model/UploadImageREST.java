package org.myphotos.rest.model;

import org.apache.commons.fileupload.FileItem;
import org.myphotos.domain.model.ImageResource;
import org.myphotos.media.model.AbstractMimeTypeImageResource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Represents image uploaded to the application via REST request.
 * 
 * @author Vitaliy Dragun
 *
 */
@ApiModel("UploadImage")
public class UploadImageREST {

	@ApiModelProperty(hidden = true)
	private final FileItem fileItem;

	public UploadImageREST(FileItem fileItem) {
		this.fileItem = fileItem;
	}

	@ApiModelProperty(hidden = true)
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
