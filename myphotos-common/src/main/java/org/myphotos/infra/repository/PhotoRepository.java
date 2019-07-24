package org.myphotos.infra.repository;

import java.util.List;

import org.myphotos.domain.entity.Photo;

public interface PhotoRepository extends EntityRepository<Photo, Long> {

	List<Photo> findProfilePhotoLatestFirst(Long profileId, int offset, int limit);
	
	int countProfilePhotos(Long profileId);
	
	List<Photo> findAllOrderedByViewsDesc(int offset, int limit);

	List<Photo> findAllOrderedByProfileRatingDesc(int offset, int limit);
	
	long countAll();
}
