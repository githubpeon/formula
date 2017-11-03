package org.formula.binding;

public class BindingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2897953219220849591L;

	public BindingException(String message) {
		super(message);
	}

	public BindingException(String message, Throwable cause) {
		super(message, cause);
	}
}
