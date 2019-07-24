package org.myphotos.infra.util;

import org.myphotos.infra.exception.business.InvalidParameterException;
import org.myphotos.infra.exception.business.ValidationException;

public final class Checks {

	/**
	 * Verifies that specified parameter check passed or throws exception otherwise
	 * @param check parameter check to verify
	 * @param message specific message to pass to possible exception
	 * @throws InvalidParameterException if specified check failed
	 */
	public static void checkParam(boolean check, String message) {
		if (!check) {
			throw new InvalidParameterException(message);
		}
	}
	
	/**
	 * Verifies that specified validation check passed or throws exception otherwise
	 * @param check validation check to verify
	 * @param message specific message to pass to possible exception
	 * @throws ValidationException if specified validation check failed
	 */
	public static void assertValid(boolean check, String message) {
		if (!check) {
			throw new ValidationException(message);
		}
	}
	
	private Checks() {
	}

}
