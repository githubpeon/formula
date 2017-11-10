package org.formula.swing.event;

import java.awt.Container;

import org.formula.binding.FormBinder;
import org.formula.event.FormController;
import org.formula.swing.binding.SwingFormBinder;

public class SwingFormController<T extends Container, U extends Object> extends FormController<T, U> {

	public SwingFormController(T form) {
		setForm(form);

		FormBinder formBinder = new SwingFormBinder(form);
		formBinder.addFormListener(this);
		formBinder.addFormValidationListener(this);
		formBinder.setConfirmationHandler(this);
		setFormBinder(formBinder);
	}

}
