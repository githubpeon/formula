package com.github.githubpeon.formula.validation;

public class ValidationMessage {

	public enum Severity {
		INFO,
		WARNING,
		ERROR;
	}

	private String message;
	private Severity severity;

	public ValidationMessage(String message, Severity severity) {
		this.message = message;
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return getMessage();
	}
}
