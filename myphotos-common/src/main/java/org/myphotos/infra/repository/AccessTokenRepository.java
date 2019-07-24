package org.myphotos.infra.repository;

import java.util.Optional;

import org.myphotos.security.AccessToken;

public interface AccessTokenRepository extends EntityRepository<AccessToken, String> {

	Optional<AccessToken> findByToken(String token);
	
	boolean removeAccessToken(String token);
}
