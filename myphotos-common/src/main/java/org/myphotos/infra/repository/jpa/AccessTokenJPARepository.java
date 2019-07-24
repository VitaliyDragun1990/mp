package org.myphotos.infra.repository.jpa;

import java.util.Optional;

import javax.enterprise.context.Dependent;

import org.myphotos.infra.repository.AccessTokenRepository;
import org.myphotos.security.AccessToken;

@Dependent
class AccessTokenJPARepository extends AbstractJPARepository<AccessToken, String> implements AccessTokenRepository {

	@Override
	public Optional<AccessToken> findByToken(String token) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public boolean removeAccessToken(String token) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected Class<AccessToken> getEntityClass() {
		return AccessToken.class;
	}
}
