package org.myphotos.validation.validator;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.myphotos.validation.PastTime;

public class PastTimeConstraintValidator implements ConstraintValidator<PastTime, LocalDateTime> {

	@Override
	public void initialize(PastTime constraintAnnotation) {
		// empty
	}

	@Override
	public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return value.isBefore(LocalDateTime.now());
	}

}
