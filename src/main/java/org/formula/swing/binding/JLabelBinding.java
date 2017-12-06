package org.formula.swing.binding;

import javax.swing.JLabel;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JLabelBinding extends FormFieldBinding<JLabel> {

	private String pattern = "";

	public JLabelBinding(JLabel jLabel, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, Converter converter) {
		super(jLabel, formBinder, propertyMap, property, labelProperties, optionsProperty, required, converter);
		this.pattern = jLabel.getText();
	}

	@Override
	protected void doRead() {
        if (getPropertyValue() != null) {
            getView().setText(getPropertyValue().toString());
        } else {
            getView().setText("");
        }
	}

	@Override
	protected void doReadLabel() {
	    String [] labelProperties = getLabelProperties();
	    if(labelProperties != null && labelProperties.length > 0) {
	        Object[] labelPropertyValues = new Object[getLabelProperties().length];
	        for(int i = 0; i < labelProperties.length; ++i) {
	            labelPropertyValues[i] = getPropertyValue(labelProperties[i]);
	        }
	        getView().setText(String.format(this.pattern, labelPropertyValues));
	    } else {
	        getView().setText(this.pattern);
	    }
	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	protected void write(Object value) {
		// We never want to write from labels.
	}

	@Override
	public void enable(boolean enable) {
		getView().setEnabled(enable);
	}

}
