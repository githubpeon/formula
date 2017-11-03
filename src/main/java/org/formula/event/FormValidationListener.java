package org.formula.event;

public interface FormValidationListener {

	public void formEditValidation(FormEditValidationEvent e);

	public void formCommitValidation(FormCommitValidationEvent e);

}
