package org.formula.validation;

import java.util.Map;
import java.util.Set;

import org.formula.binding.FormBinding;

public interface Validator {

	public void setView(Object view);

	public void setFormBindings(Set<FormBinding> formBindings);

	public void addFieldValidator(FieldValidator fieldValidator);

	public ValidationResult validate(Map<String, Object> model);

}
