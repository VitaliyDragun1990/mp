package org.myphotos.web.router;

import java.io.IOException;
import java.util.function.Supplier;

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

	/**
	 * Forwards to page with provided {@code pageName}
	 */
	void forwardToPage(String pageName, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

	/**
	 * Forwards to fragment with provided {@code fragmentName}
	 */
	void forwardToFragment(String fragmentName, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException;

	/**
	 * Redirects to specified {@code url}
	 */
	void redirectToUrl(String url, HttpServletResponse response) throws IOException;

	/**
	 * Redirects to {@code tempAuthUrl} if current user is temporary authenticated.
	 * If such user is fully authenticated - redirects to URL gottent from
	 * {@code authUrlSupplier}
	 * 
	 * @param tempAuthUrl     URL to redirect if current user is temporary
	 *                        authenticated
	 * @param authUrlSupplier provides URL to redirect if current user is fully
	 *                        authenticated
	 * @param response
	 * @throws IOException
	 */
	void redirectToAuthUrl(String tempAuthUrl, Supplier<String> authUrlSupplier, HttpServletResponse response)
			throws IOException;

	/**
	 * Sends json as a response to specified request
	 */
	void sendJson(JsonObject json, HttpServletResponse response) throws IOException;

	/**
	 * Sends json as text/plain content type
	 */
	void sendJsonAsText(JsonObject json, HttpServletResponse response) throws IOException;
}
