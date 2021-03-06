package org.formula.swing.binding;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import org.formula.binding.FormBinder;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JTextComponentBinding extends SwingFormFieldBinding<JTextComponent> implements DocumentListener {

	public JTextComponentBinding(JTextComponent jTextComponent, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, boolean errorIndicator, Converter converter) {
		super(jTextComponent, formBinder, propertyMap, property, labelProperties, optionsProperty, required, errorIndicator, converter);
		jTextComponent.getDocument().addDocumentListener(this);
	}

	@Override
	protected void doRead() {
		if (getPropertyValue() != null) {
			getView().setText(String.valueOf(getPropertyValue()));
		} else {
			getView().setText("");
		}
	}

	@Override
	protected void doReadLabel() {

	}

	@Override
	protected void doReadOptions() {

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		write(getView().getText());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		write(getView().getText());
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// We don't care about this.
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
