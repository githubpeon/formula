package org.formula.swing.binding;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.formula.binding.FormBinder;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JColorChooserBinding extends SwingFormFieldBinding<JColorChooser> implements ChangeListener {

	public JColorChooserBinding(JColorChooser jColorChooser, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, Converter converter) {
		super(jColorChooser, formBinder, propertyMap, property, labelProperties, optionsProperty, required, converter);
		jColorChooser.getSelectionModel().addChangeListener(this);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setColor((Color) getPropertyValue());
		}
	}

	@Override
	protected void doReadLabel() {

	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		write(getView().getColor());
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
