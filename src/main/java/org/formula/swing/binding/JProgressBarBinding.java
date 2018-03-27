package org.formula.swing.binding;

import java.beans.PropertyChangeEvent;

import javax.swing.JProgressBar;

import org.formula.binding.FormBinder;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JProgressBarBinding extends SwingFormFieldBinding<JProgressBar> {

    private String maxProperty;

	public JProgressBarBinding(JProgressBar jProgressBar, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, String maxProperty, boolean required, boolean errorIndicator, Converter converter) {
		super(jProgressBar, formBinder, propertyMap, property, labelProperties, optionsProperty, required, errorIndicator, converter);
		this.maxProperty = maxProperty;

        if (!maxProperty.isEmpty()) {
            getPropertyMap().put(maxProperty, null);
            getPropertyMap().addPropertyChangeListener(maxProperty, this);
        }
	}

	@Override
	protected void doRead() {
		getView().setValue((Integer) getPropertyValue());
	}

	@Override
	protected void doReadLabel() {

	}

	@Override
	protected void doReadOptions() {

	}

	protected void doReadMax() {
	    getView().setMaximum((Integer)getPropertyValue(this.maxProperty));
	}

	@Override
	public void enable(boolean enable) {
		getView().setEnabled(enable);
	}

	@Override
	public void focus() {
		getView().requestFocusInWindow();
	}

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        super.propertyChange(e);
        if (e.getPropertyName().equals(this.maxProperty)) {
            doReadMax();
        }
    }
}
