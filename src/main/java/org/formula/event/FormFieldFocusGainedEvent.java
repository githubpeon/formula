package org.formula.event;

import org.formula.binding.FormBinder;

public class FormFieldFocusGainedEvent extends FormFieldEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;

	public FormFieldFocusGainedEvent(FormBinder source, Object view) {
		super(source, view);
	}

}
