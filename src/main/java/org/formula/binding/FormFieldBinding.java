package org.formula.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class FormFieldBinding<T extends Object> extends FormBinding<T> implements PropertyChangeListener {

	private String property;
	private String optionsProperty;
	private PropertyMap propertyMap;
	private boolean required;

	private boolean reading;
	private boolean writing;

	public FormFieldBinding(T view, FormBinder formBinder, PropertyMap propertyMap, String property, String optionsProperty, boolean required) {
		super(view, formBinder);
		this.propertyMap = propertyMap;
		this.property = property;
		this.optionsProperty = optionsProperty;
		this.required = required;

		this.propertyMap.put(property, null);
		propertyMap.addPropertyChangeListener(property, this);
		if (!optionsProperty.isEmpty()) {
			this.propertyMap.put(optionsProperty, null);
			propertyMap.addPropertyChangeListener(optionsProperty, this);
		}
	}

	public PropertyMap getPropertyMap() {
		return propertyMap;
	}

	public String getProperty() {
		return property;
	}

	public String getOptionsProperty() {
		return this.optionsProperty;
	}

	public Object getPropertyValue() {
		return this.propertyMap.get(this.property);
	}

	public Object getOptionsPropertyValue() {
		return this.propertyMap.get(this.optionsProperty);
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

	protected void readOptions() {
		if (!this.writing) {
			this.reading = true;
			doReadOptions();
			this.reading = false;
		}
	}

	protected abstract void doReadOptions();

	protected void write(Object value) {
		if (!this.reading) {
			this.writing = true;
			this.propertyMap.put(this.property, value);
			this.writing = false;
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(this.property)) {
			read();
		}
		if (e.getPropertyName().equals(this.optionsProperty)) {
			readOptions();
		}
	}

}
