package com.github.githubpeon.formula.swing.binding;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.binding.PropertyMap;

public class JSpinnerBinding extends FormFieldBinding<JSpinner> implements ChangeListener {

	public JSpinnerBinding(JSpinner jSpinner, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(jSpinner, formBinder, propertyMap, property, required);
		jSpinner.addChangeListener(this);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setValue(getPropertyValue());
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		write(getView().getValue());
	}

}
