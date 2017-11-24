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

	protected Object getProperty(String property) {
		return getProperty(property, 0);
	}

	protected Object getProperty(String property, int index) {
		property = property + "." + index;
		return this.formBinder.getProperty(property);
	}

	protected void setProperty(String property, Object value) {
		setProperty(property, value, 0);
	}

	protected void setProperty(String property, Object value, int index) {
		property = property + "." + index;
		this.formBinder.setProperty(property, value);
	}

	protected void enableProperty(String property, boolean enable) {
		enableProperty(property, enable, 0);
	}

	protected void enableProperty(String property, boolean enable, int index) {
		property = property + "." + index;
		this.formBinder.enableProperty(property, enable);
	}

	@Override
	public boolean confirmCommit(Map<String, Object> model) {
		return true;
	}

	protected void enableCommit(boolean enable, boolean requestFocus) {
		getFormBinder().enable(enable, requestFocus);
	}
}
