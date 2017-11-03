package org.formula.swing.binding;

import javax.swing.JLabel;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;

public class JLabelBinding extends FormFieldBinding<JLabel> {

	public JLabelBinding(JLabel jList, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(jList, formBinder, propertyMap, property, required);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setText(getPropertyValue().toString());
		} else {
			getView().setText("");
		}
	}

}
