package org.myphotos.generator;

import static org.myphotos.infra.cdi.qualifier.UidGenerator.Category.PRIMARY;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.myphotos.infra.cdi.qualifier.UidGenerator;
import org.myphotos.infra.util.CommonUtils;

@ApplicationScoped
@UidGenerator(PRIMARY)
class PrimaryProfileUidGenerator implements ProfileUidGenerator {

	@Override
	public List<String> generateProfileUidCandidates(String firstName, String lastName) {
		return CommonUtils.getSafeList(Arrays.asList(
				String.format("%s-%s", firstName, lastName).toLowerCase(),
				String.format("%s.%s", firstName, lastName).toLowerCase(),
				String.format("%s%s", firstName, lastName).toLowerCase()
		));
	}

}
