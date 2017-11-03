package org.formula.event;

import org.formula.binding.FormBinder;

public class FormPropertyEditedEvent extends FormEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;
	private String property;
	private Object oldValue;
	private Object newValue;

	public FormPropertyEditedEvent(FormBinder source, String property, Object oldValue, Object newValue) {
		super(source);
		this.property = property;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

}
