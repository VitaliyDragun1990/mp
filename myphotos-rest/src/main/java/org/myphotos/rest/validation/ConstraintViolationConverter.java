package org.myphotos.rest.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import javax.validation.Path;

import org.myphotos.model.ListMap;
import org.myphotos.rest.model.ValidationItemREST;
import org.myphotos.rest.model.ValidationResultREST;

@ApplicationScoped
public class ConstraintViolationConverter {

	public <T> ValidationResultREST convert(Set<ConstraintViolation<T>> violations) {
		ListMap<String, String> propertiesViolations = transformToPropertiesViolations(violations);
		
		return createValidationResult(propertiesViolations);
	}
	
	private <T> ListMap<String, String> transformToPropertiesViolations(Set<ConstraintViolation<T>> violations) {
		ListMap<String, String> propertiesViolations = new ListMap<>();
		
		for (ConstraintViolation<T> violation : violations) {
			for (Path.Node propetyPath : violation.getPropertyPath()) {
				propertiesViolations.add(propetyPath.getName(), violation.getMessage());
			}
		}
		
		return propertiesViolations;
	}

	private ValidationResultREST createValidationResult(ListMap<String, String> listMap) {
		List<ValidationItemREST> validationItems = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : listMap.toMap().entrySet()) {
			validationItems.add(new ValidationItemREST(entry.getKey(), entry.getValue()));
		}
		return new ValidationResultREST(validationItems);
	}
}
