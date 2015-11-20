package com.github.githubpeon.formula.binding;

import com.github.githubpeon.formula.event.FormFieldListener;
import com.github.githubpeon.formula.event.FormListener;
import com.github.githubpeon.formula.event.FormValidationListener;

public interface FormBinder {

	public void setModel(Object model);

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
