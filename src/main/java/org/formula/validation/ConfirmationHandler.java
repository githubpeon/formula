package org.formula.validation;

import java.util.Map;

public interface ConfirmationHandler {

	public boolean confirmCommit(Map<String, Object> model);

}
