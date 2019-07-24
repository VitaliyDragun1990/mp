package org.myphotos.domain.model;

import org.myphotos.infra.exception.business.ValidationException;

/**
 * Represents possible image sorting mode.
 * 
 * @author Vitaliy Dragun
 *
 */
public enum SortMode {

	PHOTO_POPULARITY,
	
	AUTHOR_POPULARITY;
	
	public static SortMode of(String name) {
		for (SortMode sortMode : SortMode.values()) {
			if (sortMode.name().equalsIgnoreCase(name)) {
				return sortMode;
			}
		}
		throw new ValidationException("Undefined sort mode: " + String.valueOf(name).toUpperCase());
	}
}
