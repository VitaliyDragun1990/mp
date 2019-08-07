package org.myphotos.rest.errormapper;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.myphotos.rest.StatusMessages.INTERRNAL_ERROR;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.myphotos.rest.model.ErrorMessageREST;

@Provider
@ApplicationScoped
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
	
	@Inject
	private Logger logger;

	@Override
	public Response toResponse(Throwable exception) {
		logger.log(Level.SEVERE, INTERRNAL_ERROR, exception);
		
		return Response
				.status(INTERNAL_SERVER_ERROR)
				.entity(new ErrorMessageREST(INTERRNAL_ERROR))
				.type(APPLICATION_JSON)
				.build();
	}

}
