package org.myphotos.repository.jpa;

import java.util.List;

import javax.enterprise.context.Dependent;

import org.myphotos.domain.entity.Photo;
import org.myphotos.infra.repository.PhotoRepository;
import org.myphotos.repository.jpa.query.JPAQuery;
import org.myphotos.repository.jpa.query.JPARepository;

@JPARepository(Photo.class)
@DBSource
@Dependent
class PhotoJPARepository extends AbstractJPARepository<Photo, Long> implements PhotoRepository {

	@Override
	@JPAQuery("SELECT ph FROM Photo ph WHERE ph.profile.id=:profileId ORDER BY ph.id DESC")
	public List<Photo> findProfilePhotosLatestFirst(Long profileId, int offset, int limit) {
		return em.createNamedQuery("Photo.findProfilePhotosLatestFirst", Photo.class)
				.setParameter("profileId", profileId)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}

	@Override
	@JPAQuery("SELECT COUNT(ph) FROM Photo ph WHERE ph.profile.id=:profileId")
	public int countProfilePhotos(Long profileId) {
		Object count = em.createNamedQuery("Photo.countProfilePhotos")
				.setParameter("profileId", profileId)
				.getSingleResult();
		return ((Number)count).intValue();
	}

	@Override
	@JPAQuery("SELECT e FROM Photo e JOIN FETCH e.profile ORDER BY e.views DESC")
	public List<Photo> findAllOrderedByViewsDesc(int offset, int limit) {
		return em.createNamedQuery("Photo.findAllOrderedByViewsDesc", Photo.class)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}

	@Override
	@JPAQuery("SELECT e FROM Photo e JOIN FETCH e.profile p ORDER BY p.rating DESC, e.views DESC")
	public List<Photo> findAllOrderedByProfileRatingDesc(int offset, int limit) {
		return em.createNamedQuery("Photo.findAllOrderedByProfileRatingDesc", Photo.class)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}

	@Override
	@JPAQuery("SELECT COUNT(ph) FROM Photo ph")
	public long countAll() {
		Object count = em.createNamedQuery("Photo.countAll")
				.getSingleResult();
		return ((Number)count).intValue();
	}

	@Override
	protected Class<Photo> getEntityClass() {
		return Photo.class;
	}

}
