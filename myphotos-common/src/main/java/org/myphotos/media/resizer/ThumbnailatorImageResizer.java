package org.myphotos.media.resizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.enterprise.context.ApplicationScoped;

import org.myphotos.media.model.ImageCategory;
import org.myphotos.media.resizer.exception.ImageResizingException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@ApplicationScoped
class ThumbnailatorImageResizer implements ImageResizer {

	@Override
	public void resize(Path sourcePath, Path destinationPath, ImageCategory imageCategory) {
		try {
			Thumbnails.Builder<File> builder = Thumbnails.of(sourcePath.toFile());
			if (imageCategory.isCrop()) {
				builder.crop(Positions.CENTER);
			}
			builder.size(imageCategory.getWidth(), imageCategory.getHeight())
				.outputFormat(imageCategory.getOutputFormat())
				.outputQuality(imageCategory.getQuality())
				.allowOverwrite(true)
				.toFile(destinationPath.toFile());
		} catch (IOException e) {
			throw new ImageResizingException("Can't resize image: " + e.getMessage(), e);
		}
	}

}
