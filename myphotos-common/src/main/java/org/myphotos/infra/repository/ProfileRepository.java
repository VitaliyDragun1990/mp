package org.myphotos.infra.repository;

import java.util.List;
import java.util.Optional;

import org.myphotos.domain.entity.Profile;

public interface ProfileRepository extends EntityRepository<Profile, Long> {

	Optional<Profile> findByUid(String uid);
	
	Optional<Profile> findByEmial(String email);
	
	void updateRating();
	
	List<String> findUids(List<String> uids);
}
