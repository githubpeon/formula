package org.formula.swing.binding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JCheckBoxBinding extends FormFieldBinding<JCheckBox> implements ActionListener {

	private String pattern = "";

	public JCheckBoxBinding(JCheckBox jCheckBox, FormBinder formBinder, PropertyMap propertyMap, String property, String labelProperty, String optionsProperty, boolean required, Converter converter) {
		super(jCheckBox, formBinder, propertyMap, property, labelProperty, optionsProperty, required, converter);
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
		getView().setText(String.format(this.pattern, getLabelPropertyValue()));
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

}
