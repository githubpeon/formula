package org.formula.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.formula.converter.Converter;

public abstract class FormFieldBinding<T extends Object> extends FormBinding<T> implements PropertyChangeListener {

	private String property;
	private String[] labelProperties;
	private String optionsProperty;
	private PropertyMap propertyMap;
	private boolean required;
	private boolean errorIndicator;
	private Converter converter;

	private boolean reading;
	private boolean writing;

	public FormFieldBinding(T view, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, boolean errorIndicator, Converter converter) {
		super(view, formBinder);
		this.propertyMap = propertyMap;
		this.property = property;
		this.labelProperties = labelProperties;
		this.optionsProperty = optionsProperty;
		this.required = required;
		this.errorIndicator = errorIndicator;
		this.converter = converter;

		if (!property.isEmpty()) {
			this.propertyMap.put(property, null);
			propertyMap.addPropertyChangeListener(property, this);
		}

		for (String labelProperty : labelProperties) {
			this.propertyMap.put(labelProperty, null);
			propertyMap.addPropertyChangeListener(labelProperty, this);
		}
		if (!optionsProperty.isEmpty()) {
			this.propertyMap.put(optionsProperty, null);
			propertyMap.addPropertyChangeListener(optionsProperty, this);
		}
	}

	public abstract void enable(boolean enable);

	public abstract void show(boolean visible);

	public abstract void focus();

	public PropertyMap getPropertyMap() {
		return propertyMap;
	}

	public String getProperty() {
		return property;
	}

	public String[] getLabelProperties() {
		return this.labelProperties;
	}

	public String getOptionsProperty() {
		return this.optionsProperty;
	}

	public Object getPropertyValue() {
		return getPropertyValue(this.property);
	}

	@SuppressWarnings("unchecked")
	public Object getPropertyValue(String property) {
		return this.converter.convertFrom(this.propertyMap.get(property));
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

	public boolean isErrorIndicator() {
        return errorIndicator;
    }

    public void setErrorIndicator(boolean errorIndicator) {
        this.errorIndicator = errorIndicator;
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

	protected void readLabel() {
		doReadLabel();
	}

	protected abstract void doReadLabel();

	@SuppressWarnings("unchecked")
	protected void write(Object value) {
		if (!this.reading) {
			this.writing = true;
			this.propertyMap.put(this.property, this.converter.convertTo(value));
			this.writing = false;
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals(this.property)) {
			read();
		}
		for (String labelProperty : this.labelProperties) {
			if (e.getPropertyName().equals(labelProperty)) {
				readLabel();
			}
		}
		if (e.getPropertyName().equals(this.optionsProperty)) {
			readOptions();
		}
	}

}
