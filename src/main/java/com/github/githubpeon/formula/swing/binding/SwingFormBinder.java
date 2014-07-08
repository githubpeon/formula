package com.github.githubpeon.formula.swing.binding;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import com.github.githubpeon.formula.annotation.CommitsForm;
import com.github.githubpeon.formula.annotation.Form;
import com.github.githubpeon.formula.annotation.FormField;
import com.github.githubpeon.formula.binding.AbstractFormBinder;
import com.github.githubpeon.formula.binding.BindingException;
import com.github.githubpeon.formula.binding.FormBinding;
import com.github.githubpeon.formula.binding.FormFieldBinding;
import com.github.githubpeon.formula.event.FormEvent;
import com.github.githubpeon.formula.event.FormEvent.FormEventId;

public class SwingFormBinder extends AbstractFormBinder<Container> implements FocusListener {

	public SwingFormBinder() {
		super();
	}

	public SwingFormBinder(Object model) {
		super(model);
	}

	@Override
	protected Set<FormBinding> bindFormFields(Container form) {
		Set<FormBinding> components = bindContainer(form);
		return components;
	}

	@Override
	protected FormFieldBinding bindFormField(Object formField, String property, boolean required) {
		if (formField instanceof JComponent) {
			((JComponent) formField).addFocusListener(this);
		}
		if (formField instanceof JTextComponent) {
			return new JTextComponentBinding((JTextComponent) formField, this, getPropertyMap(), property, required);
		}
		throw new BindingException("Binding for class " + formField.getClass().getName() + " is not implemented in binder " + getClass().getName());
	}

	private Set<FormBinding> bindContainer(Container container) {
		Set<FormBinding> formBindings = new HashSet<FormBinding>();
		for (Field field : container.getClass().getDeclaredFields()) {
			try {
				if (field.isAnnotationPresent(FormField.class)) {
					field.setAccessible(true);
					JComponent formField = (JComponent) field.get(container);
					FormField formFieldAnnotation = field.getAnnotation(FormField.class);

					String property = formFieldAnnotation.value();
					boolean required = formFieldAnnotation.required();
					FormFieldBinding formFieldBinding = bindFormField(formField, property, required);
					formBindings.add(formFieldBinding);
				} else if (field.isAnnotationPresent(CommitsForm.class)) {
					field.setAccessible(true);
					try {
						JButton jButton = (JButton) field.get(container);
						formBindings.add(new JButtonCommitBinding(jButton, this));
					} catch (ClassCastException e) {
						throw new BindingException("@CommitsForm annotation is only valid for JButton fields in binder " + getClass().getName() + ".");
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
		fireFormEvent(new FormEvent(this, FormEventId.FORM_FIELD_FOCUS_GAINED, e.getSource()));
	}

	@Override
	public void focusLost(FocusEvent e) {
		fireFormEvent(new FormEvent(this, FormEventId.FORM_FIELD_FOCUS_LOST, e.getSource()));
	}

}
