package org.myphotos.security;

import java.util.Optional;

import javax.enterprise.inject.Vetoed;

import org.myphotos.domain.entity.Profile;
import static org.myphotos.infra.cdi.annotation.SocialProvider.Provider;

/**
 * Responsible for managing sign up process
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
public interface SignUpProcessManager {

	Optional<Profile> signUp(String authToken, Provider provider);
}
