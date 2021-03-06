package org.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

import org.formula.binding.FormBinder;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JRadioButtonBinding extends SwingFormFieldBinding<JRadioButton> implements ActionListener {

	public JRadioButtonBinding(JRadioButton jRadioButton, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, boolean errorIndicator, Converter converter) {
		super(jRadioButton, formBinder, propertyMap, property, labelProperties, optionsProperty, required, errorIndicator, converter);
		jRadioButton.addActionListener(this);
	}

	@Override
	protected void doRead() {
		Object value = getPropertyValue();
		if (value != null && String.valueOf(value).equals(getView().getActionCommand())) {
			getView().setSelected(true);
		}
	}

	@Override
	protected void doReadLabel() {

	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		write(getView().getActionCommand());
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
