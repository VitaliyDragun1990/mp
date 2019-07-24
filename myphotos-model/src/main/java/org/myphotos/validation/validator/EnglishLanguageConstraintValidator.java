package org.myphotos.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.myphotos.validation.EnglishLanguage;

public class EnglishLanguageConstraintValidator implements ConstraintValidator<EnglishLanguage, String> {
	private static final String SPEC_SYMBOLS = "~#$%^&*-+=_\\|/@`!'\";:><,.?{}";
	private static final String PUNCTUATIONS = ".,?!-:()'\"[]{}; \t\n";
	private static final String NUMBERS = "0123456789";
	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private String validationTemplate;
	
	@Override
	public void initialize(EnglishLanguage constraintAnnotation) {
		boolean withNumbers = constraintAnnotation.withNumbers();
		boolean withPunctuations = constraintAnnotation.withPunctuations();
		boolean withSpecSymbols = constraintAnnotation.withSpecSymbols();
		
		this.validationTemplate = getValidationTemplate(withNumbers, withPunctuations, withSpecSymbols);
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (isCharacterForbidden(ch)) {
				return false;
			}
		}
		return true;
	}

	private boolean isCharacterForbidden(char ch) {
		return validationTemplate.indexOf(ch) == -1;
	}

	private String getValidationTemplate(boolean withNumbers, boolean withPunctuations, boolean withSpecSymbols) {
		StringBuilder template = new StringBuilder(LETTERS);
		if (withNumbers) {
			template.append(NUMBERS);
		}
		if (withPunctuations) {
			template.append(PUNCTUATIONS);
		}
		if (withSpecSymbols) {
			template.append(SPEC_SYMBOLS);
		}
		return template.toString();
	}

}