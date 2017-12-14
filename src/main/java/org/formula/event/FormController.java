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
		getFormBinder().setForm(form);
	}

	public U getModel() {
		return model;
	}

	public void setModel(U model) {
		this.model = model;
		this.formBinder.setModel(model);
	}

	protected Object getProperty(String property) {
		return this.formBinder.getProperty(property);
	}

	protected Object getProperty(String property, int index) {
		return this.formBinder.getProperty(property, index);
	}

	protected void setProperty(String property, Object value) {
		this.formBinder.setProperty(property, value);
	}

	protected void setProperty(String property, Object value, int index) {
		this.formBinder.setProperty(property, value, index);
	}

	protected void enableProperty(String property, boolean enable) {
		this.formBinder.enableProperty(property, enable);
	}

	protected void enableProperty(String property, boolean enable, int index) {
		this.formBinder.enableProperty(property, enable, index);
	}

	protected void showProperty(String property, boolean visible) {
		this.formBinder.showProperty(property, visible);
	}

	protected void showProperty(String property, boolean visible, int index) {
		this.formBinder.showProperty(property, visible, index);
	}

	protected void focusProperty(String property) {
		this.formBinder.focusProperty(property);
	}

	protected void focusProperty(String property, int index) {
		this.formBinder.focusProperty(property, index);
	}

	@Override
	public boolean confirmCommit(Map<String, Object> model) {
		return true;
	}

	protected void enableCommit(boolean enable, boolean requestFocus) {
		getFormBinder().enable(enable, requestFocus);
	}

	protected void refresh() {
		this.formBinder.refresh();
	}
}
