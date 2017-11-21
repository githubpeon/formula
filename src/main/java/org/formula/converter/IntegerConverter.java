package org.formula.converter;

public class IntegerConverter extends DefaultConverter {

	/**
	 * Converts TO the model FROM the view.
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public Object convertTo(Object value) {
		try {
			String valueString = (String) value;
			return (valueString == null || valueString.isEmpty() ? null : Integer.decode(valueString));
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
