package com.github.githubpeon.formula.swing.binding;

import javax.swing.JLabel;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.binding.PropertyMap;

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
