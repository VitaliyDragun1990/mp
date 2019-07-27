package org.myphotos.media.generator;

import javax.enterprise.inject.Vetoed;

@Vetoed
public interface ImageFileNameGenerator {

	String generateUniqueFileName();
}
