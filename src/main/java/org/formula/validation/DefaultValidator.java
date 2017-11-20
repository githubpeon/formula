package org.formula.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.formula.binding.FormBinding;

public class DefaultValidator implements Validator {

	private Object view;
	private final List<FieldValidator> fieldValidators = new ArrayList<FieldValidator>();
	private Set<FormBinding> formBindings = new HashSet<FormBinding>();

	public DefaultValidator() {
	}

	public Object getView() {
		return view;
	}

	@Override
	public void setView(Object view) {
		this.view = view;
	}

	@Override
	public List<FieldValidator> getFieldValidators() {
		return fieldValidators;
	}

	@Override
	public void addFieldValidator(FieldValidator fieldValidator) {
		fieldValidators.add(fieldValidator);
	}

	public Set<FormBinding> getFormBindings() {
		return formBindings;
	}

	@Override
	public void setFormBindings(Set<FormBinding> formBindings) {
		this.formBindings = formBindings;
	}

	@Override
	public ValidationResult validate(Map<String, Object> model) {
		ValidationResult validationResult = validateFields(model, new ValidationResult());
		if (!validationResult.hasErrors()) {
			validationResult = validateForm(model, validationResult);
		}
		return validationResult;
	}

	protected ValidationResult validateFields(Map<String, Object> model, ValidationResult validationResult) {
		for (FieldValidator fieldValidator : fieldValidators) {
			ValidationResult fieldValidationResult = fieldValidator.validate(model);
			validationResult.getPropertyValidationMessages().addAll(fieldValidationResult.getPropertyValidationMessages());
		}
		return validationResult;
	}

	protected ValidationResult validateForm(Map<String, Object> model, ValidationResult validationResult) {
		return validationResult;
	}
}
