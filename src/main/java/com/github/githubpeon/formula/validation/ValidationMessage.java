package com.github.githubpeon.formula.validation;

public class ValidationMessage {

	public enum Severity {
		INFO,
		WARNING,
		ERROR;
	}

	private Object content;
	private Severity severity;

	public ValidationMessage(Object content, Severity severity) {
		this.content = content;
		this.severity = severity;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return getContent().toString();
	}
}
