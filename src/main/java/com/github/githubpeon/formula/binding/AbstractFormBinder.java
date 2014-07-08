package com.github.githubpeon.formula.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import com.github.githubpeon.formula.annotation.Form;
import com.github.githubpeon.formula.event.FormEvent;
import com.github.githubpeon.formula.event.FormEvent.FormEventId;
import com.github.githubpeon.formula.event.FormListener;
import com.github.githubpeon.formula.validation.Validator;

public abstract class AbstractFormBinder<T extends Object> implements FormBinder<T>, PropertyChangeListener {

	private final Set<FormListener> formListeners = new HashSet<FormListener>();
	private final PropertyMap propertyMap = new PropertyMap();
	private T form;
	private Object model;
	private Validator validator;

	public AbstractFormBinder() {
		propertyMap.addPropertyChangeListener(this);
		// valueMap.put("login.username", "username");
		// valueMap.put("login.password", "passwordD");
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
			Set<FormBinding> formBindings = bindFormFields(form);

			Form formAnnotation = form.getClass().getAnnotation(Form.class);
			Class validatorClass = formAnnotation.validator();
			try {
				Validator validator = (Validator) validatorClass.newInstance();
				validator.setFormBindings(formBindings);
				setValidator(validator);
			} catch (Exception e) {
				throw new BindingException(e.getClass().getName() + " when creating validator " + validatorClass.getName() + ".", e);
			}
			return formBindings;
		} else {
			throw new BindingException(form.getClass().getName() + " does not have a @Form annotation.");
		}
	}

	protected abstract Set<FormBinding> bindFormFields(T form);

	protected abstract FormFieldBinding bindFormField(Object formField, String property, boolean required);

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getPropertyName() + " till " + evt.getNewValue());
		validate();
		fireFormEvent(new FormEvent(this, FormEventId.FORM_EDITED));
	}

	@Override
	public void init() {

	}

	@Override
	public void validate() {
		System.out.println(getValidator().validate(getPropertyMap()));
	}

	@Override
	public void commit() {
		System.out.println("Commit: " + propertyMap);
		fireFormEvent(new FormEvent(this, FormEventId.FORM_COMMITTED));
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

	protected void fireFormEvent(FormEvent e) {
		Object[] listeners = formListeners.toArray();
		for (Object listener : listeners) {
			((FormListener) listener).handleFormEvent(e);
		}
	}

	protected PropertyMap getPropertyMap() {
		return propertyMap;
	}
}
