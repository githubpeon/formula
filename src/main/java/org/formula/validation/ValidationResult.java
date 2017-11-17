package org.formula.validation;

import java.util.ArrayList;
import java.util.List;

import org.formula.validation.ValidationMessage.Severity;

public class ValidationResult {

	private final List<ValidationMessage> formValidationMessages = new ArrayList<ValidationMessage>();
	private final List<ValidationMessage> propertyValidationMessages = new ArrayList<ValidationMessage>();
	private boolean allowCommit = true;

	public void addInfo(Object view, Object message) {
		formValidationMessages.add(new ValidationMessage(view, message, Severity.INFO));
	}

	public void addInfo(String property, Object value, Object view, Object message) {
		propertyValidationMessages.add(new ValidationMessage(property, value, view, message, Severity.INFO));
	}

	public void addWarning(Object view, Object message) {
		formValidationMessages.add(new ValidationMessage(view, message, Severity.WARNING));
	}

	public void addWarning(String property, Object value, Object view, Object message) {
		propertyValidationMessages.add(new ValidationMessage(property, value, view, message, Severity.WARNING));
	}

	public void addError(Object view, Object message) {
		formValidationMessages.add(new ValidationMessage(view, message, Severity.ERROR));
	}

	public void addError(String property, Object value, Object view, Object message) {
		propertyValidationMessages.add(new ValidationMessage(property, value, view, message, Severity.ERROR));
	}

	public List<ValidationMessage> getFormValidationMessages() {
		return formValidationMessages;
	}

	public List<ValidationMessage> getPropertyValidationMessages() {
		return propertyValidationMessages;
	}

	public ValidationMessage getPropertyValidationMessage(String property) {
		for (ValidationMessage validationMessage : getPropertyValidationMessages()) {
			if (validationMessage.getProperty().equals(property)) {
				return validationMessage;
			}
		}
		return null;
	}

	public boolean hasFormErrors() {
		for (ValidationMessage validationMessage : formValidationMessages) {
			if (validationMessage.getSeverity() == Severity.ERROR) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPropertyErrors() {
		for (ValidationMessage validationMessage : propertyValidationMessages) {
			if (validationMessage.getSeverity() == Severity.ERROR) {
				return true;
			}
		}
		return false;
	}

	public boolean hasErrors() {
		return hasFormErrors() || hasPropertyErrors();
	}

	public boolean isMissingRequiredProperties() {
		for (ValidationMessage validationMessage : propertyValidationMessages) {
			if (validationMessage.getMessage() == ValidationError.REQUIRED_FIELD_MISSING) {
				return true;
			}
		}
		return false;
	}

	public boolean isAllowCommit() {
		return allowCommit;
	}

	public void setAllowCommit(boolean allowCommit) {
		this.allowCommit = allowCommit;
	}

	@Override
	public String toString() {
		return formValidationMessages.toString() + propertyValidationMessages.toString();
	}
}
