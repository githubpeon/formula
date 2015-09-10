package com.github.githubpeon.formula.swing.binding;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.binding.PropertyMap;

public class JTextComponentBinding extends FormFieldBinding<JTextComponent> implements DocumentListener {

	public JTextComponentBinding(JTextComponent jTextComponent, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(jTextComponent, formBinder, propertyMap, property, required);
		jTextComponent.getDocument().addDocumentListener(this);
	}

	@Override
	protected void doRead() {
		getView().setText(String.valueOf(getPropertyValue()));
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
