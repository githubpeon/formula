package com.github.githubpeon.formula.swing.binding;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.binding.PropertyMap;

public class JColorChooserBinding extends FormFieldBinding<JColorChooser> implements ChangeListener {

	public JColorChooserBinding(JColorChooser jColorChooser, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(jColorChooser, formBinder, propertyMap, property, required);
		jColorChooser.getSelectionModel().addChangeListener(this);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setColor((Color) getPropertyValue());
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		write(getView().getColor());
	}

}
