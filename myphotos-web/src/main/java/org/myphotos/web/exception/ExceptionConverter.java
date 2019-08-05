package org.myphotos.web.exception;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.myphotos.web.Constants.DEFAULT_ERROR_MESSAGE;
import static org.myphotos.web.Constants.EMPTY_MESSAGE;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.web.model.ErrorModel;

@ApplicationScoped
public class ExceptionConverter {

	@SuppressWarnings("rawtypes")
	@Inject
	private Map<Class, Integer> statusCodeMap;
	
	@Inject
	private Map<Integer, String> statusMessageMap;
	
	public ErrorModel convertToHttpStatus(Throwable throwable) {
		if (throwable instanceof HttpStatusException) {
			HttpStatusException ex = (HttpStatusException) throwable;
			return createErrorModel(ex.getStatus(), ex.getMessage());
		} else {
			Integer statusCode = statusCodeMap.getOrDefault(throwable.getClass(), SC_INTERNAL_SERVER_ERROR);
			return createErrorModel(statusCode, throwable.getMessage());
		}
	}

	private ErrorModel createErrorModel(Integer statusCode, String message) {
		if (statusCode == SC_INTERNAL_SERVER_ERROR) {
			return new ErrorModel(statusCode, DEFAULT_ERROR_MESSAGE);
		} else if (EMPTY_MESSAGE.equals(message)) {
			return new ErrorModel(statusCode, statusMessageMap.getOrDefault(statusCode, DEFAULT_ERROR_MESSAGE));
		} else {
			return new ErrorModel(statusCode, message);
		}
	}
}
