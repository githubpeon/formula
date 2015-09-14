package com.github.githubpeon.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.binding.PropertyMap;
import com.github.githubpeon.formula.converter.Converter;

public class JCheckBoxBinding extends FormFieldBinding<JCheckBox> implements ActionListener {

	public JCheckBoxBinding(JCheckBox jCheckBox, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required, Converter converter) {
		super(jCheckBox, formBinder, propertyMap, property, required, converter);
		jCheckBox.addActionListener(this);
	}

	@Override
	protected void doRead() {
		getView().setSelected((Boolean) getPropertyValue());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		write(getView().isSelected());
	}

}
