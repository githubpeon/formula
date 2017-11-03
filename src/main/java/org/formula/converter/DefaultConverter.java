package org.formula.converter;

public class DefaultConverter implements Converter {

	/**
	 * Converts FROM the model TO the view.
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public Object convertFrom(Object value) {
		return value;
	}

	/**
	 * Converts TO the model FROM the view.
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public Object convertTo(Object value) {
		return value;
	}

}
