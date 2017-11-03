package org.formula.converter;

public class IntegerConverter implements Converter {

	/**
	 * Converts FROM the model TO the view.
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public Object convertFrom(Object value) {
		return String.valueOf(value);
	}

	/**
	 * Converts TO the model FROM the view.
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public Object convertTo(Object value) {
		String valueString = (String) value;
		return (valueString == null || valueString.isEmpty() ? 0 : Integer.decode(valueString));
	}

}
