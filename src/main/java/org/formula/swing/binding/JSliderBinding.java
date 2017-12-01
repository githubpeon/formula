package org.formula.swing.binding;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JSliderBinding extends FormFieldBinding<JSlider> implements ChangeListener {

	public JSliderBinding(JSlider jSlider, FormBinder formBinder, PropertyMap propertyMap, String property, String labelProperty, String optionsProperty, boolean required, Converter converter) {
		super(jSlider, formBinder, propertyMap, property, labelProperty, optionsProperty, required, converter);
		jSlider.addChangeListener(this);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setValue((Integer) getPropertyValue());
		}
	}

	@Override
	protected void doReadLabel() {

	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		write(getView().getValue());
	}

	@Override
	public void enable(boolean enable) {
		getView().setEnabled(enable);
	}

}
