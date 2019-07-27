package org.myphotos.media.generator;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
class UUIDImageFileNameGenerator implements ImageFileNameGenerator {

	@Override
	public String generateUniqueFileName() {
		return UUID.randomUUID().toString() + ".jpg";
	}

}
