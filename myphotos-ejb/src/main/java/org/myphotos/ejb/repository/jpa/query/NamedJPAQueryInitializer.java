package org.myphotos.ejb.repository.jpa.query;

import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class NamedJPAQueryInitializer {

	@Inject
	private JPARepositoryFinder jpaRepositoryFinder;
	@Inject
	private JPAQueryParser jpaQueryParser;
	@Inject
	private JPAQueryRegistrar jpaQueryRegistrar;
	
	@PostConstruct
	private void initializeNamedJPAQueries() {
		Set<Class<?>> jpaRepositoryClasses = jpaRepositoryFinder.getJPARepositoryClasses();
		Map<String, String> namedQueriesMap = jpaQueryParser.getNamedQueriesMap(jpaRepositoryClasses);
		jpaQueryRegistrar.registerNamedQueries(namedQueriesMap);
	}
}
