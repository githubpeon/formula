package com.github.githubpeon.formula.validation;

import java.util.Map;

public class NumberFieldValidator extends DefaultFieldValidator<Map<String, String>> {

	@Override
	public ValidationResult validate(Map<String, String> model) {
		ValidationResult validationResult = super.validate(model);

		try {
			String value = model.get(getFormFieldBinding().getProperty());
			if (value != null && !value.isEmpty()) {
				Integer.parseInt(value);
			}
		} catch (Exception e) {
			validationResult.addError(getFormFieldBinding().getProperty(), ValidationError.INVALID_NUMBER);
		}
		return validationResult;
	}
}
