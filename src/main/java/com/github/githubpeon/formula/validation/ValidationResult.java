package com.github.githubpeon.formula.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.githubpeon.formula.validation.ValidationMessage.Severity;

public class ValidationResult {

	private final Set<ValidationMessage> formValidationMessages = new HashSet<ValidationMessage>();
	private final Map<String, ValidationMessage> fieldValidationMessages = new HashMap<String, ValidationMessage>();

	public void addInfo(String message) {
		formValidationMessages.add(new ValidationMessage(message, Severity.INFO));
	}

	public void addInfo(String property, String message) {
		fieldValidationMessages.put(property, new ValidationMessage(message, Severity.INFO));
	}

	public void addWarning(String message) {
		formValidationMessages.add(new ValidationMessage(message, Severity.WARNING));
	}

	public void addWarning(String property, String message) {
		fieldValidationMessages.put(property, new ValidationMessage(message, Severity.WARNING));
	}

	public void addError(String message) {
		formValidationMessages.add(new ValidationMessage(message, Severity.ERROR));
	}

	public void addError(String property, String message) {
		fieldValidationMessages.put(property, new ValidationMessage(message, Severity.ERROR));
	}

	@Override
	public String toString() {
		return fieldValidationMessages.toString();
	}
}
