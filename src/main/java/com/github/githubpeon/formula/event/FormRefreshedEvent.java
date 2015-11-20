package com.github.githubpeon.formula.event;

import com.github.githubpeon.formula.binding.FormBinder;

public class FormRefreshedEvent extends FormEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7977957739646083718L;

	public FormRefreshedEvent(FormBinder source) {
		super(source);
	}

}
