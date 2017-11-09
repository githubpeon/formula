package org.formula.swing.binding;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;

public class JColorChooserBinding extends FormFieldBinding<JColorChooser> implements ChangeListener {

	public JColorChooserBinding(JColorChooser jColorChooser, FormBinder formBinder, PropertyMap propertyMap, String property, String optionsProperty, boolean required) {
		super(jColorChooser, formBinder, propertyMap, property, optionsProperty, required);
		jColorChooser.getSelectionModel().addChangeListener(this);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setColor((Color) getPropertyValue());
		}
	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		write(getView().getColor());
	}

}
