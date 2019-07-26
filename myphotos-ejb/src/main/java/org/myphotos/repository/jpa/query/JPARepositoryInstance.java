package org.myphotos.repository.jpa.query;

import javax.enterprise.util.AnnotationLiteral;

public class JPARepositoryInstance extends AnnotationLiteral<JPARepository> implements JPARepository {
	private static final long serialVersionUID = 1L;
	
	public static JPARepository get() {
		return new JPARepositoryInstance();
	}

	@Override
	public Class<?> value() {
		return null;
	}

}
