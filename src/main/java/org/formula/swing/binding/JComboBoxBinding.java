package org.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JComboBox;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;

public class JComboBoxBinding extends FormFieldBinding<JComboBox> implements ActionListener {

	public JComboBoxBinding(JComboBox jComboBox, FormBinder formBinder, PropertyMap propertyMap, String property, String optionsProperty, boolean required) {
		super(jComboBox, formBinder, propertyMap, property, optionsProperty, required);
		jComboBox.addActionListener(this);
	}

	@Override
	protected void doRead() {
		getView().setSelectedItem(getPropertyValue());
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doReadOptions() {
		Object optionsPropertyValue = getOptionsPropertyValue();
		if (optionsPropertyValue != null && optionsPropertyValue instanceof Collection) {
			for (Object object : (Collection) optionsPropertyValue) {
				getView().addItem(object);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		write(getView().getSelectedItem());
	}

}
