package com.github.githubpeon.formula.binding;

import java.util.Set;

import com.github.githubpeon.formula.event.FormFieldListener;
import com.github.githubpeon.formula.event.FormListener;
import com.github.githubpeon.formula.event.FormValidationListener;
import com.github.githubpeon.formula.validation.ValidationResult;

public interface FormBinder<T extends Object> {

	public T getForm();

	public Object getModel();

	public Set<FormBinding> bindForm(T form);

	public void init();

	public ValidationResult validate();

	public void commit();

	public void rollback();

	public void addFormListener(FormListener listener);

	public void removeFormListener(FormListener listener);

	public void addFormFieldListener(FormFieldListener listener);

	public void removeFormFieldListener(FormFieldListener listener);

	public void addFormValidationListener(FormValidationListener listener);

	public void removeFormValidationListener(FormValidationListener listener);

}
