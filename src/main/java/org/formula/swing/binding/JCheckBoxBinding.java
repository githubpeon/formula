package org.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JCheckBoxBinding extends FormFieldBinding<JCheckBox> implements ActionListener {

	private String pattern = null;

	public JCheckBoxBinding(JCheckBox jCheckBox, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, Converter converter) {
		super(jCheckBox, formBinder, propertyMap, property, labelProperties, optionsProperty, required, converter);
		jCheckBox.addActionListener(this);
		this.pattern = jCheckBox.getText();
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setSelected((Boolean) getPropertyValue());
		}
	}

	@Override
	protected void doReadLabel() {
		if (this.pattern != null) {
			String[] labelProperties = getLabelProperties();
			if (labelProperties != null && labelProperties.length > 0) {
				Object[] labelPropertyValues = new Object[getLabelProperties().length];
				for (int i = 0; i < labelProperties.length; ++i) {
					labelPropertyValues[i] = getPropertyValue(labelProperties[i]);
					if (labelPropertyValues[i] == null) {
						labelPropertyValues[i] = "";
					}
				}
				getView().setText(String.format(this.pattern, labelPropertyValues));
			} else {
				getView().setText(this.pattern);
			}
		}
	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		write(getView().isSelected());
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
