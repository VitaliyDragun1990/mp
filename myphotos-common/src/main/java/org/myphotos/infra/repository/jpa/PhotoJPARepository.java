package org.myphotos.infra.repository.jpa;

import java.util.List;

import javax.enterprise.context.Dependent;

import org.myphotos.domain.entity.Photo;
import org.myphotos.infra.repository.PhotoRepository;

@Dependent
class PhotoJPARepository extends AbstractJPARepository<Photo, Long> implements PhotoRepository {

	@Override
	public List<Photo> findProfilePhotosLatestFirst(Long profileId, int offset, int limit) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public int countProfilePhotos(Long profileId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<Photo> findAllOrderedByViewsDesc(int offset, int limit) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<Photo> findAllOrderedByProfileRatingDesc(int offset, int limit) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public long countAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	protected Class<Photo> getEntityClass() {
		return Photo.class;
	}

}
