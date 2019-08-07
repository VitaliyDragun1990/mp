package org.myphotos.rest.errormapper;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.myphotos.infra.exception.business.ObjectNotFoundException;
import org.myphotos.rest.model.ErrorMessageREST;

@Provider
@ApplicationScoped
public class ObjectNotFoundExceptionMapper implements ExceptionMapper<ObjectNotFoundException> {

	@Override
	public Response toResponse(ObjectNotFoundException exception) {
		return Response
				.status(NOT_FOUND)
				.entity(new ErrorMessageREST(exception.getMessage()))
				.type(APPLICATION_JSON)
				.build();
	}

}
