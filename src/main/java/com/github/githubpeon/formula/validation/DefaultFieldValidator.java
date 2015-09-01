package com.github.githubpeon.formula.validation;

import java.util.Map;

import com.github.githubpeon.formula.binding.FormFieldBinding;

public class DefaultFieldValidator<T extends Map> implements FieldValidator<T> {

	private FormFieldBinding formFieldBinding;

	public FormFieldBinding getFormFieldBinding() {
		return formFieldBinding;
	}

	@Override
	public void setFormFieldBinding(FormFieldBinding formFieldBinding) {
		this.formFieldBinding = formFieldBinding;
	}

	@Override
	public ValidationResult validate(T model) {
		ValidationResult validationResult = new ValidationResult();
		if (getFormFieldBinding().isRequired()
				&& getFormFieldBinding().isEmpty()) {
			validationResult.addError(formFieldBinding.getProperty(), ValidationError.REQUIRED_FIELD_MISSING);
		}
		return validationResult;
	}

}
