package org.formula.binding;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyMap extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1917423877552589417L;
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	private final static Pattern propertyPattern = Pattern.compile(".*\\.\\d*");

	@Override
	public Object put(String property, Object newValue) {
		Object oldValue = super.put(property, newValue);
		propertyChangeSupport.firePropertyChange(property, oldValue, newValue);
		return oldValue;
	}

	@Override
	public boolean containsKey(Object key) {
		String property = (String) key;
		Matcher matcher = propertyPattern.matcher(property);
		if (!matcher.matches()) {
			property += ".0";
		}
		return super.containsKey(property);
	}

	@Override
	public Object get(Object key) {
		String property = (String) key;
		Matcher matcher = propertyPattern.matcher(property);
		if (!matcher.matches()) {
			property += ".0";
		}
		return super.get(property);
	}

	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public void addPropertyChangeListener(String property, PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(property, propertyChangeListener);
	}

}
