package org.myphotos.web.controller;

import static org.myphotos.web.Constants.PHOTO_LIMIT;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.domain.model.Pageable;
import org.myphotos.domain.model.SortMode;
import org.myphotos.domain.service.PhotoService;
import org.myphotos.domain.service.ProfileService;
import org.myphotos.infra.util.CommonUtils;
import org.myphotos.web.router.Router;

@WebServlet(urlPatterns = "/", loadOnStartup = 1)
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final List<String> homeUrls;
	
	@EJB
	private ProfileService profileService;
	@EJB
	private PhotoService photoService;
	@Inject
	private Router router;
	
	public ProfileController() {
		this.homeUrls = CommonUtils.getSafeList(Arrays.asList("/"));
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		if (isHomeUrl(url)) {
			handleHomeRequest(req, resp);
		} else {
			handleProfileRequest(req, resp);
		}
	}

	private boolean isHomeUrl(String url) {
		return homeUrls.contains(url);
	}
	
	private void handleHomeRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SortMode sortMode = getSortMode(req);
		List<Photo> photos = photoService.findPopularPhotos(sortMode, Pageable.of(1, PHOTO_LIMIT));
		long totalCount = photoService.countAllPhotos();
		
		req.setAttribute("photos", photos);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("sortMode", sortMode.name().toLowerCase());
		
		router.forwardToPage("home", req, resp);
	}
	
	private void handleProfileRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uid = req.getRequestURI().substring(1);
		Profile profile = profileService.findByUid(uid);
		List<Photo> photos = photoService.findProfilePhotos(profile.getId(), Pageable.of(1, PHOTO_LIMIT));
		
		req.setAttribute("profile", profile);
		req.setAttribute("profilePhotos", Boolean.TRUE);
		req.setAttribute("photos", photos);
		
		router.forwardToPage("profile", req, resp);
	}

	private SortMode getSortMode(HttpServletRequest req) {
		Optional<String> sortMode = Optional.ofNullable(req.getParameter("sort"));
		return sortMode.isPresent() ? SortMode.of(sortMode.get()) : SortMode.POPULAR_PHOTO;
	}
}
