package org.formula.validation;

import java.util.Map;

import org.formula.binding.FormFieldBinding;

public interface FieldValidator {

    public FormFieldBinding getFormFieldBinding();

	public void setFormFieldBinding(FormFieldBinding formFieldBinding);

	public ValidationResult validate(Map<String, Object> model);

}
