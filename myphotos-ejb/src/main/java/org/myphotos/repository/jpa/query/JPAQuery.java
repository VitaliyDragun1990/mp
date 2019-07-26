package org.myphotos.repository.jpa.query;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Place this annotation at repository method to declare static named JPQL query
 * there instead of declaring it on your entity or in the mappings.xml
 * 
 * @author Vitaliy Dragun
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface JPAQuery {

	/**
	 * Name of the JPQL named query, if no specified, name of the
	 * <Entity>.<repository method name> at which this {@link JPAQuery} annotation
	 * is placed will be used.
	 * 
	 */
	String name() default "";

	/**
	 * JPQL query declaration
	 * 
	 */
	String value();
}
