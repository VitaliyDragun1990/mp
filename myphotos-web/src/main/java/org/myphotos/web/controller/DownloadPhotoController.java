package org.myphotos.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.myphotos.domain.model.Image;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.web.util.UrlExtractorUtils;

@WebServlet(urlPatterns = "/download/*", loadOnStartup = 1)
public class DownloadPhotoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PhotoService photoService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long photoId = Long.parseLong(UrlExtractorUtils.getPathVariableValue(req.getRequestURI(), "/download/", ".jpg"));
		Image originalPhoto = photoService.downloadOriginalPhoto(photoId);
		
		sendPhoto(resp, originalPhoto);
	}

	private void sendPhoto(HttpServletResponse resp, Image originalPhoto) throws IOException {
		resp.setHeader("Content-Disposition", "attachment; filename="+originalPhoto.getName());
		resp.setContentType(getServletContext().getMimeType(originalPhoto.getName()));
		resp.setContentLengthLong(originalPhoto.getSizeInBytes());
		
		try (InputStream in = originalPhoto.getInput();
				OutputStream out = resp.getOutputStream()) {
			IOUtils.copyLarge(in, out);
		}
	}
}
