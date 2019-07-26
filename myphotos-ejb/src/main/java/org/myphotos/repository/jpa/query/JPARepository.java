package org.myphotos.repository.jpa.query;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Qualifier annotation to place at classes that represents JPA repositories
 * 
 * @author Vitaliy Dragun
 *
 */
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
@Qualifier
public @interface JPARepository {

	/**
	 * Class of the entity for which JPA respository is created
	 * @return
	 */
	@Nonbinding Class<?> value();
}
