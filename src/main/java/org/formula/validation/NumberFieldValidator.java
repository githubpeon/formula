package org.formula.validation;

import java.util.Map;

public class NumberFieldValidator extends DefaultFieldValidator {

	@Override
	public ValidationResult validate(Map<String, Object> model) {
		ValidationResult validationResult = super.validate(model);
		Object value = model.get(getFormFieldBinding().getProperty());

		if (!(value instanceof Integer)) {
			try {
				String valueString = (String) value;
				Integer.parseInt(valueString);
			} catch (Exception e) {
				validationResult.addError(getFormFieldBinding().getProperty(), value, getFormFieldBinding().getView(), ValidationError.INVALID_NUMBER);
			}
		}

		return validationResult;
	}

}
