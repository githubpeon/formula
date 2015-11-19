package com.github.githubpeon.formula.swing.binding;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.binding.PropertyMap;

public class JSliderBinding extends FormFieldBinding<JSlider> implements ChangeListener {

	public JSliderBinding(JSlider jSlider, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(jSlider, formBinder, propertyMap, property, required);
		jSlider.addChangeListener(this);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setValue((Integer) getPropertyValue());
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		write(getView().getValue());
	}

}
