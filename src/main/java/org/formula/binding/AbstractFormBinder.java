package org.formula.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.formula.annotation.Form;
import org.formula.annotation.FormField;
import org.formula.converter.Converter;
import org.formula.event.FormCommitValidationEvent;
import org.formula.event.FormCommittedEvent;
import org.formula.event.FormEditValidationEvent;
import org.formula.event.FormEvent;
import org.formula.event.FormFieldFocusGainedEvent;
import org.formula.event.FormFieldFocusLostEvent;
import org.formula.event.FormFieldListener;
import org.formula.event.FormInitializedEvent;
import org.formula.event.FormListener;
import org.formula.event.FormPropertyEditedEvent;
import org.formula.event.FormRefreshedEvent;
import org.formula.event.FormRolledBackEvent;
import org.formula.event.FormValidationListener;
import org.formula.validation.FieldValidator;
import org.formula.validation.ValidationMessage;
import org.formula.validation.ValidationResult;
import org.formula.validation.Validator;
import org.minimalcode.beans.ObjectWrapper;

public abstract class AbstractFormBinder implements FormBinder, PropertyChangeListener {

	private final Set<FormListener> formListeners = new HashSet<FormListener>();
	private final Set<FormFieldListener> formFieldListeners = new HashSet<FormFieldListener>();
	private final Set<FormValidationListener> formValidationListeners = new HashSet<FormValidationListener>();
	private final PropertyMap propertyMap = new PropertyMap();
	private final Object form;
	private Object model;
	private Validator validator;
	private final Map<String, Converter> converters = new HashMap<String, Converter>();
	private final List<ObjectWrapper> objectWrappers = new ArrayList<ObjectWrapper>();
	private final Map<Class, List<Object>> containers = new HashMap<Class, List<Object>>();

	public AbstractFormBinder(Object form) {
		if (!form.getClass().isAnnotationPresent(Form.class)) {
			throw new BindingException(form.getClass().getName() + " does not have a @Form annotation.");
		}
		this.form = form;
		propertyMap.addPropertyChangeListener(this);
		bindForm();
	}

	protected Object getForm() {
		return this.form;
	}

	protected Object getModel() {
		return model;
	}

	@Override
	public void setModel(Object model) {
		if (model instanceof Collection) {
			Iterator iterator = ((Collection) model).iterator();
			while (iterator.hasNext()) {
				this.objectWrappers.add(new ObjectWrapper(iterator.next()));
			}
		} else if (!(model instanceof Map)) {
			this.objectWrappers.add(new ObjectWrapper(model));
		}
		this.model = model;
		init();
	}

	@Override
	public void refresh() {
		read();
		fireFormEvent(new FormRefreshedEvent(this));
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	protected void addConverter(String property, Converter converter) {
		this.converters.put(property, converter);
	}

	protected Set<FormBinding> bindForm() {
		Form formAnnotation = this.form.getClass().getAnnotation(Form.class);
		Class validatorClass = formAnnotation.validator();
		try {
			Validator validator = (Validator) validatorClass.newInstance();
			setValidator(validator);
		} catch (Exception e) {
			throw new BindingException(e.getClass().getName() + " when creating validator " + validatorClass.getName() + ".", e);
		}

		Set<FormBinding> formBindings = bindFormFields();
		validator.setView(this.form);
		validator.setFormBindings(formBindings);

		return formBindings;
	}

	protected abstract Set<FormBinding> bindFormFields();

	protected FormFieldBinding bindFormField(Field field, Object container) {
		try {
			field.setAccessible(true);
			Object formField = field.get(container);
			FormField formFieldAnnotation = field.getAnnotation(FormField.class);

			String property = formFieldAnnotation.property();
			// We add the index of the container to the property key so we can have multiple copies of the same
			// container in one and the same form. We're going to assume the model is an Iterable when
			// we have multiple copies.
			List<Object> classContainers = containers.get(container.getClass());
			if (classContainers == null) {
				classContainers = new ArrayList<Object>();
				containers.put(container.getClass(), classContainers);
			}
			if (!classContainers.contains(container)) {
				classContainers.add(container);
			}
			property += "." + classContainers.indexOf(container);

			boolean required = formFieldAnnotation.required();
			Converter converter = null;
			Class converterClass = formFieldAnnotation.converter();
			try {
				converter = (Converter) converterClass.newInstance();
				addConverter(property, converter);
			} catch (Exception e) {
				throw new BindingException(e.getClass().getName() + " when creating converter " + converterClass.getName() + ".", e);
			}

			FormFieldBinding formFieldBinding = bindFormField(formField, property, required);

			Class validatorClass = formFieldAnnotation.validator();
			try {
				FieldValidator fieldValidator = (FieldValidator) validatorClass.newInstance();
				fieldValidator.setFormFieldBinding(formFieldBinding);
				getValidator().addFieldValidator(fieldValidator);
			} catch (Exception e) {
				throw new BindingException(e.getClass().getName() + " when creating validator " + validatorClass.getName() + ".", e);
			}

			return formFieldBinding;
		} catch (IllegalAccessException e) {
			throw new BindingException(e.getClass().getName() + " when creating binding for field " + field + ".", e);
		}
	}

	protected abstract FormFieldBinding bindFormField(Object formField, String property, boolean required);

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		fireFormEvent(new FormPropertyEditedEvent(this, e.getPropertyName(), e.getOldValue(), e.getNewValue()));
		ValidationResult validationResult = validate();
		if (validationResult != null) {
			fireFormValidationEvent(new FormEditValidationEvent(this, validationResult));
		}
	}

