package org.formula.event;

public interface FormFieldListener {

	public void formFieldFocusGained(FormFieldFocusGainedEvent e);

	public void formFieldFocusLost(FormFieldFocusLostEvent e);

}
