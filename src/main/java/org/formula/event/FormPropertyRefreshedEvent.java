package org.formula.event;

import org.formula.binding.FormBinder;

public class FormPropertyRefreshedEvent extends FormEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;
	private String property;

	public FormPropertyRefreshedEvent(FormBinder source, String property) {
		super(source);
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public boolean isProperty(String property) {
		return this.property.equals(property);
	}

	public boolean isProperty(String property, int index) {
		property = property.replaceFirst("#", "#" + index);
		return this.property.equals(property);
	}

}
