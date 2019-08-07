package org.myphotos.rest.errormapper;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.myphotos.rest.model.ErrorMessageREST;
import org.myphotos.security.exception.AccessForbiddenException;

@Provider
@ApplicationScoped
public class AccessForbiddenExceptionMapper implements ExceptionMapper<AccessForbiddenException> {

	@Override
	public Response toResponse(AccessForbiddenException exception) {
		return Response
				.status(FORBIDDEN)
				.entity(new ErrorMessageREST(exception.getMessage()))
				.type(APPLICATION_JSON)
				.build();
	}

}
