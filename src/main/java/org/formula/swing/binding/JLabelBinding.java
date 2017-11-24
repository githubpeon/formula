package org.formula.swing.binding;

import javax.swing.JLabel;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JLabelBinding extends FormFieldBinding<JLabel> {

	public JLabelBinding(JLabel jList, FormBinder formBinder, PropertyMap propertyMap, String property, String optionsProperty, boolean required, Converter converter) {
		super(jList, formBinder, propertyMap, property, optionsProperty, required, converter);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setText(getPropertyValue().toString());
		} else {
			getView().setText("");
		}
	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	protected void write(Object value) {
		// We never want to write from labels.
	}

	@Override
	public void enable(boolean enable) {
		getView().setEnabled(enable);
	}

}
