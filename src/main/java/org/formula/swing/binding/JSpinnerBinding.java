package org.formula.swing.binding;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.formula.binding.FormBinder;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JSpinnerBinding extends SwingFormFieldBinding<JSpinner> implements ChangeListener {

	public JSpinnerBinding(JSpinner jSpinner, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, Converter converter) {
		super(jSpinner, formBinder, propertyMap, property, labelProperties, optionsProperty, required, converter);
		jSpinner.addChangeListener(this);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			try {
				getView().setValue(getPropertyValue());
			} catch (IllegalArgumentException e) {
				// We've probably not initialized the spinner model yet.
			}
		}
	}

	@Override
	protected void doReadLabel() {

	}

	@Override
	protected void doReadOptions() {
		Object optionsPropertyValue = getOptionsPropertyValue();
		if (optionsPropertyValue != null && getView().getModel() instanceof SpinnerListModel) {
			SpinnerListModel spinnerListModel = (SpinnerListModel) getView().getModel();
			if (optionsPropertyValue.getClass().isArray()) {
				optionsPropertyValue = Arrays.asList((Object[]) optionsPropertyValue);
			}
			if (optionsPropertyValue instanceof Iterable) {
				ArrayList<Object> objects = new ArrayList<Object>();
				for (Object object : (Iterable) optionsPropertyValue) {
					objects.add(object);
				}
				spinnerListModel.setList(objects);
			}
		}
		doRead();
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		write(getView().getValue());
	}

	@Override
	public void enable(boolean enable) {
		getView().setEnabled(enable);
	}

	@Override
	public void focus() {
		getView().requestFocusInWindow();
	}

}
