package org.myphotos.web.controller;

import static org.myphotos.web.Constants.PHOTO_LIMIT;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.model.SortMode;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.web.router.Router;

@WebServlet(urlPatterns = "/photos/popular/more", loadOnStartup = 1)
public class MorePopularPhotosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private PhotoService photoService;
	@Inject
	private Router router;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SortMode sortMode = SortMode.of(req.getParameter("sort"));
		int page = Integer.parseInt(req.getParameter("page"));
		
		List<Photo> photos = photoService.findPopularPhotos(sortMode, Pageable.of(page, PHOTO_LIMIT));
		
		req.setAttribute("photos", photos);
		req.setAttribute("sortMode", sortMode.name().toLowerCase());
		router.forwardToFragment("more-photos", req, resp);
	}
}
