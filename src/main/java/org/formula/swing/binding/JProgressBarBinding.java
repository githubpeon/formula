package org.formula.swing.binding;

import javax.swing.JProgressBar;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;

public class JProgressBarBinding extends FormFieldBinding<JProgressBar> {

	public JProgressBarBinding(JProgressBar jProgressBar, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(jProgressBar, formBinder, propertyMap, property, required);
	}

	@Override
	protected void doRead() {
		getView().setValue((Integer) getPropertyValue());
	}

}
