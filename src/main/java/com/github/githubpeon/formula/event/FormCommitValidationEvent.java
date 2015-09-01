package com.github.githubpeon.formula.event;

import com.github.githubpeon.formula.binding.FormBinder;
import com.github.githubpeon.formula.validation.ValidationResult;

public class FormCommitValidationEvent extends FormValidationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3683203752178776038L;

	public FormCommitValidationEvent(FormBinder source, ValidationResult validationResult) {
		super(source, validationResult);
	}

}
