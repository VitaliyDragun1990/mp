package org.myphotos.web.router;

import java.io.IOException;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents simple router abstraction
 * 
 * @author Vitaliy Dragun
 *
 */
public interface Router {

	void forwardToPage(String pageName, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
	
	void forwardToFragment(String fragmentName, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;
	
	void redirectToUrl(String url, HttpServletResponse response) throws IOException;
	
	void sendJson(JsonObject json, HttpServletRequest request, HttpServletResponse response) throws IOException;
}
