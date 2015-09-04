package com.github.githubpeon.formula.binding;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class PropertyMap extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1917423877552589417L;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	@Override
	public Object put(String property, Object newValue) {
		Object oldValue = super.put(property, newValue);
		propertyChangeSupport.firePropertyChange(property, oldValue, newValue);
		return oldValue;
	}

	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public void addPropertyChangeListener(String property, PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(property, propertyChangeListener);
	}

}
