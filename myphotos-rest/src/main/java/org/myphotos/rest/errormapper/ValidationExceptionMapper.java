package org.myphotos.rest.errormapper;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.myphotos.infra.exception.business.ValidationException;
import org.myphotos.rest.model.ErrorMessageREST;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

	@Override
	public Response toResponse(ValidationException exception) {
		return Response
				.status(Status.BAD_REQUEST)
				.entity(new ErrorMessageREST(exception.getMessage(), true))
				.type(APPLICATION_JSON)
				.build();
	}

}
