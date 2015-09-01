package com.github.githubpeon.formula.event;

public interface FormValidationListener {

	public void formEditValidation(FormEditValidationEvent e);

	public void formCommitValidation(FormCommitValidationEvent e);

}
