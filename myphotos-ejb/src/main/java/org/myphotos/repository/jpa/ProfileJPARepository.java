package org.myphotos.repository.jpa;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.persistence.NoResultException;
import javax.persistence.StoredProcedureQuery;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.repository.ProfileRepository;
import org.myphotos.repository.jpa.query.JPAQuery;
import org.myphotos.repository.jpa.query.JPARepository;

@JPARepository(Profile.class)
@DBSource
@Dependent
class ProfileJPARepository extends AbstractJPARepository<Profile, Long> implements ProfileRepository {

	@Override
	@JPAQuery("SELECT p FROM Profile p WHERE p.uid=:uid")
	public Optional<Profile> findByUid(String uid) {
		try {
			Profile profile = em.createNamedQuery("Profile.findByUid", Profile.class)
					.setParameter("uid", uid)
					.getSingleResult();
			return Optional.of(profile);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	@JPAQuery("SELECT p FROM Profile p WHERE p.email=:email")
	public Optional<Profile> findByEmail(String email) {
		try {
			Profile profile = em.createNamedQuery("Profile.findByEmail", Profile.class)
					.setParameter("email", email)
					.getSingleResult();
			return Optional.of(profile);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	public void updateRating() {
		StoredProcedureQuery query = em.createNamedStoredProcedureQuery("update_rating");
		query.execute();
	}

	@Override
	@JPAQuery("SELECT p.uid FROM Profile p WHERE p.uid IN :uids")
	public List<String> findUids(List<String> uids) {
		return em.createNamedQuery("Profile.findUids", String.class)
				.setParameter("uids", uids)
				.getResultList();
	}

	@Override
	protected Class<Profile> getEntityClass() {
		return Profile.class;
	}

}
