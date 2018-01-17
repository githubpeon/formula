package org.formula.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.formula.annotation.Form;
import org.formula.annotation.FormField;
import org.formula.converter.Converter;
import org.formula.event.FormCommitValidationEvent;
import org.formula.event.FormCommittedEvent;
import org.formula.event.FormEditValidationEvent;
import org.formula.event.FormEnableEvent;
import org.formula.event.FormEnableListener;
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
import org.formula.validation.ConfirmationHandler;
import org.formula.validation.FieldValidator;
import org.formula.validation.ValidationResult;
import org.formula.validation.Validator;
import org.minimalcode.beans.ObjectWrapper;

public abstract class AbstractFormBinder implements FormBinder, PropertyChangeListener {

	private final Set<FormListener> formListeners = new HashSet<FormListener>();
	private final Set<FormFieldListener> formFieldListeners = new HashSet<FormFieldListener>();
	private final Set<FormValidationListener> formValidationListeners = new HashSet<FormValidationListener>();
	private final Set<FormEnableListener> formEnableListeners = new HashSet<FormEnableListener>();
	private final PropertyMap propertyMap = new PropertyMap();
	private final Map<String, Converter> converters = new HashMap<String, Converter>();
	private final Map<String, Set<FormFieldBinding>> formFieldBindings = new HashMap<String, Set<FormFieldBinding>>();
	private final Map<String, Integer> indexMap = new HashMap<String, Integer>();
	private Object form;
	private Object model;
	private Map<Object, ObjectWrapper> objectWrappers = new HashMap<Object, ObjectWrapper>();
	private Validator validator;
	private ConfirmationHandler confirmationHandler;
	private boolean initialized = false;

	public AbstractFormBinder() {

	}

	public AbstractFormBinder(Object form) {
		setForm(form);
	}

	@Override
	public Object getProperty(String property) {
		return this.propertyMap.get(property);
	}

	@Override
	public Object getProperty(String property, int index) {
		property = property.replaceFirst("#", "#" + index);
		return getProperty(property);
	}

	@Override
	public void setProperty(String property, Object value) {
		this.propertyMap.put(property, value);
	}

	@Override
	public void setProperty(String property, Object value, int index) {
		property = property.replaceFirst("#", "#" + index);
		setProperty(property, value);
	}

	@Override
	public void enableProperty(String property, boolean enable) {
		Set<FormFieldBinding> formFieldBindings = this.formFieldBindings.get(property);
		if (formFieldBindings != null) {
			for (FormFieldBinding formFieldBinding : formFieldBindings) {
				formFieldBinding.enable(enable);
			}
		}
	}

	@Override
	public void enableProperty(String property, boolean enable, int index) {
		property = property.replaceFirst("#", "#" + index);
		enableProperty(property, enable);
	}

	@Override
	public void focusProperty(String property) {
		Set<FormFieldBinding> formFieldBindings = this.formFieldBindings.get(property);
		if (formFieldBindings != null) {
			for (FormFieldBinding formFieldBinding : formFieldBindings) {
				formFieldBinding.focus();
			}
		}
	}

	@Override
	public void focusProperty(String property, int index) {
		property = property.replaceFirst("#", "#" + index);
		focusProperty(property);
	}

	@Override
	public void showProperty(String property, boolean visible) {
		Set<FormFieldBinding> formFieldBindings = this.formFieldBindings.get(property);
		if (formFieldBindings != null) {
			for (FormFieldBinding formFieldBinding : formFieldBindings) {
				formFieldBinding.show(visible);
			}
		}
	}

	@Override
	public void showProperty(String property, boolean visible, int index) {
		property = property.replaceFirst("#", "#" + index);
		showProperty(property, visible);
	}

	protected Object getForm() {
		return this.form;
	}

	@Override
	public void setForm(Object form) {
		if (!form.getClass().isAnnotationPresent(Form.class)) {
			throw new BindingException(form.getClass().getName() + " does not have a @Form annotation.");
		} else if (this.form != null) {
			throw new BindingException("FormBinder " + getClass().getName() + " is already bound to form " + this.form.getClass().getName() + ".");
		}
		this.form = form;
		propertyMap.addPropertyChangeListener(this);
		bindForm();
	}

