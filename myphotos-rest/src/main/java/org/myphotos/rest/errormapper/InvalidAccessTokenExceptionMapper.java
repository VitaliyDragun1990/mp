package org.myphotos.rest.errormapper;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.myphotos.rest.StatusMessages.INVALID_ACCESS_TOKEN;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.myphotos.rest.model.ErrorMessageREST;
import org.myphotos.security.exception.InvalidAccessTokenException;

@Provider
@ApplicationScoped
public class InvalidAccessTokenExceptionMapper implements ExceptionMapper<InvalidAccessTokenException> {

	@Override
	public Response toResponse(InvalidAccessTokenException exception) {
		return Response
				.status(UNAUTHORIZED)
				.entity(new ErrorMessageREST(INVALID_ACCESS_TOKEN))
				.type(APPLICATION_JSON)
				.build();
	}

}
