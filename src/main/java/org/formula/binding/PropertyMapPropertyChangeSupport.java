package org.formula.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;

/**
 * Fires changes for lists without checking for equals. You can update properties on an object in a table
 * and want the table to refresh a cell even if the object equals itself even after the property has been changed.
 */
public class PropertyMapPropertyChangeSupport extends PropertyChangeSupport {

	private Object sourceBean;
	private static final long serialVersionUID = -2751451530835550708L;

	public PropertyMapPropertyChangeSupport(Object sourceBean) {
		super(sourceBean);
		this.sourceBean = sourceBean;
	}

	@Override
	public void firePropertyChange(PropertyChangeEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();
		if (oldValue == null || newValue == null || newValue instanceof Collection || !oldValue.equals(newValue)) {
			String name = event.getPropertyName();

			PropertyChangeListener[] common = getPropertyChangeListeners();
			PropertyChangeListener[] named = (name != null
					? getPropertyChangeListeners(name)
					: null);

			if (common != null) {
				for (PropertyChangeListener listener : common) {
					listener.propertyChange(event);
				}
			}

			if (named != null) {
				for (PropertyChangeListener listener : named) {
					listener.propertyChange(event);
				}
			}
		}
	}

	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		if (oldValue == null || newValue == null || newValue instanceof Collection || !oldValue.equals(newValue)) {
			firePropertyChange(new PropertyChangeEvent(this.sourceBean, propertyName, oldValue, newValue));
		}
	}

}