	protected void init() {
		read();
		fireFormEvent(new FormInitializedEvent(this));
	}

	protected ValidationResult validate() {
		if (getValidator() != null) {
			ValidationResult validationResult = getValidator().validate(this.propertyMap);
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
			write();
			System.out.println("Commit: " + propertyMap + " to " + getModel());
			fireFormEvent(new FormCommittedEvent(this));
		} else {
			// TODO: Remove this
			for (ValidationMessage message : validationResult.getFormValidationMessages()) {
				System.out.println(message);
			}
			for (ValidationMessage message : validationResult.getPropertyValidationMessages()) {
				System.out.println(message.getProperty() + " == " + message);
			}
		}
	}

	@Override
	public void rollback() {
		read();
		fireFormEvent(new FormRolledBackEvent(this));
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
			} else if (e instanceof FormInitializedEvent) {
				formListener.formInitialized((FormInitializedEvent) e);
			} else if (e instanceof FormRefreshedEvent) {
				formListener.formRefreshed((FormRefreshedEvent) e);
			} else if (e instanceof FormCommittedEvent) {
				formListener.formCommitted((FormCommittedEvent) e);
			} else if (e instanceof FormRolledBackEvent) {
				formListener.formRolledBack((FormRolledBackEvent) e);
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

	public PropertyMap getPropertyMap() {
		return propertyMap;
	}

	private void read() {
		for (String key : this.propertyMap.keySet()) {
			// We're assuming we have a key in the form of property.index, for example password.0, where
			// the index corresponds to the index of the objectwrapper for the specific model object
			// we're reading from. If it's not a map. In that case we just read it straight from the key.
			Object value = null;
			int lastIndex = key.lastIndexOf(".");
			int index = Integer.parseInt(key.substring(lastIndex + 1));

			if (getModel() instanceof Map) {
				Map modelMap = (Map) model;
				value = modelMap.get(key);
				if (value == null && index == 0) {
					value = modelMap.get(key.substring(0, lastIndex));
				}
			} else {
				value = this.objectWrappers.get(index).getValue(key.substring(0, lastIndex));
			}
			Converter converter = this.converters.get(key);
			this.propertyMap.put(key, converter.convertFrom(value));
		}
	}

	@SuppressWarnings("unchecked")
	private void write() {
		for (String key : this.propertyMap.keySet()) {
			// And we do the same thing when writing.
			int lastIndex = key.lastIndexOf(".");
			int index = Integer.parseInt(key.substring(lastIndex + 1));
			Object value = this.propertyMap.get(key);
			Converter converter = this.converters.get(key);
			if (getModel() instanceof Map) {
				Map modelMap = (Map) model;
				modelMap.put(key, value);
				modelMap.put(key.substring(0, lastIndex), value);
			} else {
				this.objectWrappers.get(index).setValue(key.substring(0, lastIndex), converter.convertTo(value));
			}
		}
	}

}
