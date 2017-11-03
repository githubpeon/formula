package org.formula.event;

import org.formula.binding.FormBinder;

public class FormRefreshedEvent extends FormEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7977957739646083718L;

	public FormRefreshedEvent(FormBinder source) {
		super(source);
	}

}
