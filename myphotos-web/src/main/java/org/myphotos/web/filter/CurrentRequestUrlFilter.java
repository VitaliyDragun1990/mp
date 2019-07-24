package org.myphotos.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "CurrentRequestUrlFilter", asyncSupported = true)
public class CurrentRequestUrlFilter extends AbstractFilter {
	public static final String CURRENT_REQUEST_URL = "currentRequestUrl";

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setAttribute(CURRENT_REQUEST_URL, request.getRequestURI());
		chain.doFilter(request, response);
	}

}
