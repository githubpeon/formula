package com.github.githubpeon.formula.event;

import java.util.EventObject;

import com.github.githubpeon.formula.binding.FormBinder;

public class FormEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3940726011165626441L;

	public FormEvent(FormBinder source) {
		super(source);
	}

}
