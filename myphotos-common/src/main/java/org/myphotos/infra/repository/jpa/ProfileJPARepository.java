package org.myphotos.infra.repository.jpa;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.repository.ProfileRepository;

@Dependent
class ProfileJPARepository extends AbstractJPARepository<Profile, Long> implements ProfileRepository {

	@Override
	public Optional<Profile> findByUid(String uid) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public Optional<Profile> findByEmail(String email) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void updateRating() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<String> findUids(List<String> uids) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected Class<Profile> getEntityClass() {
		return Profile.class;
	}

}
