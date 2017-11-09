package org.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;

public class JRadioButtonBinding extends FormFieldBinding<JRadioButton> implements ActionListener {

	public JRadioButtonBinding(JRadioButton jRadioButton, FormBinder formBinder, PropertyMap propertyMap, String property, String optionsProperty, boolean required) {
		super(jRadioButton, formBinder, propertyMap, property, optionsProperty, required);
		jRadioButton.addActionListener(this);
	}

	@Override
	protected void doRead() {
		Object value = getPropertyValue();
		if (value != null && value.equals(getView().getActionCommand())) {
			getView().setSelected(true);
		}
	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		write(getView().getActionCommand());
	}

}
