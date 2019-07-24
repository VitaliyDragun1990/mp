package org.myphotos.ejb.repository.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.myphotos.domain.entity.Photo;
import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.util.CommonUtils;

final class InMemoryDataBase {

	public static final Profile PROFILE;
	public static final List<Photo> PHOTOS;
	
	static {
		PROFILE = createProfile();
		PHOTOS = createPhotos(PROFILE);
	}

	private static Profile createProfile() {
		Profile profile = new Profile();
		profile.setId(1L);
		profile.setUid("richard-hendricks");
		profile.setCreated(LocalDateTime.now());
		profile.setFirstName("Richard");
		profile.setLastName("Hendricks");
		profile.setJobTitle("CEO of Pied Piper");
		profile.setLocation("Los Angeles, California");
		profile.setAvatarUrl("https://devstudy-net.github.io/myphotos-com-test-images/Richard-Hendricks.jpg");
		return profile;
	}

	private static List<Photo> createPhotos(Profile profile) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		List<Photo> photos = new ArrayList<>();
		for (int i = 1; i <= 15; i++) {
			Photo photo = new Photo();
			photo.setProfile(profile);
			profile.setPhotoCount(profile.getPhotoCount() + 1);
			String imageUrl = String.format("https://devstudy-net.github.io/myphotos-com-test-images/%s.jpg", i % 6 + 1);
			photo.setSmallUrl(imageUrl);
			photo.setLargeUrl("https://devstudy-net.github.io/myphotos-com-test-images/large.jpg");
			photo.setOriginalUrl(imageUrl);
			photo.setViews(random.nextInt(100) * 10L + 1);
			photo.setDownloads(random.nextInt(20) * 10L + 1);
			photo.setCreated(LocalDateTime.now());
			
			photos.add(photo);
		}
		return CommonUtils.getSafeList(photos);
	}
	
	private InMemoryDataBase() {}
}
