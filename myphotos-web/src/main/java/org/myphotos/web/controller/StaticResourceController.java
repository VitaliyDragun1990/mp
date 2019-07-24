package org.myphotos.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles static resource requests
 * 
 * @author Vitaliy Dragun
 *
 */
@WebServlet(urlPatterns = { "/static/*", "/favicon.ico" }, loadOnStartup = 1)
public class StaticResourceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path resourcePath = Paths.get(getServletContext().getRealPath("/") + req.getRequestURI());
		String resourceMimeType = getServletContext().getMimeType(resourcePath.toString());
		long resourceContentLength = Files.size(resourcePath);

		sendStaticResource(resp, resourcePath, resourceMimeType, resourceContentLength);
	}

	private void sendStaticResource(HttpServletResponse resp, Path resourcePath, String resourceMimeType,
			long resourceContentLength) throws IOException {
		resp.setContentType(resourceMimeType);
		resp.setContentLengthLong(resourceContentLength);
		try (OutputStream out = resp.getOutputStream()) {
			Files.copy(resourcePath, out);
		}
	}
}
