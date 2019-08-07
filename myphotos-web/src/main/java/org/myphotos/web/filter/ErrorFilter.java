package org.myphotos.web.filter;

import static org.myphotos.web.Constants.EMPTY_MESSAGE;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.myphotos.infra.exception.business.BusinessException;
import org.myphotos.web.exception.ExceptionConverter;
import org.myphotos.web.exception.HttpStatusException;
import org.myphotos.web.model.ErrorModel;
import org.myphotos.web.router.Router;
import org.myphotos.web.util.WebUtils;

@WebFilter(filterName = "ErrorFilter", asyncSupported = true)
public class ErrorFilter extends AbstractFilter {

	@Inject
	private Logger logger;

	@Inject
	private ExceptionConverter exceptionConverter;

	@Inject
	private Router router;

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, new ExceptionThrowableHttpResponse(response));
		} catch (Throwable th) {
			processException(request, response, th);
		}
	}

	private void processException(HttpServletRequest request, HttpServletResponse response, Throwable th)
			throws IOException, ServletException {
		Throwable throwable = unwrapThrowable(th);
		logError(request, throwable);
		ErrorModel errorModel = exceptionConverter.convertToHttpStatus(throwable);
		handleError(errorModel, request, response);
	}

	private Throwable unwrapThrowable(Throwable th) {
		if (th instanceof ServletException && th.getCause() != null) {
			return th.getCause();
		} else {
			return th;
		}
	}

	private void logError(HttpServletRequest request, Throwable throwable) {
		String errorMessage = String.format("Can't process request: %s -> %s", request.getRequestURI(), throwable.getMessage());
		if (throwable instanceof BusinessException) {
			logger.log(Level.WARNING, errorMessage);
		} else {
			logger.log(Level.SEVERE, errorMessage, throwable);
		}
	}
	
	private void handleError(ErrorModel errorModel, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.reset();
		response.setStatus(errorModel.getStatus());
		
		if (WebUtils.isAjaxRequest(request)) {
			sendAjaxJsonErrorResponse(errorModel, response);
		} else {
			forwardToErrorPage(errorModel, request, response);
		}
	}
	
	private void sendAjaxJsonErrorResponse(ErrorModel errorModel, HttpServletResponse response) throws IOException {
		JsonObject json = Json.createObjectBuilder().add("success", false).add("error", errorModel.getMessage()).build();
		router.sendJson(json, response);
	}

	private void forwardToErrorPage(ErrorModel errorModel, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("errorModel", errorModel);
		router.forwardToPage("error", request, response);
	}

	/**
	 * This custom response wrapper throws {@link HttpStatusException} instead of
	 * sending error response directly to the client
	 * 
	 * @author Vitaliy Dragun
	 *
	 */
	private static class ExceptionThrowableHttpResponse extends HttpServletResponseWrapper {

		public ExceptionThrowableHttpResponse(HttpServletResponse response) {
			super(response);
		}

		@Override
		public void sendError(int sc) throws IOException {
			sendError(sc, EMPTY_MESSAGE);
		}

		@Override
		public void sendError(int sc, String msg) throws IOException {
			throw new HttpStatusException(sc, msg);
		}
	}
}
