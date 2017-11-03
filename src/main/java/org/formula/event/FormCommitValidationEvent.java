package org.formula.event;

import org.formula.binding.FormBinder;
import org.formula.validation.ValidationResult;

public class FormCommitValidationEvent extends FormValidationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;

	public FormCommitValidationEvent(FormBinder source, ValidationResult validationResult) {
		super(source, validationResult);
	}

}