	protected Object getModel() {
		return model;
	}

	@Override
	public void setModel(Object model) {
		this.initialized = false;
		this.objectWrappers.clear();
		this.model = model;

		if (!(model instanceof Map)) {
			this.objectWrappers.put(model, new ObjectWrapper(model));
			for (String key : this.propertyMap.keySet()) {
				Matcher matcher = FormBinder.INDEXDED_PROPERTY_KEY_PATTERN.matcher(key);
				if (matcher.matches()) {
					String indexKey = matcher.group(2);
					int index = Integer.valueOf(matcher.group(3));
					Object indexedModel = getIndexedValueAt(indexKey, index, getModel());
					this.objectWrappers.put(indexedModel, new ObjectWrapper(indexedModel));
				}
			}
		}

		init();
		validate();
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

	public ConfirmationHandler getConfirmationHandler() {
		return confirmationHandler;
	}

	public void setConfirmationHandler(ConfirmationHandler confirmationHandler) {
		this.confirmationHandler = confirmationHandler;
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
			if (property.contains("#")) {
				int index = 0;
				String key = container.getClass() + ";" + property;
				if (!this.indexMap.containsKey(key)) {
					indexMap.put(key, 0);
				} else {
					index = indexMap.get(key) + 1;
					indexMap.put(key, index);
				}
				property = property.replace("#", "#" + String.valueOf(index));
			}

			boolean required = formFieldAnnotation.required();
			Converter converter = null;
			Class converterClass = formFieldAnnotation.converter();
			try {
				converter = (Converter) converterClass.newInstance();
				if (!property.isEmpty()) {
					addConverter(property, converter);
				}
			} catch (Exception e) {
				throw new BindingException(e.getClass().getName() + " when creating converter " + converterClass.getName() + ".", e);
			}

			String[] labelProperties = formFieldAnnotation.labelProperties();
			String optionsProperty = formFieldAnnotation.optionsProperty();

			FormFieldBinding formFieldBinding = bindFormField(formField, property, labelProperties, optionsProperty, required, converter);

			Class validatorClass = formFieldAnnotation.validator();
			try {
				FieldValidator fieldValidator = (FieldValidator) validatorClass.newInstance();
				fieldValidator.setFormFieldBinding(formFieldBinding);
				getValidator().addFieldValidator(fieldValidator);
			} catch (Exception e) {
				throw new BindingException(e.getClass().getName() + " when creating validator " + validatorClass.getName() + ".", e);
			}

			Set<FormFieldBinding> formFieldBindingSet = this.formFieldBindings.get(property);
			if (formFieldBindingSet == null) {
				formFieldBindingSet = new HashSet<FormFieldBinding>();
				this.formFieldBindings.put(property, formFieldBindingSet);
			}
			formFieldBindingSet.add(formFieldBinding);

			for (String labelProperty : labelProperties) {
				formFieldBindingSet = this.formFieldBindings.get(labelProperty);
				if (formFieldBindingSet == null) {
					formFieldBindingSet = new HashSet<FormFieldBinding>();
					this.formFieldBindings.put(labelProperty, formFieldBindingSet);
				}
				formFieldBindingSet.add(formFieldBinding);
			}

			return formFieldBinding;
		} catch (IllegalAccessException e) {
			throw new BindingException(e.getClass().getName() + " when creating binding for field " + field + ".", e);
		}
	}

