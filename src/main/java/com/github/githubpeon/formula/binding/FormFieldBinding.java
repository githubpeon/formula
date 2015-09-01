package com.github.githubpeon.formula.binding;

public abstract class FormFieldBinding<T extends Object> extends FormBinding<T> {

	private String property;
	private PropertyMap propertyMap;
	private boolean required;

	public FormFieldBinding(T view, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(view, formBinder);
		this.propertyMap = propertyMap;
		this.property = property;
		this.required = required;
	}

	public PropertyMap getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(PropertyMap propertyMap) {
		this.propertyMap = propertyMap;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getPropertyValue() {
		return this.propertyMap.get(this.property);
	}

	public void setPropertyValue(Object value) {
		this.propertyMap.put(this.property, value);
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

	public abstract void read();

	public void write(Object value) {
		setPropertyValue(value);
	}
}
