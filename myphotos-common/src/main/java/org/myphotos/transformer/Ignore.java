package org.myphotos.transformer;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Fields marked with this annotation (either in business entity and/or dto
 * class) will be skipped during transformation provided by {@link Transformer}
 * component.
 * 
 * @author Vitaliy Dragun
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Ignore {

}
