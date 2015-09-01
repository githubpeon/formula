package com.github.githubpeon.formula.event;

import com.github.githubpeon.formula.binding.FormBinder;

public class FormFieldEvent extends FormEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3940726011165626441L;
	private Object field;

	public FormFieldEvent(FormBinder source, Object field) {
		super(source);
		this.field = field;
	}

	public Object getField() {
		return field;
	}

	public void setField(Object field) {
		this.field = field;
	}

}
