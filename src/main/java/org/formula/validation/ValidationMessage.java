package org.formula.validation;

public class ValidationMessage {

	public enum Severity {
		INFO,
		WARNING,
		ERROR;
	}

	private String property;
	private Object value;
	private Object view;
	private Object message;
	private Severity severity;

	public ValidationMessage(Object view, Object message, Severity severity) {
		this.view = view;
		this.message = message;
		this.severity = severity;
	}

	public ValidationMessage(String property, Object value, Object view, Object message, Severity severity) {
		this.property = property;
		this.value = value;
		this.view = view;
		this.message = message;
		this.severity = severity;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getView() {
		return view;
	}

	public void setView(Object view) {
		this.view = view;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
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
		return getMessage().toString();
	}
}
