package com.github.githubpeon.formula.swing.binding;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.binding.PropertyMap;

public class JTextComponentBinding extends FormFieldBinding implements DocumentListener {

	public JTextComponentBinding(JTextComponent jTextComponent, FormBinder formBinder, PropertyMap propertyMap, String property, boolean required) {
		super(jTextComponent, formBinder, propertyMap, property, required);
		jTextComponent.getDocument().addDocumentListener(this);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		JTextComponent jTextComponent = (JTextComponent) getView();
		setPropertyValue(jTextComponent.getText());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		JTextComponent jTextComponent = (JTextComponent) getView();
		setPropertyValue(jTextComponent.getText());
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// We don't care about this.
	}

}
