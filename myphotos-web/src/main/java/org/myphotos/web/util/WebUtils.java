package org.myphotos.web.util;

import javax.servlet.http.HttpServletRequest;

public final class WebUtils {
	private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";
	private static final String AJAX_HEADER_NAME = "X-Requested-With";

	public static boolean isAjaxRequest(HttpServletRequest request ) {
		return AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME));
	}
	
	private WebUtils() {}
}
