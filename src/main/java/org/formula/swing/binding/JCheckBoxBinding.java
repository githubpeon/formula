package org.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;

public class JCheckBoxBinding extends FormFieldBinding<JCheckBox> implements ActionListener {

	public JCheckBoxBinding(JCheckBox jCheckBox, FormBinder formBinder, PropertyMap propertyMap, String property, String optionsProperty, boolean required) {
		super(jCheckBox, formBinder, propertyMap, property, optionsProperty, required);
		jCheckBox.addActionListener(this);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setSelected((Boolean) getPropertyValue());
		}
	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		write(getView().isSelected());
	}

}
