package com.github.githubpeon.formula.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.github.githubpeon.formula.converter.Converter;

public abstract class FormFieldBinding<T extends Object> extends FormBinding<T> implements PropertyChangeListener {

	private String property;
	private PropertyMap propertyMap;
	private boolean required;
	private Converter converter;

	private boolean reading;
	private boolean writing;

	public FormFieldBinding(T view, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required, Converter converter) {
		super(view, formBinder);
		this.propertyMap = propertyMap;
		this.property = property;
		this.required = required;
		this.converter = converter;

		this.propertyMap.put(property, null);
		propertyMap.addPropertyChangeListener(property, this);
	}

	public PropertyMap getPropertyMap() {
		return propertyMap;
	}

	public String getProperty() {
		return property;
	}

	protected Object getPropertyValue() {
		return this.converter.convertFrom(this.propertyMap.get(this.property));
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Converter getConverter() {
		return converter;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
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
