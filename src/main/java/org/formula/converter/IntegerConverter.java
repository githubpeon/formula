package org.formula.converter;

public class IntegerConverter implements Converter<Integer, String> {

	/**
	 * Converts FROM the model TO the view.
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public String convertFrom(Integer value) {
		return String.valueOf(value);
	}

	/**
	 * Converts TO the model FROM the view.
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public Integer convertTo(String value) {
		String valueString = value;
		return (valueString == null || valueString.isEmpty() ? 0 : Integer.decode(valueString));
	}

}
