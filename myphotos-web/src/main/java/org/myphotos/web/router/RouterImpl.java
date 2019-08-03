package org.myphotos.web.router;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.web.security.SecurityUtils;

@ApplicationScoped
class RouterImpl implements Router {

	private static final String JSON_CONTENT_TYPE = "application/json";
	private static final String CURRENT_PAGE = "currentPage";
	private static final String TEMPLATE_PAGE = "/WEB-INF/template/page-template.jsp";

	@Override
	public void forwardToPage(String pageName, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute(CURRENT_PAGE, resolveCurrentPage(pageName));
		request.getRequestDispatcher(TEMPLATE_PAGE).forward(request, response);
	}

	@Override
	public void forwardToFragment(String fragmentName, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(resolveFragment(fragmentName)).forward(request, response);
	}

	@Override
	public void redirectToUrl(String url, HttpServletResponse response) throws IOException {
		response.sendRedirect(url);
	}
	
	@Override
	public void redirectToAuthUrl(String tempAuthUrl, Supplier<String> authUrlSupplier, HttpServletResponse response)
			throws IOException {
		if (SecurityUtils.isTempAuthenticated()) {
			redirectToUrl(tempAuthUrl, response);
		} else {
			redirectToUrl(authUrlSupplier.get(), response);
		}
	}

	@Override
	public void sendJson(JsonObject json, HttpServletRequest request, HttpServletResponse response) throws IOException {
		sendContent(JSON_CONTENT_TYPE, json.toString(), response);
	}
	
	private String resolveCurrentPage(String pageName) {
		return String.format("../view/%s.jsp", pageName);
	}
	
	private String resolveFragment(String fragmentName) {
		return String.format("/WEB-INF/fragment/%s.jsp", fragmentName);
	}

	private void sendContent(String contentType, String content, HttpServletResponse response) throws IOException {
		int length = content.getBytes(StandardCharsets.UTF_8).length;
		
		response.setContentType(contentType);
		response.setContentLength(length);
		
		writeContent(content, response);
	}

	private void writeContent(String content, HttpServletResponse response) throws IOException {
		try (Writer wr = response.getWriter()) {
			wr.write(content);
			wr.flush();
		}
	}

}
