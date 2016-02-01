package com.github.githubpeon.formula.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class FormFieldBinding<T extends Object> extends FormBinding<T> implements PropertyChangeListener {

	private String property;
	private PropertyMap propertyMap;
	private boolean required;

	private boolean reading;
	private boolean writing;

	public FormFieldBinding(T view, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(view, formBinder);
		this.propertyMap = propertyMap;
		this.property = property;
		this.required = required;

		this.propertyMap.put(property, null);
		propertyMap.addPropertyChangeListener(property, this);
	}

	public PropertyMap getPropertyMap() {
		return propertyMap;
	}

	public String getProperty() {
		return property;
	}

	public Object getPropertyValue() {
		return this.propertyMap.get(this.property);
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isEmpty() {
		Object value = getPropertyMap().get(getProperty());
		if (value == null) {
			return true;
		} else if (value instanceof String) {
			String valueString = (String) value;
			return valueString.isEmpty();
		}
		return false;
	}

	protected void read() {
		if (!this.writing) {
			this.reading = true;
			doRead();
			this.reading = false;
		}
	}

	protected abstract void doRead();

	protected void write(Object value) {
		if (!this.reading) {
			this.writing = true;
			this.propertyMap.put(this.property, value);
			this.writing = false;
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		read();
	}

}
