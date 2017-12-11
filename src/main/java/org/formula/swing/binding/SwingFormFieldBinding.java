package org.formula.swing.binding;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.UIManager;

import org.formula.binding.FormBinder;
import org.formula.binding.FormFieldBinding;
import org.formula.binding.PropertyMap;
import org.formula.converter.Converter;

public abstract class SwingFormFieldBinding<T extends JComponent> extends FormFieldBinding<T> {

	public SwingFormFieldBinding(T view, FormBinder formBinder, PropertyMap propertyMap, String property, String[] labelProperties, String optionsProperty, boolean required, Converter converter) {
		super(view, formBinder, propertyMap, property, labelProperties, optionsProperty, required, converter);
	}

	public void show(boolean visible) {
		getView().setVisible(visible);
		Object labeledBy = getView().getClientProperty("labeledBy");
		if (labeledBy instanceof JComponent) {
			((JComponent) labeledBy).setVisible(visible);
		}
	}

	protected void setValidationError(boolean hasError) {
		Object labeledBy = getView().getClientProperty("labeledBy");
		if (labeledBy instanceof JComponent) {
			Color color = (hasError ? Color.RED : (Color) UIManager.getLookAndFeelDefaults().get("Label.foreground"));
			((JComponent) labeledBy).setForeground(color);
		}
	}

}
