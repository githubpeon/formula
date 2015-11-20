package com.github.githubpeon.formula.validation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.githubpeon.formula.binding.FormBinding;

public class DefaultValidator<T extends Map> implements Validator<T> {

	private final Set<FieldValidator<T>> fieldValidators = new HashSet<FieldValidator<T>>();
	private Set<FormBinding> formBindings = new HashSet<FormBinding>();

	public DefaultValidator() {
	}

	public DefaultValidator(Set<FormBinding> formBindings) {
		this.formBindings = formBindings;
	}

	public Set<FieldValidator<T>> getFieldValidators() {
		return fieldValidators;
	}

	@Override
	public void addFieldValidator(FieldValidator<T> fieldValidator) {
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
	public ValidationResult validate(T model) {
		ValidationResult validationResult = validateFields(model, new ValidationResult());
		if (!validationResult.hasErrors()) {
			validationResult = validateForm(model, validationResult);
		}
		return validationResult;
	}

	protected ValidationResult validateFields(T model, ValidationResult validationResult) {
		for (FieldValidator<T> fieldValidator : fieldValidators) {
			ValidationResult fieldValidationResult = fieldValidator.validate(model);
			validationResult.getPropertyValidationMessages().putAll(fieldValidationResult.getPropertyValidationMessages());
		}
		return validationResult;
	}

	protected ValidationResult validateForm(T model, ValidationResult validationResult) {
		return validationResult;
	}
}
