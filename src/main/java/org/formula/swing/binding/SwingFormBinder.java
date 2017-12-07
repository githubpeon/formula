package org.formula.swing.binding;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import org.formula.annotation.CommitForm;
import org.formula.annotation.Form;
import org.formula.annotation.FormField;
import org.formula.annotation.RollbackForm;
import org.formula.binding.AbstractFormBinder;
import org.formula.binding.BindingException;
import org.formula.binding.FormBinding;
import org.formula.binding.FormFieldBinding;
import org.formula.converter.Converter;
import org.formula.event.FormFieldFocusGainedEvent;
import org.formula.event.FormFieldFocusLostEvent;
import org.formula.validation.FieldValidator;
import org.formula.validation.ValidationMessage;
import org.formula.validation.ValidationResult;

public class SwingFormBinder extends AbstractFormBinder implements FocusListener {

	public SwingFormBinder() {
		super();
	}

	public SwingFormBinder(Container form) {
		super(form);
	}

	@Override
	protected Set<FormBinding> bindFormFields() {
		Set<FormBinding> formBindings = bindContainer((Container) getForm());
		return formBindings;
	}

	@Override
	protected FormFieldBinding bindFormField(Object formField, String property, String[] labelProperties, String optionsProperty, boolean required, Converter converter) {
		if (formField instanceof JComponent) {
			((JComponent) formField).addFocusListener(this);
		}
		if (formField instanceof JLabel) {
			return new JLabelBinding((JLabel) formField, this, getPropertyMap(), property, labelProperties, optionsProperty, required, converter);
		} else if (formField instanceof JTextComponent) {
			return new JTextComponentBinding((JTextComponent) formField, this, getPropertyMap(), property, labelProperties, optionsProperty, required, converter);
		} else if (formField instanceof JComboBox) {
			return new JComboBoxBinding((JComboBox) formField, this, getPropertyMap(), property, labelProperties, optionsProperty, required, converter);
		} else if (formField instanceof JCheckBox) {
			return new JCheckBoxBinding((JCheckBox) formField, this, getPropertyMap(), property, labelProperties, optionsProperty, required, converter);
		} else if (formField instanceof JRadioButton) {
			return new JRadioButtonBinding((JRadioButton) formField, this, getPropertyMap(), property, labelProperties, optionsProperty, required, converter);
		} else if (formField instanceof JList) {
			return new JListBinding((JList) formField, this, getPropertyMap(), property, labelProperties, optionsProperty, required, converter);
		} else if (formField instanceof JSlider) {
			return new JSliderBinding((JSlider) formField, this, getPropertyMap(), property, labelProperties, optionsProperty, required, converter);
		} else if (formField instanceof JSpinner) {
			return new JSpinnerBinding((JSpinner) formField, this, getPropertyMap(), property, labelProperties, optionsProperty, required, converter);
		} else if (formField instanceof JProgressBar) {
			return new JProgressBarBinding((JProgressBar) formField, this, getPropertyMap(), property, labelProperties, optionsProperty, required, converter);
		} else if (formField instanceof JColorChooser) {
			return new JColorChooserBinding((JColorChooser) formField, this, getPropertyMap(), property, labelProperties, optionsProperty, required, converter);
		}
		throw new BindingException("Binding for class " + formField.getClass().getName() + " is not implemented in binder " + getClass().getName());
	}

	private Set<FormBinding> bindContainer(Container container) {
		Set<FormBinding> formBindings = new HashSet<FormBinding>();
		for (Field field : getAllFields(container)) {
			try {
				if (field.isAnnotationPresent(FormField.class)) {
					FormFieldBinding formFieldBinding = bindFormField(field, container);
					formBindings.add(formFieldBinding);
				} else if (field.isAnnotationPresent(CommitForm.class)) {
					field.setAccessible(true);
					try {
						JButton jButton = (JButton) field.get(container);
						formBindings.add(new JButtonCommitBinding(jButton, this));
					} catch (ClassCastException e) {
						throw new BindingException("@CommitForm annotation is only valid for JButton fields in binder " + getClass().getName() + ".");
					}
				} else if (field.isAnnotationPresent(RollbackForm.class)) {
					field.setAccessible(true);
					try {
						JButton jButton = (JButton) field.get(container);
						formBindings.add(new JButtonRollbackBinding(jButton, this));
					} catch (ClassCastException e) {
						throw new BindingException("@RollbackForm annotation is only valid for JButton fields in binder " + getClass().getName() + ".");
					}
				}
			} catch (IllegalAccessException e) {
				throw new BindingException("IllegalAccessException when binding field " + field.getName() + " in container " + container.getClass().getName(), e);
			}
		}
		for (Component component : container.getComponents()) {
			if (!component.getClass().isAnnotationPresent(Form.class) && component instanceof Container) {
				formBindings.addAll(bindContainer((Container) component));
			}
		}
		return formBindings;
	}

	private List<Field> getAllFields(Container container) {
		List<Field> fields = new ArrayList<Field>();
		for (Class<?> clazz = container.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		return fields;
	}

	@Override
	protected ValidationResult validate() {
		ValidationResult validationResult = super.validate();
		Color color = (Color) UIManager.getLookAndFeelDefaults().get("Label.foreground");

		for (FieldValidator fieldValidator : getValidator().getFieldValidators()) {
			Object view = fieldValidator.getFormFieldBinding().getView();
			if (view instanceof JComponent) {
				Object labeledBy = ((JComponent) view).getClientProperty("labeledBy");
				if (labeledBy instanceof JComponent) {
					((JComponent) labeledBy).setForeground(color);
				}
			}
		}
		if (validationResult != null) {
			for (ValidationMessage validationMessage : validationResult.getPropertyValidationMessages()) {
				for (FieldValidator fieldValidator : getValidator().getFieldValidators(validationMessage.getProperty())) {
					Object view = fieldValidator.getFormFieldBinding().getView();
					if (view instanceof JComponent) {
						Object labeledBy = ((JComponent) view).getClientProperty("labeledBy");
						if (labeledBy != null) {
							((JComponent) labeledBy).setForeground(Color.RED);
						}
					}
				}
			}
		}
		return validationResult;
	}

	@Override
	public void focusGained(FocusEvent e) {
		fireFormFieldEvent(new FormFieldFocusGainedEvent(this, e.getSource()));
	}

	@Override
	public void focusLost(FocusEvent e) {
		fireFormFieldEvent(new FormFieldFocusLostEvent(this, e.getSource()));
	}

}
