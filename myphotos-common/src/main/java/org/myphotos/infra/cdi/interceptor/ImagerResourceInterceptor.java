package org.myphotos.infra.cdi.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.myphotos.domain.model.ImageResource;

/**
 * Responsible for automatically call {@link ImageResource#close()} when method,
 * that takes {@link ImageResource} instance as first argument and which
 * alligible for this interceptor, finished.
 * 
 * @author Vitaliy Dragun
 *
 */
@Interceptor
public class ImagerResourceInterceptor {

	@AroundInvoke
	public Object aroundProcessImageResource(InvocationContext ic) throws Exception {
		try (ImageResource imageResource = (ImageResource) ic.getParameters()[0]) {
			return ic.proceed();
		}
	}
}
