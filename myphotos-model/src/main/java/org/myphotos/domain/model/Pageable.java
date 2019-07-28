package org.myphotos.domain.model;

import org.myphotos.infra.util.Checks;

/**
 * Represents pageable abstraction in the application
 * 
 * @author Vitaliy Dragun
 *
 */
public class Pageable {
	private final int page;
	private final int limit;
	
	public static Pageable of(int limit) {
		return new Pageable(limit);
	}
	
	public static Pageable of(int page, int limit) {
		return new Pageable(page, limit);
	}

	private Pageable(int limit) {
		this(1, limit);
	}

	private Pageable(int page, int limit) {
		validateParams(page, limit);

		this.page = page;
		this.limit = limit;
	}

	public int getPage() {
		return page;
	}

	public int getLimit() {
		return limit;
	}
	
	public int getOffset() {
		return (page - 1) * limit;
	}

	private static void validateParams(int page, int limit) {
		Checks.assertValid(page > 0, "Invalid page value. Should be >= 1");
		Checks.assertValid(limit > 0, "Invalid limit value. Should be >= 1");
	}

}
