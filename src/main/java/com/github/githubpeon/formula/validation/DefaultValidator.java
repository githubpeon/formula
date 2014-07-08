package com.github.githubpeon.formula.validation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.githubpeon.formula.binding.FormBinding;
import com.github.githubpeon.formula.binding.FormFieldBinding;

public class DefaultValidator<T extends Map> implements Validator<T> {

	private Set<FormBinding> formBindings = new HashSet<FormBinding>();

	@Override
	public void setFormBindings(Set<FormBinding> formBindings) {
		this.formBindings = formBindings;
	}

	@Override
	public ValidationResult validate(T model) {
		ValidationResult validationResult = new ValidationResult();

		for (FormBinding formBinding : formBindings) {
			if (formBinding instanceof FormFieldBinding) {
				FormFieldBinding formFieldBinding = (FormFieldBinding) formBinding;
				if (formFieldBinding.isRequired()) {
					Object value = model.get(formFieldBinding.getProperty());
					if (value == null
							|| (value instanceof String && ((String) value).trim().isEmpty())) {
						validationResult.addError(formFieldBinding.getProperty(), ValidationError.REQUIRED_FIELD_MISSING.toString());
					}
				}
			}
		}
		return validationResult;
	}

}
