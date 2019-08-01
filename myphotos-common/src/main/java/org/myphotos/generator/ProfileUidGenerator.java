package org.myphotos.generator;

import java.util.List;

import org.myphotos.domain.entity.Profile;

/**
 * Generates {@link Profile}'s user identifiers
 * 
 * @author Vitaliy Dragun
 *
 */
public interface ProfileUidGenerator {

	List<String> generateProfileUidCandidates(String firstName, String lastName);
}
