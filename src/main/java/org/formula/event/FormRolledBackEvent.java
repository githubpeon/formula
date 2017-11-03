package org.formula.event;

import org.formula.binding.FormBinder;

public class FormRolledBackEvent extends FormEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;

	public FormRolledBackEvent(FormBinder source) {
		super(source);
	}

}
