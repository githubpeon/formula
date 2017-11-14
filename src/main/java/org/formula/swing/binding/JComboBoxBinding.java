package org.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
		getView().removeAllItems();
		Object optionsPropertyValue = getOptionsPropertyValue();
		if (optionsPropertyValue != null) {
			if (optionsPropertyValue.getClass().isArray()) {
				optionsPropertyValue = Arrays.asList((Object[]) optionsPropertyValue);
			}
			if (optionsPropertyValue instanceof Iterable) {
				for (Object object : (Iterable) optionsPropertyValue) {
					getView().addItem(object);
				}
			}
		}
		doRead();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		write(getView().getSelectedItem());
	}

}
