package org.formula.swing.binding;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public class JTextComponentBinding extends FormFieldBinding<JTextComponent> implements DocumentListener {

	public JTextComponentBinding(JTextComponent jTextComponent, FormBinder formBinder, PropertyMap propertyMap, String property, String optionsProperty, boolean required, Converter converter) {
		super(jTextComponent, formBinder, propertyMap, property, optionsProperty, required, converter);
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

}
