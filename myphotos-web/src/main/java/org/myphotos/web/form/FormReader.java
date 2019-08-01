package org.myphotos.web.form;

import java.lang.reflect.InvocationTargetException;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.myphotos.infra.exception.base.ApplicationException;

/**
 * Creates and populates form instances using {@link HttpServletRequest}
 * parameters.
 * 
 * @author Vitaliy Dragun
 *
 */
@ApplicationScoped
public class FormReader {

	public <T> T readForm(HttpServletRequest request, Class<T> formClass) {
		try {
			T form = formClass.newInstance();
			BeanUtils.populate(form, request.getParameterMap());
			return form;
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
			throw new ApplicationException("Can't create form: " + formClass, e);
		}
	}
}
