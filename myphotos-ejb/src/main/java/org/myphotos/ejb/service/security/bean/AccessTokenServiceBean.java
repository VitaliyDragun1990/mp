package org.myphotos.ejb.service.security.bean;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.repository.AccessTokenRepository;
import org.myphotos.security.AccessToken;
import org.myphotos.security.AccessTokenService;
import org.myphotos.security.exception.AccessForbiddenException;
import org.myphotos.security.exception.InvalidAccessTokenException;

@Stateless
@Local(AccessTokenService.class)
public class AccessTokenServiceBean implements AccessTokenService {
	private Logger logger;
	private AccessTokenRepository accessTokenRepository;
	
	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	@Inject
	public void setAccessTokenRepository(AccessTokenRepository accessTokenRepository) {
		this.accessTokenRepository = accessTokenRepository;
	}

	@Override
	public AccessToken generateAccessToken(Profile profile) {
		AccessToken accessToken = new AccessToken();
		accessToken.setProfile(profile);
		accessTokenRepository.create(accessToken);
		return accessToken;
	}

	@Override
	public Profile findProfile(String token, Long profileId) {
		Optional<AccessToken> accessToken = accessTokenRepository.findByToken(token);
		if (!accessToken.isPresent()) {
			throw new InvalidAccessTokenException(String.format("Access token %s invalid", token));
		}
		Profile profile = accessToken.get().getProfile();
		if (!profile.getId().equals(profileId)) {
			throw new AccessForbiddenException(String.format("Access forbidden for token=%s and profileId=%s", token, profileId));
		}
		return profile;
	}

	@Override
	public void invalidateAccessToken(String token) {
		boolean removed = accessTokenRepository.removeAccessToken(token);
		if (!removed) {
			logger.log(Level.WARNING, "Access token {0} not found", token);
			throw new InvalidAccessTokenException("Access toke not found");
		}

	}

}
