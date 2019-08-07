package org.myphotos.rest.errormapper;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.myphotos.rest.StatusMessages.INTERRNAL_ERROR;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.myphotos.rest.model.ErrorMessageREST;
import org.myphotos.social.exception.SocialDataRetrievingException;

@Provider
@ApplicationScoped
public class SocialDataRetrievingExceptionMapper implements ExceptionMapper<SocialDataRetrievingException> {
	
	@Inject
	private Logger logger;

	@Override
	public Response toResponse(SocialDataRetrievingException exception) {
		logger.log(Level.SEVERE, INTERRNAL_ERROR, exception);
		
		return Response
				.status(Status.UNAUTHORIZED)
				.entity(new ErrorMessageREST(exception.getMessage()))
				.type(APPLICATION_JSON)
				.build();
	}

}
