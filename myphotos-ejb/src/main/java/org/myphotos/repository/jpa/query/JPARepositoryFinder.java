package org.myphotos.repository.jpa.query;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

/**
 * Responsible for finding managed beans classes that annotated with
 * {@link JPARepository} annotation
 * 
 * @author Vitaliy Dragun
 *
 */
@Dependent
public class JPARepositoryFinder {

	private Logger logger;
	private BeanManager beanManager;

	/**
	 * Finds all managed beans classes that annotated with {@link JPARepository}
	 * qualifier annotation
	 * 
	 */
	public Set<Class<?>> getJPARepositoryClasses() {
		Set<Class<?>> result = new HashSet<>();
		for (Bean<?> bean : beanManager.getBeans(Object.class, new JPARepositoryInstance())) {
			Class<?> beanClass = bean.getBeanClass();
			if (isCandidateValid(bean)) {
				result.add(beanClass);
				logger.log(Level.INFO, "Found {0} JPA respotiry class", beanClass.getName());
			}
		}
		return result;
	}

	protected boolean isCandidateValid(Bean<?> bean) {
		return true;
	}

	@Inject
	void setLogger(Logger logger) {
		this.logger = logger;
	}

	@Inject
	void setBeanManager(BeanManager beanManager) {
		this.beanManager = beanManager;
	}
}
