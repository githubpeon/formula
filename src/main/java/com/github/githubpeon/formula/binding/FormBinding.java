package com.github.githubpeon.formula.binding;

public class FormBinding<T extends Object> {

	private T view;
	private FormBinder formBinder;

	public FormBinding(T view, FormBinder formBinder) {
		this.view = view;
		this.formBinder = formBinder;
	}

	public T getView() {
		return view;
	}

	public void setView(T view) {
		this.view = view;
	}

	public FormBinder getFormBinder() {
		return formBinder;
	}

	public void setFormBinder(FormBinder formBinder) {
		this.formBinder = formBinder;
	}

}
