package org.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;

public class JComboBoxBinding extends FormFieldBinding<JComboBox> implements ActionListener {

	public JComboBoxBinding(JComboBox jComboBox, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(jComboBox, formBinder, propertyMap, property, required);
		jComboBox.addActionListener(this);
	}

	@Override
	protected void doRead() {
		getView().setSelectedItem(getPropertyValue());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		write(getView().getSelectedItem());
	}

}
