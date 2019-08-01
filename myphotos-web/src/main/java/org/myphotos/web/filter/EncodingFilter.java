package org.myphotos.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Forcibly set character encoding for all requests and response to UTF-8 to
 * prevent errors related to inapropriate processing of different kinds of
 * cyrillic encodings
 * 
 * @author Vitaliy Dragun
 *
 */
@WebFilter(filterName = "EncodingFilter", asyncSupported = true)
public class EncodingFilter extends AbstractFilter {
	private static final String UTF_8 = "UTF-8";

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding(UTF_8);
		response.setCharacterEncoding(UTF_8);
		chain.doFilter(request, response);
	}

}
