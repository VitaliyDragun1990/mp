package org.myphotos.repository.jpa.query;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.Dependent;

import org.myphotos.infra.util.CommonUtils;

/**
 * Component responsible for parsing all {@link JPAQuery} annotations found at
 * specified JPA repository classes
 * 
 * @author Vitaliy Dragun
 *
 */
@Dependent
public class JPAQueryParser {

	/**
	 * Returns map with named JPQL queries in format:<named query name>=<query
	 * value>
	 * 
	 * @param jpaRepositoryClasses classes which may contain methods annotated with
	 *                             {@link JPAQuery} annotation that should be parsed
	 */
	public Map<String, String> getNamedQueriesMap(Set<Class<?>> jpaRepositoryClasses) {
		Map<String, String> namedQueriesMap = new HashMap<>();
		for (Class<?> jpaRepositoryClass : jpaRepositoryClasses) {
			addQueriesFromJPARepository(jpaRepositoryClass, namedQueriesMap);
		}
		return namedQueriesMap;
	}

	private void addQueriesFromJPARepository(Class<?> jpaRepositoryClass, Map<String, String> namedQueriesMap) {
		for (Method method : CommonUtils.getMethodsWithAnnotation(jpaRepositoryClass, JPAQuery.class)) {
			JPAQuery jpaQuery = method.getAnnotation(JPAQuery.class);
			String queryName = jpaQuery.name();
			if (queryName.isEmpty()) {
				queryName = getDefaultQueryName(jpaRepositoryClass, method);
			}
			String query = jpaQuery.value();
			if (namedQueriesMap.put(queryName, query) != null) {
				throw new IllegalStateException("Detected named query duplicates: " + queryName);
			}
		}
	}

	private String getDefaultQueryName(Class<?> jpaRepositoryClass, Method method) {
		return String.format("%s.%s", getEntityClass(jpaRepositoryClass), method.getName());
	}

	private String getEntityClass(Class<?> jpaRepositoryClass) {
		if (jpaRepositoryClass.isAnnotationPresent(JPARepository.class)) {
			return jpaRepositoryClass.getAnnotation(JPARepository.class).value().getSimpleName();
		}
		throw new IllegalArgumentException("Class " + jpaRepositoryClass + " is not a valid JPA repository class: "
				+ "@JPARepository is not found");
	}
}
