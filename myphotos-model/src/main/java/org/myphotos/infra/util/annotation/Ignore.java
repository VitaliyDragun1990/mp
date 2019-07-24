package org.myphotos.infra.util.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Fields annotated with this annotation will be skipped during field-to-field
 * copying
 * 
 * @author Vitaliy Dragun
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Ignore {

}
