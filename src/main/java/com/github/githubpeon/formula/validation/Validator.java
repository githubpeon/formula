package com.github.githubpeon.formula.validation;

import java.util.Set;

import com.github.githubpeon.formula.binding.FormBinding;

public interface Validator<T extends Object> {

	public void setFormBindings(Set<FormBinding> formBindings);

	public void addFieldValidator(FieldValidator fieldValidator);

	public ValidationResult validate(T model);

}
