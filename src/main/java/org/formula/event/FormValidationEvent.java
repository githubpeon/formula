package org.formula.event;

import org.formula.binding.FormBinder;
import org.formula.validation.ValidationResult;

public class FormValidationEvent extends FormEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;
	private ValidationResult validationResult;

	public FormValidationEvent(FormBinder source, ValidationResult validationResult) {
		super(source);
		this.validationResult = validationResult;
	}

	public ValidationResult getValidationResult() {
		return validationResult;
	}

	public void setValidationResult(ValidationResult validationResult) {
		this.validationResult = validationResult;
	}

}
