package com.github.githubpeon.formula.binding;

import java.util.Set;

import com.github.githubpeon.formula.event.FormListener;

public interface FormBinder<T extends Object> {

	public T getForm();

	public Object getModel();

	public Set<FormBinding> bindForm(T form);

	public void init();

	public void validate();

	public void commit();

	public void rollback();

	public void addFormListener(FormListener listener);

	public void removeFormListener(FormListener listener);

}
