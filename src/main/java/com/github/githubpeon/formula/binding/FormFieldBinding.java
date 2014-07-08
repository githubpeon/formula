package com.github.githubpeon.formula.binding;

public class FormFieldBinding extends FormBinding {

	private String property;
	private PropertyMap propertyMap;
	private boolean required;

	public FormFieldBinding(Object view, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
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

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	protected void setPropertyValue(Object value) {
		getPropertyMap().put(getProperty(), value);
	}
}
