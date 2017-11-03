package org.formula.event;

import org.formula.binding.FormBinder;
import org.formula.validation.ValidationResult;

public class FormEditValidationEvent extends FormValidationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;

	public FormEditValidationEvent(FormBinder source, ValidationResult validationResult) {
		super(source, validationResult);
	}

}
