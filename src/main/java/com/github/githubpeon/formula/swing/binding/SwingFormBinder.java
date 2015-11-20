package com.github.githubpeon.formula.swing.binding;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.text.JTextComponent;

import com.github.githubpeon.formula.annotation.CommitForm;
import com.github.githubpeon.formula.annotation.Form;
import com.github.githubpeon.formula.annotation.FormField;
import com.github.githubpeon.formula.annotation.RollbackForm;
import com.github.githubpeon.formula.binding.AbstractFormBinder;
import com.github.githubpeon.formula.binding.BindingException;
import com.github.githubpeon.formula.binding.FormBinding;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.event.FormFieldFocusGainedEvent;
import com.github.githubpeon.formula.event.FormFieldFocusLostEvent;

public class SwingFormBinder extends AbstractFormBinder<Container> implements FocusListener {

	public SwingFormBinder() {
		super();
	}

	public SwingFormBinder(Object model) {
		super(model);
	}

	@Override
	protected Set<FormBinding> bindFormFields(Container form) {
		Set<FormBinding> formBindings = bindContainer(form);
		return formBindings;
	}

	@Override
	protected FormFieldBinding bindFormField(Object formField, String property, boolean required) {
		if (formField instanceof JComponent) {
			((JComponent) formField).addFocusListener(this);
		}
		if (formField instanceof JLabel) {
			return new JLabelBinding((JLabel) formField, this, getPropertyMap(), property, required);
		} else if (formField instanceof JTextComponent) {
			return new JTextComponentBinding((JTextComponent) formField, this, getPropertyMap(), property, required);
		} else if (formField instanceof JComboBox) {
			return new JComboBoxBinding((JComboBox) formField, this, getPropertyMap(), property, required);
		} else if (formField instanceof JCheckBox) {
			return new JCheckBoxBinding((JCheckBox) formField, this, getPropertyMap(), property, required);
		} else if (formField instanceof JRadioButton) {
			return new JRadioButtonBinding((JRadioButton) formField, this, getPropertyMap(), property, required);
		} else if (formField instanceof JList) {
			return new JListBinding((JList) formField, this, getPropertyMap(), property, required);
		} else if (formField instanceof JSlider) {
			return new JSliderBinding((JSlider) formField, this, getPropertyMap(), property, required);
		} else if (formField instanceof JSpinner) {
			return new JSpinnerBinding((JSpinner) formField, this, getPropertyMap(), property, required);
		} else if (formField instanceof JProgressBar) {
			return new JProgressBarBinding((JProgressBar) formField, this, getPropertyMap(), property, required);
		}
		throw new BindingException("Binding for class " + formField.getClass().getName() + " is not implemented in binder " + getClass().getName());
	}

	private Set<FormBinding> bindContainer(Container container) {
		Set<FormBinding> formBindings = new HashSet<FormBinding>();
		for (Field field : container.getClass().getDeclaredFields()) {
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

	@Override
	public void focusGained(FocusEvent e) {
		fireFormFieldEvent(new FormFieldFocusGainedEvent(this, e.getSource()));
	}

	@Override
	public void focusLost(FocusEvent e) {
		fireFormFieldEvent(new FormFieldFocusLostEvent(this, e.getSource()));
	}

}
