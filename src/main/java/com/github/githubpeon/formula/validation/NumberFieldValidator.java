package com.github.githubpeon.formula.validation;

import java.util.Map;

public class NumberFieldValidator extends DefaultFieldValidator {

	@Override
	public ValidationResult validate(Map model) {
		ValidationResult validationResult = super.validate(model);

		try {
			String value = (String) model.get(getFormFieldBinding().getProperty());
			if (value != null && !value.isEmpty()) {
				Integer.parseInt(value);
			}
		} catch (Exception e) {
			validationResult.addError(getFormFieldBinding().getProperty(), ValidationError.INVALID_NUMBER);
		}
		return validationResult;
	}
}
