package org.myphotos.generator;

import static org.myphotos.infra.cdi.qualifier.UidGenerator.Category.*;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.myphotos.infra.cdi.qualifier.UidGenerator;
import org.myphotos.infra.util.CommonUtils;

@ApplicationScoped
@UidGenerator(SECONDARY)
class SecondaryProfileUidGenerator implements ProfileUidGenerator {

	@Override
	public List<String> generateProfileUidCandidates(String firstName, String lastName) {
		return CommonUtils.getSafeList(Arrays.asList(
				String.format("%s-%s", firstName.charAt(0), lastName).toLowerCase(),
				String.format("%s.%s", firstName.charAt(0), lastName).toLowerCase(),
				String.format("%s%s", firstName.charAt(0), lastName).toLowerCase(),
				lastName.toLowerCase(),
				firstName.toLowerCase()
		));
	}

}
