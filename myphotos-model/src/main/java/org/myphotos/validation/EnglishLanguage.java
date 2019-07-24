package org.myphotos.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.myphotos.validation.validator.EnglishLanguageConstraintValidator;

/**
 * Validation annotation to check whether some text value written in Enlish
 * language with some specified constrains.
 * 
 * @author Vitaliy Dragun
 *
 */
@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER, CONSTRUCTOR, ANNOTATION_TYPE })
@Constraint(validatedBy = { EnglishLanguageConstraintValidator.class })
public @interface EnglishLanguage {

	String message() default "javax.validation.constraints.EnglishLanguage.message";

	// 0123456789
	boolean withNumbers() default true;

	// .,?!-:()'"[]{}; \t\n
	boolean withPunctuations() default true;

	// ~#$%^&*-+=_\\|/@`!'\";:><,.?{}
	boolean withSpecSymbols() default true;

	Class<? extends Payload>[] payload() default {};

	Class<?>[] groups() default {};
}
