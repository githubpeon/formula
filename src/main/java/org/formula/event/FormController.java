package org.formula.event;

import java.util.Map;

import org.formula.binding.FormBinder;
import org.formula.validation.ConfirmationHandler;

public class FormController<T extends Object, U extends Object> extends DefaultFormListener implements ConfirmationHandler {

	private FormBinder formBinder;
	private T form;
	private U model;

	public FormController() {

	}

	public FormBinder getFormBinder() {
		return formBinder;
	}

	public void setFormBinder(FormBinder formBinder) {
		this.formBinder = formBinder;
	}

	public T getForm() {
		return form;
	}

	public void setForm(T form) {
		this.form = form;
	}

	public U getModel() {
		return model;
	}

	public void setModel(U model) {
		this.model = model;
		this.formBinder.setModel(model);
	}

	protected void setProperty(String property, Object value) {
		setProperty(property, value, 0);
	}

	protected void setProperty(String property, Object value, int index) {
		property = property + "." + index;
		this.formBinder.setProperty(property, value);
	}

	@Override
	public boolean confirmCommit(Map<String, Object> propertyMap) {
		return true;
	}

}
