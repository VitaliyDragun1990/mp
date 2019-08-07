package org.myphotos.ws.error;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

import org.myphotos.infra.exception.business.BusinessException;

@Interceptor
@Dependent
public class ExceptionMapperInterceptor {

	@Inject
	private Logger logger;
	
	@AroundInvoke
	public Object aroundProcessImageRequest(InvocationContext ic) throws Exception {
		validateParameters(ic.getParameters());
		try {
			return ic.proceed();
		} catch (Exception e) {
			throw createAppropriateSOAPException(e);
		}
	}

	private SOAPFaultException createAppropriateSOAPException(Exception e) throws SOAPException {
		if (e instanceof BusinessException) {
			logger.log(Level.WARNING, "Ws request failed: {0}", e.getMessage());
			return new SOAPFaultException(createSOAPFault(e.getMessage(), true));
		} else {
			logger.log(Level.SEVERE, "Ws request failed: " + e.getMessage(), e);
			return new SOAPFaultException(createSOAPFault("Internal error", false));
		}
	}

	private void validateParameters(Object[] parameters) throws SOAPException {
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i] == null) {
				throw new SOAPFaultException(createSOAPFault("Parameter ["+i+"] is invalid", true));
			}
		}
	}

	private SOAPFault createSOAPFault(String message, boolean isClientError) throws SOAPException {
		String errorType = isClientError ? "Client" : "Server";
		SOAPFactory soapFactory = SOAPFactory.newInstance();
		return soapFactory.createFault(message, new QName("http://schemas.xmlsoap.org/soap/envelope/", errorType, ""));
	}
}
