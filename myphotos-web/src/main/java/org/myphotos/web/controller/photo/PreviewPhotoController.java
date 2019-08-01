package org.myphotos.web.controller.photo;

import java.io.IOException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.domain.service.PhotoService;
import org.myphotos.web.router.Router;
import org.myphotos.web.util.UrlExtractorUtils;

@WebServlet(urlPatterns = "/preview/*", loadOnStartup = 1)
public class PreviewPhotoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private PhotoService photoService;
	@Inject
	private Router router;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long photoId = Long.parseLong(UrlExtractorUtils.getPathVariableValue(req.getRequestURI(), "/preview/", ".jpg"));
		String redirectUrl = photoService.viewLargePhoto(photoId);
		router.redirectToUrl(redirectUrl, resp);
	}
}
