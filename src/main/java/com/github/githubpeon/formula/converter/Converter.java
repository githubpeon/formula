package com.github.githubpeon.formula.converter;

public interface Converter {

	/**
	 * Converts FROM the model TO the view.
	 * 
	 * @param value
	 * @return
	 */
	public Object convertFrom(Object value);

	/**
	 * Converts TO the model FROM the view.
	 * 
	 * @param value
	 * @return
	 */
	public Object convertTo(Object value);

}
