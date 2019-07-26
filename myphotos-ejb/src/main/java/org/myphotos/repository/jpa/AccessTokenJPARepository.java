package org.myphotos.repository.jpa;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.persistence.NoResultException;

import org.myphotos.infra.repository.AccessTokenRepository;
import org.myphotos.repository.jpa.query.JPAQuery;
import org.myphotos.repository.jpa.query.JPARepository;
import org.myphotos.security.AccessToken;

@JPARepository(AccessToken.class)
@DBSource
@Dependent
class AccessTokenJPARepository extends AbstractJPARepository<AccessToken, String> implements AccessTokenRepository {

	@Override
	@JPAQuery("SELECT at FROM AccessToken at JOIN FETCH at.profile WHERE at.token=:token")
	public Optional<AccessToken> findByToken(String token) {
		try {
			AccessToken accessToken = em.createNamedQuery("AccessToken.findByToken", AccessToken.class)
					.setParameter("token", token)
					.getSingleResult();
			return Optional.of(accessToken);
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	@Override
	@JPAQuery("DELETE FROM AccessToken at WHERE at.token=:token")
	public boolean removeAccessToken(String token) {
		int result = em.createNamedQuery("AccessToken.removeAccessToken")
				.setParameter("token", token)
				.executeUpdate();
		return result == 1;
	}

	@Override
	protected Class<AccessToken> getEntityClass() {
		return AccessToken.class;
	}
}
