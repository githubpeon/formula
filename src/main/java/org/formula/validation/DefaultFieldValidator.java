package org.formula.validation;

import java.util.Map;

import org.formula.binding.FormFieldBinding;

public class DefaultFieldValidator implements FieldValidator {

	private FormFieldBinding formFieldBinding;

	@Override
	public FormFieldBinding getFormFieldBinding() {
		return formFieldBinding;
	}

	@Override
	public void setFormFieldBinding(FormFieldBinding formFieldBinding) {
		this.formFieldBinding = formFieldBinding;
	}

	@Override
	public ValidationResult validate(Map<String, Object> model) {
		ValidationResult validationResult = new ValidationResult();
		if (getFormFieldBinding().isRequired()
				&& getFormFieldBinding().isEmpty()) {
			validationResult.addError(formFieldBinding.getProperty(), formFieldBinding.getPropertyValue(), formFieldBinding.getView(), ValidationError.REQUIRED_FIELD_MISSING);
		}
		return validationResult;
	}

}
