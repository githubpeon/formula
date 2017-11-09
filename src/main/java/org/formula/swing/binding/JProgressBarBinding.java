package org.formula.swing.binding;

import javax.swing.JProgressBar;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;

public class JProgressBarBinding extends FormFieldBinding<JProgressBar> {

	public JProgressBarBinding(JProgressBar jProgressBar, FormBinder formBinder, PropertyMap propertyMap, String property, String optionsProperty, boolean required) {
		super(jProgressBar, formBinder, propertyMap, property, optionsProperty, required);
	}

	@Override
	protected void doRead() {
		getView().setValue((Integer) getPropertyValue());
	}

	@Override
	protected void doReadOptions() {

	}

}
