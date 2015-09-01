package com.github.githubpeon.formula.validation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.githubpeon.formula.validation.ValidationMessage.Severity;

public class ValidationResult {

	private final Set<ValidationMessage> formValidationMessages = new HashSet<ValidationMessage>();
	private final Map<String, ValidationMessage> propertyValidationMessages = new HashMap<String, ValidationMessage>();

	public void addInfo(Object content) {
		formValidationMessages.add(new ValidationMessage(content, Severity.INFO));
	}

	public void addInfo(String property, Object content) {
		propertyValidationMessages.put(property, new ValidationMessage(content, Severity.INFO));
	}

	public void addWarning(Object content) {
		formValidationMessages.add(new ValidationMessage(content, Severity.WARNING));
	}

	public void addWarning(String property, Object content) {
		propertyValidationMessages.put(property, new ValidationMessage(content, Severity.WARNING));
	}

	public void addError(Object content) {
		formValidationMessages.add(new ValidationMessage(content, Severity.ERROR));
	}

	public void addError(String property, Object content) {
		propertyValidationMessages.put(property, new ValidationMessage(content, Severity.ERROR));
	}

	public Set<ValidationMessage> getFormValidationMessages() {
		return formValidationMessages;
	}

	public Map<String, ValidationMessage> getPropertyValidationMessages() {
		return propertyValidationMessages;
	}

	public ValidationMessage getPropertyValidationMessage(String property) {
		return propertyValidationMessages.get(property);
	}

	public boolean hasErrors() {
		for (ValidationMessage validationMessage : formValidationMessages) {
			if (validationMessage.getSeverity() == Severity.ERROR) {
				return true;
			}
		}
		for (ValidationMessage validationMessage : propertyValidationMessages.values()) {
			if (validationMessage.getSeverity() == Severity.ERROR) {
				return true;
			}
		}
		return false;
	}

	public boolean isMissingRequiredProperties() {
		for (ValidationMessage validationMessage : propertyValidationMessages.values()) {
			if (validationMessage.getContent() == ValidationError.REQUIRED_FIELD_MISSING) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return propertyValidationMessages.toString();
	}
}
