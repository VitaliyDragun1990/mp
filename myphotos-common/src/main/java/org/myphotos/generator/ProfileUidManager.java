package org.myphotos.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.myphotos.infra.cdi.qualifier.UidGenerator;
import org.myphotos.infra.util.CommonUtils;

@ApplicationScoped
public class ProfileUidManager {

	@Inject
	private Logger logger;
	
	@Inject
	@Any
	private Instance<ProfileUidGenerator> uidGenerators;
	
	public List<String> getProfileUidCandidates(String firstName, String lastName) {
		List<String> result = new ArrayList<>();
		addCandidates(new PrimaryUidGenerator(), result, firstName, lastName);
		addCandidates(new SecondaryUidGenerator(), result, firstName, lastName);
		return CommonUtils.getSafeList(result);
	}

	public String getDefaultUid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	@PostConstruct
	private void init() {
		StringBuilder logMsg = new StringBuilder("Detected the following ProfileUidGenerators:\n");
		for (ProfileUidGenerator generator : uidGenerators) {
			logMsg.append(String.format("%s%n", generator.getClass().getName()));
		}
		logger.info(logMsg.toString());
	}
	
	private void addCandidates(AnnotationLiteral<UidGenerator> selector, List<String> result, String firstName,
			String lastName) {
		Instance<ProfileUidGenerator> generators = uidGenerators.select(selector);
		for (ProfileUidGenerator generator : generators) {
			result.addAll(generator.generateProfileUidCandidates(firstName, lastName));
		}
	}
	
	void setUidGenerators(Instance<ProfileUidGenerator> uidGenerators) {
		this.uidGenerators = uidGenerators;
	}
	
	void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	private static class PrimaryUidGenerator extends AnnotationLiteral<UidGenerator> implements UidGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public Category value() {
			return Category.PRIMARY;
		}
	}
	
	private static class SecondaryUidGenerator extends AnnotationLiteral<UidGenerator> implements UidGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public Category value() {
			return Category.SECONDARY;
		}
	}
}
