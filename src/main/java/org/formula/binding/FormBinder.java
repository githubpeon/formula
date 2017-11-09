package org.formula.binding;

import org.formula.event.FormFieldListener;
import org.formula.event.FormListener;
import org.formula.event.FormValidationListener;

public interface FormBinder {

	public void setModel(Object model);

	public void setProperty(String property, Object value);

	public void refresh();

	public void commit();

	public void rollback();

	public void addFormListener(FormListener listener);

	public void removeFormListener(FormListener listener);

	public void addFormFieldListener(FormFieldListener listener);

	public void removeFormFieldListener(FormFieldListener listener);

	public void addFormValidationListener(FormValidationListener listener);

	public void removeFormValidationListener(FormValidationListener listener);

}
