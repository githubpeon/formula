package com.github.githubpeon.formula.binding;

import com.github.githubpeon.formula.event.FormEvent;
import com.github.githubpeon.formula.event.FormListener;

public class FormBinding implements FormListener {

	private Object view;
	private FormBinder formBinder;

	public FormBinding(Object view, FormBinder formBinder) {
		this.view = view;
		this.formBinder = formBinder;
		formBinder.addFormListener(this);
	}

	public Object getView() {
		return view;
	}

	public void setView(Object view) {
		this.view = view;
	}

	public FormBinder getFormBinder() {
		return formBinder;
	}

	public void setFormBinder(FormBinder formBinder) {
		this.formBinder = formBinder;
	}

	@Override
	public void handleFormEvent(FormEvent e) {
		// Default is to do nothing.
	}
}
