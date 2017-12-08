package org.formula.swing.binding;

import javax.swing.JProgressBar;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JProgressBarBinding extends FormFieldBinding<JProgressBar> {

	public JProgressBarBinding(JProgressBar jProgressBar, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, Converter converter) {
		super(jProgressBar, formBinder, propertyMap, property, labelProperties, optionsProperty, required, converter);
	}

	@Override
	protected void doRead() {
		getView().setValue((Integer) getPropertyValue());
	}

	@Override
	protected void doReadLabel() {

	}

	@Override
	protected void doReadOptions() {

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
