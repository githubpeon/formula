package com.github.githubpeon.formula.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import com.github.githubpeon.formula.annotation.Form;
import com.github.githubpeon.formula.event.FormCommitValidationEvent;
import com.github.githubpeon.formula.event.FormCommittedEvent;
import com.github.githubpeon.formula.event.FormEditValidationEvent;
import com.github.githubpeon.formula.event.FormEvent;
import com.github.githubpeon.formula.event.FormFieldFocusGainedEvent;
import com.github.githubpeon.formula.event.FormFieldFocusLostEvent;
import com.github.githubpeon.formula.event.FormFieldListener;
import com.github.githubpeon.formula.event.FormListener;
import com.github.githubpeon.formula.event.FormPropertyEditedEvent;
import com.github.githubpeon.formula.event.FormValidationListener;
import com.github.githubpeon.formula.validation.ValidationResult;
import com.github.githubpeon.formula.validation.Validator;

public abstract class AbstractFormBinder<T extends Object> implements FormBinder<T>, PropertyChangeListener {

	private final Set<FormListener> formListeners = new HashSet<FormListener>();
	private final Set<FormFieldListener> formFieldListeners = new HashSet<FormFieldListener>();
	private final Set<FormValidationListener> formValidationListeners = new HashSet<FormValidationListener>();
	private final PropertyMap propertyMap = new PropertyMap();
	private T form;
	private Object model;
	private Validator validator;

	public AbstractFormBinder() {
		propertyMap.addPropertyChangeListener(this);
		propertyMap.put("username", "username");
		propertyMap.put("password", "passwordD");
	}

	public AbstractFormBinder(Object model) {
		this();
		this.model = model;
	}

	@Override
	public T getForm() {
		return form;
	}

	public void setForm(T form) {
		this.form = form;
	}

	@Override
	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Override
	public Set<FormBinding> bindForm(T form) {
		if (form.getClass().isAnnotationPresent(Form.class)) {
			setForm(form);

			Form formAnnotation = form.getClass().getAnnotation(Form.class);
			Class validatorClass = formAnnotation.validator();
			try {
				Validator validator = (Validator) validatorClass.newInstance();
				setValidator(validator);
			} catch (Exception e) {
				throw new BindingException(e.getClass().getName() + " when creating validator " + validatorClass.getName() + ".", e);
			}

			Set<FormBinding> formBindings = bindFormFields(form);
			validator.setFormBindings(formBindings);

			return formBindings;
		} else {
			throw new BindingException(form.getClass().getName() + " does not have a @Form annotation.");
		}
	}

	protected abstract Set<FormBinding> bindFormFields(T form);

	protected abstract FormFieldBinding bindFormField(Object formField, String property, boolean required);

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		fireFormEvent(new FormPropertyEditedEvent(this, e.getPropertyName(), e.getOldValue(), e.getNewValue()));
		ValidationResult validationResult = validate();
		if (validationResult != null) {
			fireFormValidationEvent(new FormEditValidationEvent(this, validationResult));
		}
	}

	@Override
	public void init() {

	}

	@Override
	public ValidationResult validate() {
		if (getValidator() != null) {
			ValidationResult validationResult = getValidator().validate(getPropertyMap());
			return validationResult;
		} else {
			return null;
		}
	}

	@Override
	public void commit() {
		ValidationResult validationResult = validate();
		fireFormValidationEvent(new FormCommitValidationEvent(this, validationResult));
		if (!validationResult.hasErrors()) {
			System.out.println("Commit: " + propertyMap);
			fireFormEvent(new FormCommittedEvent(this));
		}
	}

	@Override
	public void rollback() {
		System.out.println("Rollback");
	}

	@Override
	public synchronized void addFormListener(FormListener listener) {
		if (listener != null) {
			this.formListeners.add(listener);
		}
	}

	@Override
	public synchronized void removeFormListener(FormListener listener) {
		if (listener != null) {
			this.formListeners.remove(listener);
		}
	}

	@Override
	public synchronized void addFormFieldListener(FormFieldListener listener) {
		if (listener != null) {
			this.formFieldListeners.add(listener);
		}
	}

	@Override
	public synchronized void removeFormFieldListener(FormFieldListener listener) {
		if (listener != null) {
			this.formFieldListeners.remove(listener);
		}
	}

	@Override
	public synchronized void addFormValidationListener(FormValidationListener listener) {
		if (listener != null) {
			this.formValidationListeners.add(listener);
		}
	}

	@Override
	public synchronized void removeFormValidationListener(FormValidationListener listener) {
		if (listener != null) {
			this.formValidationListeners.remove(listener);
		}
	}

	protected void fireFormEvent(FormEvent e) {
		Object[] listeners = formListeners.toArray();
		for (Object listener : listeners) {
			FormListener formListener = (FormListener) listener;
			if (e instanceof FormPropertyEditedEvent) {
				formListener.formPropertyEdited((FormPropertyEditedEvent) e);
			} else if (e instanceof FormCommittedEvent) {
				formListener.formCommitted((FormCommittedEvent) e);
			}
		}
	}

	protected void fireFormFieldEvent(FormEvent e) {
		Object[] listeners = formFieldListeners.toArray();
		for (Object listener : listeners) {
			FormFieldListener formFieldListener = (FormFieldListener) listener;
			if (e instanceof FormFieldFocusGainedEvent) {
				formFieldListener.formFieldFocusGained((FormFieldFocusGainedEvent) e);
			} else if (e instanceof FormFieldFocusLostEvent) {
				formFieldListener.formFieldFocusLost((FormFieldFocusLostEvent) e);
			}
		}
	}

	protected void fireFormValidationEvent(FormEvent e) {
		Object[] listeners = formValidationListeners.toArray();
		for (Object listener : listeners) {
			FormValidationListener formValidationListener = (FormValidationListener) listener;
			if (e instanceof FormEditValidationEvent) {
				formValidationListener.formEditValidation((FormEditValidationEvent) e);
			} else if (e instanceof FormCommitValidationEvent) {
				formValidationListener.formCommitValidation((FormCommitValidationEvent) e);
			}
		}
	}

	protected PropertyMap getPropertyMap() {
		return propertyMap;
	}
}
