package com.github.githubpeon.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.binding.PropertyMap;
import com.github.githubpeon.formula.converter.Converter;

public class JComboBoxBinding extends FormFieldBinding<JComboBox> implements ActionListener {

	public JComboBoxBinding(JComboBox jComboBox, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required, Converter converter) {
		super(jComboBox, formBinder, propertyMap, property, required, converter);
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
