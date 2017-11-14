package org.formula.binding;

import org.formula.event.FormEnableListener;
import org.formula.event.FormFieldListener;
import org.formula.event.FormListener;
import org.formula.event.FormValidationListener;
import org.formula.validation.ConfirmationHandler;

public interface FormBinder {

	public void setModel(Object model);

	public void setProperty(String property, Object value);

	public void refresh();

	public void commit();

	public void rollback();

	public void enable(boolean enable, boolean requestFocus);

	public void addFormListener(FormListener listener);

	public void removeFormListener(FormListener listener);

	public void addFormFieldListener(FormFieldListener listener);

	public void removeFormFieldListener(FormFieldListener listener);

	public void addFormValidationListener(FormValidationListener listener);

	public void removeFormValidationListener(FormValidationListener listener);

	public void setConfirmationHandler(ConfirmationHandler confirmationHandler);

	public void addFormEnableListener(FormEnableListener listener);

	public void removeFormEnableListener(FormEnableListener listener);

}
