package org.formula.event;

import org.formula.binding.FormBinder;

public class FormInitializedEvent extends FormEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;

	public FormInitializedEvent(FormBinder source) {
		super(source);
	}

}
