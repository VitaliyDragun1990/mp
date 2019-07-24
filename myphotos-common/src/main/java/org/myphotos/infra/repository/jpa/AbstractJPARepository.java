package org.myphotos.infra.repository.jpa;

import java.util.Optional;

import javax.enterprise.inject.Vetoed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.myphotos.infra.repository.EntityRepository;

@Vetoed
abstract class AbstractJPARepository<T, I> implements EntityRepository<T, I> {
	
	@PersistenceContext(unitName = "org.myphotos.pu")
	protected EntityManager em;
	
	protected abstract Class<T> getEntityClass();

	@Override
	public Optional<T> findById(I id) {
		return Optional.ofNullable(em.find(getEntityClass(), id));
	}

	@Override
	public void create(T entity) {
		em.persist(entity);
	}

	@Override
	public void update(T entity) {
		em.merge(entity);
	}

	@Override
	public void delete(T entity) {
		em.refresh(entity);
	}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public T getProxyInstance(I id) {
		return em.getReference(getEntityClass(), id);
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}

}
