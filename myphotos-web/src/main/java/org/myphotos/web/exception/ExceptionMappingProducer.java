package org.myphotos.web.exception;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.myphotos.web.Constants.DEFAULT_ERROR_MESSAGE;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import org.myphotos.infra.exception.business.ObjectNotFoundException;
import org.myphotos.infra.exception.business.ValidationException;
import org.myphotos.infra.util.CommonUtils;
import org.myphotos.security.exception.AccessForbiddenException;
import org.myphotos.security.exception.InvalidAccessTokenException;

/**
 * Producer responsible for producing mapping between exception class and
 * appropriate status code, and between status code and appropriate message.
 * 
 * @author Vitaliy Dragun
 *
 */
@Dependent
class ExceptionMappingProducer {

	@SuppressWarnings("rawtypes")
	@Produces
	public Map<Class, Integer> getExceptionToStatusCodeMapping() {
		Map<Class, Integer> map = new HashMap<>();
		
		map.put(ObjectNotFoundException.class, SC_NOT_FOUND);
		map.put(ValidationException.class, SC_BAD_REQUEST);
		map.put(AccessForbiddenException.class, SC_FORBIDDEN);
		map.put(InvalidAccessTokenException.class, SC_UNAUTHORIZED);
		
		return CommonUtils.getSafeMap(map);
	}
	
	@Produces
	public Map<Integer, String> getStatusCodeToMessageMapping() {
		Map<Integer, String> map = new HashMap<>();
		
		map.put(SC_BAD_REQUEST, "Current request is invalid");
		map.put(SC_UNAUTHORIZED, "Authentication required");
		map.put(SC_FORBIDDEN, "Requested operation not permitted");
		map.put(SC_NOT_FOUND, "Requested resource not found");
		map.put(SC_INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MESSAGE);
		
		return map;
	}
}
