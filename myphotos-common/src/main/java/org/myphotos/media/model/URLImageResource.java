package org.myphotos.media.model;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.myphotos.infra.exception.base.ApplicationException;

public class URLImageResource extends AbstractMimeTypeImageResource {
	
	private final String url;
	private final URLConnection urlConnection;
	
	public URLImageResource(String url) {
		this.url = url;
		try {
			this.urlConnection = new URL(url).openConnection();
		} catch (IOException e) {
			throw new ApplicationException("Can't open connection to url: " + url, e);
		}
	}

	@Override
	protected String getContentType() {
		return urlConnection.getContentType();
	}

	@Override
	protected void copyContent() throws Exception {
		Files.copy(urlConnection.getInputStream(), getPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	@Override
	protected void deleteTempResources() throws Exception {
		// do nothing
	}

	@Override
	public String toString() {
		return String.format("%s(%s)", getClass().getSimpleName(), url);
	}
}
