package com.github.githubpeon.formula.swing.binding;

import javax.swing.JProgressBar;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.binding.PropertyMap;

public class JProgressBarBinding extends FormFieldBinding<JProgressBar> {

	public JProgressBarBinding(JProgressBar jProgressBar, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(jProgressBar, formBinder, propertyMap, property, required);
	}

	@Override
	protected void doRead() {
		getView().setValue((Integer) getPropertyValue());
	}

}