	protected abstract FormFieldBinding bindFormField(Object formField, String property, String[] labelProperties, String optionsProperty, boolean required, Converter converter);

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		fireFormEvent(new FormPropertyEditedEvent(this, e.getPropertyName(), e.getOldValue(), e.getNewValue()));
		if (this.initialized) {
			ValidationResult validationResult = validate();
			if (validationResult != null) {
				fireFormValidationEvent(new FormEditValidationEvent(this, validationResult));
			}
		}
	}

	protected void init() {
		read();
		this.initialized = true;
		fireFormEvent(new FormInitializedEvent(this));
	}

	protected ValidationResult validate() {
		if (this.initialized && getValidator() != null) {
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
		if (!validationResult.hasErrors() && (this.confirmationHandler == null || this.confirmationHandler.confirmCommit(this.propertyMap))) {
			write();
			fireFormEvent(new FormCommittedEvent(this));
		}
	}

	@Override
	public void rollback() {
		read();
		fireFormEvent(new FormRolledBackEvent(this));
	}

	@Override
	public void enable(boolean enable, boolean requestFocus) {
		fireFormEnableEvent(new FormEnableEvent(this, enable, requestFocus));
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

	@Override
	public synchronized void addFormEnableListener(FormEnableListener listener) {
		if (listener != null) {
			this.formEnableListeners.add(listener);
		}
	}

	@Override
	public synchronized void removeFormEnableListener(FormEnableListener listener) {
		if (listener != null) {
			this.formEnableListeners.remove(listener);
		}
	}

	protected void fireFormEvent(FormEvent e) {
		ArrayList<Object> listeners = new ArrayList<Object>(formListeners);
		// We want to formbindings like for example the commit button binding to do their things
		// before anything the application does.
		Collections.sort(listeners, new Comparator<Object>() {
			public int compare(Object listener1, Object listener2) {
				if (listener1 instanceof FormBinding && !(listener2 instanceof FormBinding)) {
					return -1;
				} else if (listener2 instanceof FormBinding && !(listener1 instanceof FormBinding)) {
					return 1;
				}
				return 0;
			}
		});

		for (Object listener : listeners) {
			FormListener formListener = (FormListener) listener;
			if (e instanceof FormPropertyEditedEvent) {
				if (this.initialized) {
					formListener.formPropertyEdited((FormPropertyEditedEvent) e);
				}
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

	protected void fireFormEnableEvent(FormEvent e) {
		Object[] listeners = formEnableListeners.toArray();
		for (Object listener : listeners) {
			FormEnableListener formEnableListener = (FormEnableListener) listener;
			if (e instanceof FormEnableEvent) {
				formEnableListener.formEnable((FormEnableEvent) e);
			}
		}
	}

	protected PropertyMap getPropertyMap() {
		return propertyMap;
	}

	private void read() {
		for (String key : this.propertyMap.keySet()) {
			Object value = null;
			if (getModel() instanceof Map) {
				Map modelMap = (Map) getModel();
				value = modelMap.get(key);
			} else {
				Matcher matcher = FormBinder.INDEXDED_PROPERTY_KEY_PATTERN.matcher(key);
				if (matcher.matches()) {
					String indexKey = matcher.group(2);
					int index = Integer.valueOf(matcher.group(3));
					String property = matcher.group(4);
					value = getIndexedValueAt(indexKey, index, getModel());
					value = this.objectWrappers.get(value).getValue(property);
				} else {
					value = this.objectWrappers.get(getModel()).getValue(key);
				}
			}
			this.propertyMap.put(key, value);
		}
	}

	@SuppressWarnings("unchecked")
	private void write() {
		for (String key : this.propertyMap.keySet()) {
			Object value = this.propertyMap.get(key);

			if (getModel() instanceof Map) {
				Map modelMap = (Map) model;
				modelMap.put(key, value);
			} else {
				Matcher matcher = FormBinder.INDEXDED_PROPERTY_KEY_PATTERN.matcher(key);
				if (matcher.matches()) {
					String indexKey = matcher.group(2);
					int index = Integer.valueOf(matcher.group(3));
					String property = matcher.group(4);
					Object indexedModel = getIndexedValueAt(indexKey, index, getModel());
					if (this.objectWrappers.get(indexedModel).getProperty(property).isWritable()) {
						this.objectWrappers.get(indexedModel).setValue(property, value);
					}
				} else {
					if (this.objectWrappers.get(getModel()).getProperty(key).isWritable()) {
						this.objectWrappers.get(getModel()).setValue(key, value);
					}
				}
			}
		}
	}

	private Object getIndexedValueAt(String key, int index, Object model) {
		Object value = null;
		if (key == null || key.isEmpty()) {
			if (model instanceof Iterator) {
				Iterator iterator = ((Iterable) model).iterator();
				for (int i = 0; i <= index; ++i) {
					value = iterator.next();
				}
			} else {
				throw new BindingException(getClass().getName() + " expected the model to be an iterable but it was " + model.getClass().getName() + ".");
			}
		} else {
			value = new ObjectWrapper(model).getIndexedValue(key, index);
		}
		return value;
	}
}
