package com.github.githubpeon.formula.validation;

import java.util.Map;

import com.github.githubpeon.formula.binding.FormFieldBinding;

public interface FieldValidator {

	public void setFormFieldBinding(FormFieldBinding formFieldBinding);

	public ValidationResult validate(Map<String, Object> model);

}
