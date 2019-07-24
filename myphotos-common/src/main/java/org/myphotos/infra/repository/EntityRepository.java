package org.myphotos.infra.repository;

import java.util.Optional;

public interface EntityRepository<T, I> {

	Optional<T> findById(I id);

	void create(T entity);
	
	void update(T entity);
	
	void delete(T entity);
	
	void flush();
	
	T getProxyInstance(I id);
}
