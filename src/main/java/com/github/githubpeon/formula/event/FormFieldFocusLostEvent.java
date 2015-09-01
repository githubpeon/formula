package com.github.githubpeon.formula.event;

import com.github.githubpeon.formula.binding.FormBinder;

public class FormFieldFocusLostEvent extends FormFieldEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;

	public FormFieldFocusLostEvent(FormBinder source, Object field) {
		super(source, field);
	}

}
