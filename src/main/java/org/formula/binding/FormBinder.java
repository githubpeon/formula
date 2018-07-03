package org.formula.binding;

import java.util.regex.Pattern;

import org.formula.event.FormEnableListener;
import org.formula.event.FormFieldListener;
import org.formula.event.FormListener;
import org.formula.event.FormValidationListener;
import org.formula.validation.ConfirmationHandler;

public interface FormBinder {

	public final static Pattern INDEXDED_PROPERTY_KEY_PATTERN = Pattern.compile("((.*)\\.)?#(\\d*)\\.(.*)");

	public void setForm(Object form);

	public void setModel(Object model);

	public Object getProperty(String property);

	public Object getProperty(String property, int index);

	public void setProperty(String property, Object value);

	public void setProperty(String property, Object value, int index);

	public void enableProperty(String property, boolean enable);

	public void enableProperty(String property, boolean enable, int index);

	public void focusProperty(String property);

	public void focusProperty(String property, int index);

	public void showProperty(String property, boolean visible);

	public void showProperty(String property, boolean visible, int index);

	public void refresh();

	public void commit();

	public void commit(Object source);

	public void commitProperty(String property);

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
