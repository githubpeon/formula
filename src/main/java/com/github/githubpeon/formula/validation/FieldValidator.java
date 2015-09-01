package com.github.githubpeon.formula.validation;

import com.github.githubpeon.formula.binding.FormFieldBinding;

public interface FieldValidator<T extends Object> {

	public void setFormFieldBinding(FormFieldBinding formFieldBinding);

	public ValidationResult validate(T model);

}
