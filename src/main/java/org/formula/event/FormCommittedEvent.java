package org.formula.event;

import org.formula.binding.FormBinder;

public class FormCommittedEvent extends FormEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;

	public FormCommittedEvent(FormBinder source) {
		super(source);
	}

}
