package org.myphotos.ejb.repository.jpa.query;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

/**
 * Registers specified named queries
 * 
 * @author Vitaliy Dragun
 *
 */
@Dependent
public class JPAQueryRegistrar {

	private Logger logger;
	private EntityManagerFactory entityManagerFactory;

	/**
	 * Register specified named queries
	 */
	public void registerNamedQueries(Map<String, String> namedQueriesMap) {
		EntityManager em = entityManagerFactory.createEntityManager();
		logger.log(Level.INFO, "Registering {0} named JPQL queries...", namedQueriesMap.size());
		
		try {
			registerNamedQueries(namedQueriesMap, em);
			logger.log(Level.INFO, "{0} named JPQL queries have been registered successfully", namedQueriesMap.size());
		} finally {
			em.close();
		}
	}

	private void registerNamedQueries(Map<String, String> namedQueriesMap, EntityManager em) {
		for (Map.Entry<String, String> entry : namedQueriesMap.entrySet()) {
			registerNamedQuery(em, entry.getKey(), entry.getValue());
		}
	}

	private void registerNamedQuery(EntityManager em, String queryName, String queryValue) {
		try {
			entityManagerFactory.addNamedQuery(queryName, em.createQuery(queryValue));
			logger.log(Level.FINE, "Registered named query: {0} -> {1}", new Object[] { queryName, queryValue });
		} catch (RuntimeException e) {
			throw new RuntimeException("Invalid query: " + queryName, e);
		}
	}

	@Inject
	void setLogger(Logger logger) {
		this.logger = logger;
	}

	@PersistenceUnit
	void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
}
